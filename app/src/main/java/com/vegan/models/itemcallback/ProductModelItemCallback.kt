package com.vegan.models.itemcallback

import androidx.recyclerview.widget.DiffUtil
import com.vegan.models.ProductModel

class ProductModelItemCallback : DiffUtil.ItemCallback<ProductModel>() {
    override fun areItemsTheSame(oldItem: ProductModel, newItem: ProductModel) =
        oldItem.product.id == newItem.product.id

    override fun areContentsTheSame(oldItem: ProductModel, newItem: ProductModel) =
        oldItem.isPicked == newItem.isPicked
}