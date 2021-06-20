package com.vegan.ui.refrigerator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.vegan.R
import com.vegan.databinding.FragmentProductBinding
import com.vegan.models.ProductModel
import com.vegan.models.itemcallback.ProductModelItemCallback
import com.vegan.ui.delegateadapter.DelegateAdapter
import com.vegan.ui.delegates.product.ProductViewDelegate
import com.vegan.ui.view.ScreenStateController
import dagger.hilt.android.AndroidEntryPoint

private const val IS_REFRIGERATOR_ARG = "IS_REFRIGERATOR_ARG"

@AndroidEntryPoint
class ProductFragment : Fragment() {

    lateinit var binding: FragmentProductBinding
    private lateinit var stateController: ScreenStateController

    private val viewModel: RefrigeratorViewModel by viewModels()

    private lateinit var adapter: DelegateAdapter<ProductModel>

    private val isRefrigerator: Boolean by lazy {
        requireArguments().getBoolean(IS_REFRIGERATOR_ARG)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentProductBinding.inflate(inflater)
        .also {
            binding = it
            stateController =
                ScreenStateController(it.productProgress.progress, it.refrigeratorContent)
        }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = DelegateAdapter(
            ProductModelItemCallback(),
            listOf(ProductViewDelegate(viewModel::onProductClick))
        )
        binding.productToolbar.title = if (isRefrigerator) "Мой холодильник" else "Мой стоп-лист"
        binding.productDescription.setText(if (isRefrigerator) R.string.refrigerator_description else R.string.stoplist_description)
        binding.productRecyclerView.adapter = adapter
        stateController.showContent()
        binding.productSave.setOnClickListener {
            viewModel.onSaveButtonClick(isRefrigerator)
        }
        binding.productToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        observeData()
        viewModel.loadUserProducts(isRefrigerator)
    }

    private fun observeData() {
        viewModel.screenState.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        viewModel.progressState.observe(viewLifecycleOwner) { state ->
            when (state) {
                RefrigeratorViewModel.ScreenState.SHOW_LOADING_CONTENT_PROGRESS -> stateController.showProgress()
                RefrigeratorViewModel.ScreenState.SHOW_CONTENT -> stateController.showContent()
                RefrigeratorViewModel.ScreenState.SHOW_SAVE_PROGRESS -> stateController.showProgress()
                RefrigeratorViewModel.ScreenState.SAVED -> findNavController().popBackStack()
            }
        }
        viewModel.saveButtonState.observe(viewLifecycleOwner) {
            binding.productSave.isEnabled = it
        }
    }
}