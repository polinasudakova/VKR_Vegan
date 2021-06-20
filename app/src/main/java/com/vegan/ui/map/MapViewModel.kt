package com.vegan.ui.map

import androidx.lifecycle.MutableLiveData
import com.vegan.data.RestaurantsRepository
import com.vegan.models.FavoriteRecipeModel
import com.vegan.models.RecipeModel
import com.vegan.ui.base.BaseViewModel
import com.vegan.ui.base.addTo
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


@HiltViewModel
class MapViewModel @Inject constructor(
    private val restaurantsRepository: RestaurantsRepository
) : BaseViewModel() {

    init {
/*
        restaurantsRepository.restaurants()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { screenProgress.value = true }
            .doOnSuccess { screenProgress.value = false }
            .subscribe()
            .addTo(compositeDisposable)
*/
    }

    val screenState = MutableLiveData<Pair<FavoriteRecipeModel, List<RecipeModel>>>()

    val screenProgress = MutableLiveData<Boolean>()

}