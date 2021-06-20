package com.vegan.ui.delegateadapter

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class DelegateAdapter<T>(
    diffUtil: DiffUtil.ItemCallback<T>,
    private val delegates: List<ViewDelegate<*, T>>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val differ = AsyncListDiffer(this, diffUtil)
    var onItemClick: ((T) -> Unit)? = null

    override fun getItemViewType(position: Int): Int {
        return delegates.indexOfFirst { viewDelegate ->
            viewDelegate.isForViewType(
                differ.currentList[position],
                position
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        delegates[viewType].onCreateViewHolder(parent, viewType)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) = Unit

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) {
        val item = differ.currentList[position]
        holder.itemView.setOnClickListener { onItemClick?.invoke(item) }
        (holder as BindableViewHolder<T>).bind(item, position, payloads)
    }

    override fun getItemCount() = differ.currentList.size

    fun submitList(newItems: List<T>) = differ.submitList(newItems)

}
