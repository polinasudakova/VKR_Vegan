package com.vegan.ui.delegates.product

import androidx.recyclerview.widget.RecyclerView
import com.vegan.databinding.ItemProductBinding
import com.vegan.models.ProductModel
import com.vegan.ui.delegateadapter.BindableViewHolder


class ProductViewHolder(
    private val binding: ItemProductBinding,
    private val onCheckedChange: (ProductModel) -> Unit
) : RecyclerView.ViewHolder(binding.root), BindableViewHolder<ProductModel> {

    override fun bind(item: ProductModel, position: Int, payloads: MutableList<Any>) {
        binding.productCheckbox.setOnCheckedChangeListener(null)
        binding.productCheckbox.isChecked = item.isPicked
        binding.productCheckbox.setOnCheckedChangeListener { _, _ ->
            onCheckedChange(item)
        }
        binding.root.setOnClickListener {
            onCheckedChange(item)
        }
        binding.productName.text = item.product.name
    }
}