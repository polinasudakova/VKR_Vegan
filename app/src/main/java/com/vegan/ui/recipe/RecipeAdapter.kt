package com.vegan.ui.recipe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vegan.models.RecipeModel
import com.vegan.ui.delegates.recipe.RecipeDescriptionViewHolder
import com.vegan.ui.delegates.recipe.RecipeIngredientViewHolder
import com.vegan.ui.delegates.recipe.RecipeTitleViewHolder


class RecipeAdapter(models: List<RecipeModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val differ = AsyncListDiffer(this, RecipeModelItemCallback())

    init {
        differ.submitList(models)
    }

    override fun getItemViewType(position: Int): Int {
        return when (differ.currentList[position]) {
            is RecipeModel.Description -> ViewType.DESCRIPTION.ordinal
            is RecipeModel.Ingredient -> ViewType.INGREDIENT.ordinal
            is RecipeModel.Title -> ViewType.TITLE.ordinal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (ViewType.values()[viewType]) {
            ViewType.DESCRIPTION -> RecipeDescriptionViewHolder.create(inflater, parent)
            ViewType.INGREDIENT -> RecipeIngredientViewHolder.create(inflater, parent)
            ViewType.TITLE -> RecipeTitleViewHolder.create(inflater, parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RecipeDescriptionViewHolder -> holder.bind(
                differ.currentList[position] as RecipeModel.Description,
                position,
                mutableListOf()
            )
            is RecipeIngredientViewHolder -> holder.bind(
                differ.currentList[position] as RecipeModel.Ingredient,
                position,
                mutableListOf()
            )
            is RecipeTitleViewHolder -> holder.bind(
                differ.currentList[position] as RecipeModel.Title,
                position,
                mutableListOf()
            )
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    private class RecipeModelItemCallback : DiffUtil.ItemCallback<RecipeModel>() {
        override fun areItemsTheSame(oldItem: RecipeModel, newItem: RecipeModel) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: RecipeModel, newItem: RecipeModel) = true
    }

    enum class ViewType {
        DESCRIPTION,
        INGREDIENT,
        TITLE
    }
}