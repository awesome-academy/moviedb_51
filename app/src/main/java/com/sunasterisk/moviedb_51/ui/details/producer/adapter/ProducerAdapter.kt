package com.sunasterisk.moviedb_51.ui.details.producer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.sunasterisk.moviedb_51.BR
import com.sunasterisk.moviedb_51.R
import com.sunasterisk.moviedb_51.base.BaseRecyclerAdapter
import com.sunasterisk.moviedb_51.base.BaseViewHolder
import com.sunasterisk.moviedb_51.data.model.Producer
import com.sunasterisk.moviedb_51.databinding.ItemProducerBinding

class ProducerAdapter :
    BaseRecyclerAdapter<Producer, ProducerAdapter.ViewHolder>(MovieDiffUtilCallback()) {
    var onItemClick: (Producer) -> Unit = { _ -> }

    override fun getItemViewType(position: Int) = R.layout.item_producer

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ItemProducerBinding>(layoutInflater, viewType, parent, false)
        return ViewHolder(binding, onItemClick)
    }

    class ViewHolder(
        itemView: ItemProducerBinding,
        private var onItemClick: (Producer) -> Unit
    ) : BaseViewHolder<Producer>(itemView) {

        override fun onItemClickListener(itemData: Producer) = onItemClick(itemData)

        override fun getVariableID() = BR.producer
    }

    class MovieDiffUtilCallback : DiffUtil.ItemCallback<Producer>() {
        override fun areItemsTheSame(oldItem: Producer, newItem: Producer): Boolean =
            oldItem.produceID == newItem.produceID

        override fun areContentsTheSame(oldItem: Producer, newItem: Producer): Boolean =
            oldItem == newItem
    }
}
