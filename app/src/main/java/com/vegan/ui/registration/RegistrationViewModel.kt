package com.vegan.ui.registration

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.vegan.data.UserRepository
import com.vegan.extentions.toast
import com.vegan.models.UserRegistration
import com.vegan.ui.base.BaseViewModel
import com.vegan.ui.base.addTo
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject


@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    val screenState = MutableLiveData<UserRepository.UserAuthState>()

    private val avatarUriSubject = BehaviorSubject.create<Uri>()
    private val userInfoSubject = BehaviorSubject.create<Uri>()

    init {
        userRepository.authState
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(screenState::setValue)
            .addTo(compositeDisposable)
    }

    fun sendSmsCode(
        phone: String,
        firstName: String,
        secondName: String,
        requireActivity: AppCompatActivity
    ) {
        val avatarUri = avatarUriSubject.value
        if (avatarUri != null) {
            userRepository.sendSmsCode(
                UserRegistration.SignUp(phone, firstName, secondName, avatarUri),
                requireActivity
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
                .addTo(compositeDisposable)
        }
    }

    fun validateSmsCode(code: String) {
        userRepository.validateSmsCode(code)
    }

    fun onPhotoPicked(fileUri: Uri) = avatarUriSubject.onNext(fileUri)

}