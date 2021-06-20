package com.vegan.ui.recipes

import androidx.lifecycle.MutableLiveData
import com.vegan.domain.RecipeInteractor
import com.vegan.models.FavoriteRecipeModel
import com.vegan.ui.base.BaseViewModel
import com.vegan.ui.base.addTo
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val recipeInteractor: RecipeInteractor
) : BaseViewModel() {

    fun onFavoriteClick(recipeModel: FavoriteRecipeModel) {
        recipeInteractor.saveToFavorite(recipeModel.recipe.id)
            .delaySubscription(600, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { screenProgress.value = true }
            .subscribe()
            .addTo(compositeDisposable)
    }

    val screenState = MutableLiveData<List<FavoriteRecipeModel>>()

    val screenProgress = MutableLiveData<Boolean>()


    fun startObserveUserRecipes(isFavoritesScreen: Boolean) {
        val recipes = recipeInteractor.run {
            if (isFavoritesScreen) userFavoriteRecipes() else userRecipes()
        }

        recipes
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { screenProgress.value = true }
            .doOnNext { screenProgress.value = false }
            .subscribe(screenState::setValue)
            .addTo(compositeDisposable)
    }

}