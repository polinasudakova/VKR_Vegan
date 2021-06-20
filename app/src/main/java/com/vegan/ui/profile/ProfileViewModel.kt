package com.vegan.ui.profile

import androidx.lifecycle.MutableLiveData
import com.vegan.data.UserRepository
import com.vegan.models.User
import com.vegan.ui.base.BaseViewModel
import com.vegan.ui.base.addTo
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    fun onSignoutClick() {
        userRepository.signOut()
        screenState.value = null
    }

    val screenState = MutableLiveData<User?>()

    init {
        userRepository.user()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(screenState::setValue)
            .addTo(compositeDisposable)
    }


}