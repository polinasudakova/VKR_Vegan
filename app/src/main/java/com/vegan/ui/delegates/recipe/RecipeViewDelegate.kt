package com.vegan.ui.delegates.recipe

import android.view.LayoutInflater
import android.view.ViewGroup
import com.vegan.databinding.ItemRecipeBinding
import com.vegan.models.FavoriteRecipeModel
import com.vegan.ui.delegateadapter.ViewDelegate


class RecipeViewDelegate(
    private val onFavoriteChange: (FavoriteRecipeModel) -> Unit,
    private val onRecipeClick: (FavoriteRecipeModel) -> Unit,
) : ViewDelegate<RecipeViewHolder, FavoriteRecipeModel> {

    override fun isForViewType(item: FavoriteRecipeModel, adapterPosition: Int) = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RecipeViewHolder(
        ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        onFavoriteChange,
        onRecipeClick
    )

}