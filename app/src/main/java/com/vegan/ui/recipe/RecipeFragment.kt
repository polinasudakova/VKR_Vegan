package com.vegan.ui.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.vegan.R
import com.vegan.databinding.FragmentRecipeBinding
import com.vegan.ui.view.ScreenStateController
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RecipeFragment : Fragment() {
    companion object {
        const val RECIPE_ID_ARG = "RECIPE_ID_ARG"
    }

    lateinit var binding: FragmentRecipeBinding
    private lateinit var stateController: ScreenStateController

    private val viewModel: RecipeViewModel by viewModels()

    private val recipeId: String by lazy(LazyThreadSafetyMode.NONE) {
        requireArguments().getString(RECIPE_ID_ARG)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentRecipeBinding.inflate(inflater)
        .also {
            binding = it
            stateController =
                ScreenStateController(it.recipeProgress.progress, it.recipeRecyclerView)
        }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbarListeners()
        observeData()
        viewModel.recipe(recipeId)
    }

    private fun initToolbarListeners() {
        with(binding.recipeToolbar) {
            setNavigationOnClickListener { requireActivity().onBackPressed() }
            setOnMenuItemClickListener {
                viewModel.onFavoriteClick(recipeId)
                true
            }
        }
    }

    private fun observeData() {
        viewModel.screenProgress.observe(viewLifecycleOwner) {
            if (it) stateController.showProgress() else stateController.showContent()
        }
        viewModel.screenState.observe(viewLifecycleOwner) { (recipe, models) ->
            binding.apply {
                recipeToolbarLayout.title = recipe.recipe.name
                recipeImage.load(recipe.recipe.imageUrl)
                recipeToolbar.menu.findItem(R.id.favorite).setIcon(
                    if (recipe.isFaforite) R.drawable.ic_star else R.drawable.ic_star_empty
                )
                recipeRecyclerView.adapter = RecipeAdapter(models)
            }

        }
    }

}