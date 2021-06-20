package com.vegan.ui.delegates.recipe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vegan.databinding.ItemRecipeDescriptionBinding
import com.vegan.databinding.ItemRecipeTitleBinding
import com.vegan.models.RecipeModel
import com.vegan.ui.delegateadapter.BindableViewHolder


class RecipeTitleViewHolder private constructor(
    private val binding: ItemRecipeTitleBinding,
) : RecyclerView.ViewHolder(binding.root), BindableViewHolder<RecipeModel.Title> {

    override fun bind(item: RecipeModel.Title, position: Int, payloads: MutableList<Any>) {
        binding.itemRecipeTitle.text = item.text
    }

    companion object {
        fun create(inflater: LayoutInflater, parent: ViewGroup) =
            RecipeTitleViewHolder(ItemRecipeTitleBinding.inflate(inflater, parent, false))
    }
}