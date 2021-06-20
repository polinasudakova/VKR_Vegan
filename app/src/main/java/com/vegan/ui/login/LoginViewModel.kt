package com.vegan.ui.login

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.vegan.data.UserRepository
import com.vegan.models.UserRegistration
import com.vegan.ui.base.BaseViewModel
import com.vegan.ui.base.addTo
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    val screenState = MutableLiveData<UserRepository.UserAuthState>()

    init {
        userRepository.authState
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(screenState::setValue)
            .addTo(compositeDisposable)
    }

    fun sendSmsCode(
        phone: String,
        requireActivity: AppCompatActivity
    ) {
        userRepository.sendSmsCode(
            UserRegistration.Login(phone),
            requireActivity
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
            .addTo(compositeDisposable)
    }

    fun validateSmsCode(code: String) {
        userRepository.validateSmsCode(code)
    }

}