package com.sunasterisk.moviedb_51.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.sunasterisk.moviedb_51.BR
import com.sunasterisk.moviedb_51.R
import com.sunasterisk.moviedb_51.base.BaseRecyclerAdapter
import com.sunasterisk.moviedb_51.base.BaseViewHolder
import com.sunasterisk.moviedb_51.data.model.Genres
import com.sunasterisk.moviedb_51.databinding.ItemGenreBinding

class GenreAdapter : BaseRecyclerAdapter<Genres, GenreAdapter.ViewHolder>(MovieDiffUtilCallback()) {
    var onItemClick: (Genres) -> Unit = { _ -> }

    override fun getItemViewType(position: Int) = R.layout.item_genre

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ItemGenreBinding>(layoutInflater, viewType, parent, false)
        return ViewHolder(binding, onItemClick)
    }

    class ViewHolder(
        itemView: ViewDataBinding,
        private var onItemClick: (Genres) -> Unit
    ) : BaseViewHolder<Genres>(itemView) {

        override fun onItemClickListener(itemData: Genres) = onItemClick(itemData)

        override fun getVariableID() = BR.genre
    }

    class MovieDiffUtilCallback : DiffUtil.ItemCallback<Genres>() {
        override fun areItemsTheSame(oldItem: Genres, newItem: Genres): Boolean =
            oldItem.genresID == newItem.genresID

        override fun areContentsTheSame(oldItem: Genres, newItem: Genres): Boolean =
            oldItem == newItem
    }
}
