package com.vegan.ui.splash

import androidx.lifecycle.MutableLiveData
import com.vegan.data.UserRepository
import com.vegan.ui.base.BaseViewModel
import com.vegan.ui.base.addTo
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
    userRepository: UserRepository
) : BaseViewModel() {

    val screenState = MutableLiveData<Boolean>()

    init {
        Single.just(userRepository.isUserAuthenticated())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .delaySubscription(2, TimeUnit.SECONDS)
            .subscribe(screenState::setValue)
            .addTo(compositeDisposable)
    }

}