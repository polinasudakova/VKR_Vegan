package com.vegan.ui.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.vegan.R
import com.vegan.databinding.FragmentRecipesBinding
import com.vegan.models.FavoriteRecipeModel
import com.vegan.models.itemcallback.RecipeModelItemCallback
import com.vegan.ui.delegateadapter.DelegateAdapter
import com.vegan.ui.delegates.recipe.RecipeViewDelegate
import com.vegan.ui.recipe.RecipeFragment
import com.vegan.ui.view.ScreenStateController
import dagger.hilt.android.AndroidEntryPoint

private const val IS_FAVORITES_ARG = "IS_FAVORITES_ARG"

@AndroidEntryPoint
class RecipesFragment : Fragment() {

    lateinit var binding: FragmentRecipesBinding
    private lateinit var stateController: ScreenStateController

    private val viewModel: RecipesViewModel by viewModels()

    private lateinit var adapter: DelegateAdapter<FavoriteRecipeModel>

    val isFavorite by lazy { requireArguments().getBoolean(IS_FAVORITES_ARG) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.startObserveUserRecipes(isFavorite)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentRecipesBinding.inflate(inflater)
        .also {
            binding = it
            stateController =
                ScreenStateController(it.recipesProgress.progress, it.recipesContent)
        }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.recipesToolbar) {
            title = if (isFavorite) "Избранные рецепты" else "Рецепты"
            if (isFavorite) setNavigationIcon(R.drawable.ic_arrow) else navigationIcon = null
            setNavigationOnClickListener {
                requireActivity().onBackPressed()
            }
        }
        adapter = DelegateAdapter(
            RecipeModelItemCallback(),
            listOf(
                RecipeViewDelegate(viewModel::onFavoriteClick, ::onRecipeClick)
            )
        )
        binding.recipesRecyclerView.adapter = adapter
        observeData()
    }

    private fun onRecipeClick(recipeModel: FavoriteRecipeModel) {
        val args = Bundle().apply {
            putString(RecipeFragment.RECIPE_ID_ARG, recipeModel.recipe.id)
        }
        findNavController().navigate(R.id.recipe, args)
    }

    private fun observeData() {
        viewModel.screenState.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
        viewModel.screenProgress.observe(viewLifecycleOwner, {
            if (it) stateController.showProgress() else stateController.showContent()
        })
    }

}