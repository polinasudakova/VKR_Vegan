package com.vegan.models.itemcallback

import androidx.recyclerview.widget.DiffUtil
import com.vegan.models.FavoriteRecipeModel

class RecipeModelItemCallback : DiffUtil.ItemCallback<FavoriteRecipeModel>() {
    override fun areItemsTheSame(oldItem: FavoriteRecipeModel, newItem: FavoriteRecipeModel) =
        oldItem.recipe.id == newItem.recipe.id

    override fun areContentsTheSame(oldItem: FavoriteRecipeModel, newItem: FavoriteRecipeModel) =
        oldItem.isFaforite == newItem.isFaforite
}