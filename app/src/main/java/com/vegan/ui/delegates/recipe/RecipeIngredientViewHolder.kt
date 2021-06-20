package com.vegan.ui.delegates.recipe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.vegan.R
import com.vegan.databinding.ItemRecipeIngredientBinding
import com.vegan.models.RecipeModel
import com.vegan.ui.delegateadapter.BindableViewHolder


class RecipeIngredientViewHolder private constructor(
    private val binding: ItemRecipeIngredientBinding,
) : RecyclerView.ViewHolder(binding.root), BindableViewHolder<RecipeModel.Ingredient> {

    override fun bind(item: RecipeModel.Ingredient, position: Int, payloads: MutableList<Any>) {
        binding.itemIngredientName.text = item.name
        binding.itemIngredientCount.text = item.count
        binding.itemIngredientLine.isVisible = item.count != null
    }

    companion object {
        fun create(inflater: LayoutInflater, parent: ViewGroup) =
            RecipeIngredientViewHolder(ItemRecipeIngredientBinding.inflate(inflater, parent, false))
    }
}