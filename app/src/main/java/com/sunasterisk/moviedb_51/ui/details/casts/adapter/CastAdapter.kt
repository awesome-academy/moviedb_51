package com.sunasterisk.moviedb_51.ui.details.casts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.sunasterisk.moviedb_51.BR
import com.sunasterisk.moviedb_51.R
import com.sunasterisk.moviedb_51.base.BaseRecyclerAdapter
import com.sunasterisk.moviedb_51.base.BaseViewHolder
import com.sunasterisk.moviedb_51.data.model.CastAttribute
import com.sunasterisk.moviedb_51.databinding.ItemCastBinding

class CastAdapter :
    BaseRecyclerAdapter<CastAttribute, CastAdapter.ViewHolder>(MovieDiffUtilCallback()) {
    var onItemClick: (CastAttribute) -> Unit = { _ -> }

    override fun getItemViewType(position: Int) = R.layout.item_cast

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ItemCastBinding>(layoutInflater, viewType, parent, false)
        return ViewHolder(binding, onItemClick)
    }

    class ViewHolder(
        itemView: ItemCastBinding,
        private var onItemClick: (CastAttribute) -> Unit
    ) : BaseViewHolder<CastAttribute>(itemView) {

        override fun onItemClickListener(itemData: CastAttribute) = onItemClick(itemData)

        override fun getVariableID() = BR.cast
    }

    class MovieDiffUtilCallback : DiffUtil.ItemCallback<CastAttribute>() {
        override fun areItemsTheSame(oldItem: CastAttribute, newItem: CastAttribute): Boolean =
            oldItem.castId == newItem.castId

        override fun areContentsTheSame(oldItem: CastAttribute, newItem: CastAttribute): Boolean =
            oldItem == newItem
    }
}
