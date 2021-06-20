package com.vegan.ui.recipe

import androidx.lifecycle.MutableLiveData
import com.vegan.domain.RecipeInteractor
import com.vegan.models.FavoriteRecipeModel
import com.vegan.models.RecipeModel
import com.vegan.ui.base.BaseViewModel
import com.vegan.ui.base.addTo
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val recipeInteractor: RecipeInteractor
) : BaseViewModel() {

    fun onFavoriteClick(id: String) {
        recipeInteractor.saveToFavorite(id)
            .delaySubscription(600, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { screenProgress.value = true }
            .doOnComplete { screenProgress.value = false }
            .subscribe()
            .addTo(compositeDisposable)
    }

    fun recipe(id: String) {
        recipeInteractor.recipe(id)
            .distinctUntilChanged { it -> it.isFaforite }
            .map(::convertToModel)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { screenProgress.value = true }
            .doOnNext { screenProgress.value = false }
            .subscribe(screenState::setValue)
            .addTo(compositeDisposable)
    }

    private fun convertToModel(favoriteRecipeModel: FavoriteRecipeModel): Pair<FavoriteRecipeModel, List<RecipeModel>> {
        val ingredients = favoriteRecipeModel.recipe.ingredients.map { (name, value) ->
            RecipeModel.Ingredient(name, value.takeIf(String::isNotEmpty))
        }
        val methods = favoriteRecipeModel.recipe.cookingMethod.map(RecipeModel::Description)
        val ingredientsTitle = RecipeModel.Title("Ингредиенты")
        val methodsTitle = RecipeModel.Title("Способ приготовления")
        return favoriteRecipeModel to
                listOf(ingredientsTitle)
                    .plus(ingredients)
                    .plus(methodsTitle)
                    .plus(methods)
    }

    val screenState = MutableLiveData<Pair<FavoriteRecipeModel, List<RecipeModel>>>()

    val screenProgress = MutableLiveData<Boolean>()

}