package com.vegan.data

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.storage.StorageReference
import com.vegan.di.module.Users
import com.vegan.extentions.requiredCurrentUserUid
import com.vegan.extentions.requiredUid
import com.vegan.models.User
import com.vegan.models.UserRegistration
import durdinapps.rxfirebase2.RxFirebaseAuth
import durdinapps.rxfirebase2.RxFirebaseStorage
import durdinapps.rxfirebase2.RxFirestore
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class UserRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val imageStoreReference: StorageReference,
    @Users private val users: CollectionReference,
) {

    val authState = BehaviorSubject.createDefault(UserAuthState.SMS_CODE_NOT_SENT)

    private val credentialSubject = BehaviorSubject.create<PhoneAuthCredential>()
    private val userRegistrationSubject =
        BehaviorSubject.create<UserRegistration>()

    private var verificationId: String? = null

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            credentialSubject.onNext(credential)
        }

        override fun onVerificationFailed(p0: FirebaseException) = Unit

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            super.onCodeSent(verificationId, token)
            authState.onNext(UserAuthState.SMS_END_CODE_SENT)
            this@UserRepository.verificationId = verificationId
        }
    }

    fun sendSmsCode(
        userRegistration: UserRegistration,
        activity: AppCompatActivity
    ): Completable {
        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(userRegistration.phone)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(activity)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        authState.onNext(UserAuthState.SMS_START_CODE_SENT)
        userRegistrationSubject.onNext(userRegistration)
        PhoneAuthProvider.verifyPhoneNumber(options)
        return signUp()
    }

    fun validateSmsCode(code: String) =
        credentialSubject.onNext(PhoneAuthProvider.getCredential(verificationId!!, code))

    private fun signUp() =
        Observable.combineLatest(
            credentialSubject,
            userRegistrationSubject,
            ::Pair
        )
            .flatMapCompletable { (credential, userRegistration) ->
                RxFirebaseAuth.signInWithCredential(firebaseAuth, credential)
                    .flatMapCompletable { auth ->
                        when (userRegistration) {
                            is UserRegistration.Login -> {
                                authState.onNext(UserAuthState.LOGGED_IN)
                                Completable.complete()
                            }
                            is UserRegistration.SignUp -> safeUser(
                                userRegistration,
                                auth.user.requiredUid()
                            )
                        }

                    }
            }

    fun user() = RxFirestore.getDocument(
        users.document(firebaseAuth.requiredCurrentUserUid()),
        User::class.java
    )

    fun userFlowable() = RxFirestore.observeDocumentRef(
        users.document(firebaseAuth.requiredCurrentUserUid()),
        User::class.java
    )

    private fun safeUser(
        userRegistration: UserRegistration.SignUp,
        id: String
    ): Completable {
        val ref = imageStoreReference.child(
            userRegistration.avatarUri.lastPathSegment ?: UUID.randomUUID()
                .toString()
        )
        return RxFirebaseStorage.putFile(ref, userRegistration.avatarUri)
            .flatMapMaybe {
                RxFirebaseStorage.getDownloadUrl(ref)
            }
            .flatMapCompletable {
                val user = User(
                    id,
                    userRegistration.firstName,
                    userRegistration.secondName,
                    userRegistration.phone,
                    it.toString()
                )

                RxFirestore.setDocument(users.document(id), user)
                    .doOnComplete {
                        authState.onNext(UserAuthState.REGISTERED)
                    }
            }
    }

    fun updateRefrigerator(pickedIds: List<String>) =
        RxFirestore.updateDocument(
            users.document(firebaseAuth.requiredCurrentUserUid()),
            "refrigerator",
            pickedIds
        )

    fun updateStopList(pickedIds: List<String>) =
        RxFirestore.updateDocument(
            users.document(firebaseAuth.requiredCurrentUserUid()),
            "stopList",
            pickedIds
        )

    fun signOut() = firebaseAuth.signOut()

    fun isUserAuthenticated() = firebaseAuth.currentUser != null
    fun updateFavoriteRecipe(id: String) =
        user()
            .map(User::favorites)
            .map { recipes ->
                if (recipes.contains(id))
                    recipes.filter { recipeId -> recipeId != id }
                else recipes.plus(id)
            }
            .flatMapCompletable {
                RxFirestore.updateDocument(
                    users.document(firebaseAuth.requiredCurrentUserUid()),
                    "favorites",
                    it
                )
            }

    enum class UserAuthState {

        SMS_CODE_NOT_SENT,
        SMS_START_CODE_SENT,
        SMS_END_CODE_SENT,
        SMS_CODE_VERIFICATION,
        USER_NOT_REGISTERED_YET,
        REGISTERED,
        LOGGED_IN

    }
}