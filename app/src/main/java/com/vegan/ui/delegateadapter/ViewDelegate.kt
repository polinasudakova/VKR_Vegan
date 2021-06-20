package com.vegan.ui.delegateadapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


interface ViewDelegate<VH, T> where VH : RecyclerView.ViewHolder, VH : BindableViewHolder<T> {

    fun isForViewType(item: T, adapterPosition: Int): Boolean

    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH

}

interface BindableViewHolder<T> {

    fun bind(item: T, position: Int, payloads: MutableList<Any>)

}
