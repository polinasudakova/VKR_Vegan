package com.vegan.ui.delegates.recipe

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.vegan.R
import com.vegan.databinding.ItemRecipeBinding
import com.vegan.models.FavoriteRecipeModel
import com.vegan.ui.delegateadapter.BindableViewHolder


class RecipeViewHolder(
    private val binding: ItemRecipeBinding,
    private val onFavoriteChange: (FavoriteRecipeModel) -> Unit,
    private val onRecipeClick: (FavoriteRecipeModel) -> Unit,
) : RecyclerView.ViewHolder(binding.root), BindableViewHolder<FavoriteRecipeModel> {

    override fun bind(item: FavoriteRecipeModel, position: Int, payloads: MutableList<Any>) {
        binding.root.setOnClickListener {
            onRecipeClick(item)
        }
        binding.recipeFavorite.setOnClickListener(null)
        binding.recipeFavorite.setImageResource(
            if (item.isFaforite) R.drawable.ic_star else R.drawable.ic_star_empty
        )
        binding.recipeName.text = "${item.recipe.name} ${item.recipe.calorieContent}"
        binding.recipeImage.load(item.recipe.imageUrl)
        binding.recipeFavorite.setOnClickListener {
            onFavoriteChange(item)
        }
    }
}