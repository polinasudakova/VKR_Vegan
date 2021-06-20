package com.vegan.ui.delegates.recipe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vegan.databinding.ItemRecipeDescriptionBinding
import com.vegan.models.RecipeModel
import com.vegan.ui.delegateadapter.BindableViewHolder


class RecipeDescriptionViewHolder private constructor(
    private val binding: ItemRecipeDescriptionBinding,
) : RecyclerView.ViewHolder(binding.root), BindableViewHolder<RecipeModel.Description> {

    override fun bind(item: RecipeModel.Description, position: Int, payloads: MutableList<Any>) {
        binding.itemRecipeDescription.text = item.text
    }

    companion object {
        fun create(inflater: LayoutInflater, parent: ViewGroup) =
            RecipeDescriptionViewHolder(ItemRecipeDescriptionBinding.inflate(inflater, parent, false))
    }
}