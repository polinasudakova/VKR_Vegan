package com.vegan.ui.delegates.product

import android.view.LayoutInflater
import android.view.ViewGroup
import com.vegan.databinding.ItemProductBinding
import com.vegan.models.ProductModel
import com.vegan.ui.delegateadapter.ViewDelegate


class ProductViewDelegate(
    private val onCheckedChange: (ProductModel) -> Unit
) : ViewDelegate<ProductViewHolder, ProductModel> {

    override fun isForViewType(item: ProductModel, adapterPosition: Int) = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ProductViewHolder(
        ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        onCheckedChange
    )

}