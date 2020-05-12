package com.sunasterisk.moviedb_51.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(
    private val binding: ViewDataBinding
) : RecyclerView.ViewHolder(binding.root) {
    private var itemData: T? = null

    init {
        itemView.setOnClickListener {
            itemData?.let { onItemClickListener(it) }
        }
    }

    open fun onBindData(itemData: T) {
        binding.setVariable(getVariableID(), itemData)
        binding.executePendingBindings()
        this.itemData = itemData
    }

    protected abstract fun getVariableID(): Int

    open fun onItemClickListener(itemData: T) {}
}
