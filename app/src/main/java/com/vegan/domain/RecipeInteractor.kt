package com.vegan.domain

import com.vegan.data.ProductsRepository
import com.vegan.data.RecipeRepository
import com.vegan.data.UserRepository
import com.vegan.models.FavoriteRecipeModel
import io.reactivex.Flowable
import javax.inject.Inject


class RecipeInteractor @Inject constructor(
    private val userRepository: UserRepository,
    private val recipeRepository: RecipeRepository,
    private val productRepository: ProductsRepository
) {

    fun userRecipes(): Flowable<List<FavoriteRecipeModel>> = Flowable.combineLatest(
        userRepository.userFlowable(),
        recipeRepository.recipes().toFlowable(),
        { user, recipes ->
            recipes.mapNotNull { recipe ->
                if (recipe.ingredients.keys.intersect(user.stopList).isNotEmpty())
                    return@mapNotNull null
                FavoriteRecipeModel(
                    recipe,
                    user.favorites.contains(recipe.id)
                )
            }
        }
    )

    fun userFavoriteRecipes() = userRecipes().map { it.filter(FavoriteRecipeModel::isFaforite) }

    fun recipe(id: String): Flowable<FavoriteRecipeModel> =
        Flowable.combineLatest(
            userRecipes()
                .map { recipes -> recipes.first { recipeModel -> recipeModel.recipe.id == id } },
            productRepository.refrigeratorProducts().toFlowable(),
            { recipe, products ->
                val namedIngredients = recipe.recipe.ingredients
                    .map { (id, value) ->
                        (products.find { it.id == id }?.name ?: id) to value
                    }
                    .toMap()
                recipe.copy(recipe = recipe.recipe.copy(ingredients = namedIngredients))
            }
        )


    fun saveStopList(pickedIds: List<String>) = userRepository.updateStopList(pickedIds)

    fun saveToFavorite(id: String) = userRepository.updateFavoriteRecipe(id)

}