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
import com.sunasterisk.moviedb_51.data.model.MovieRecent
import com.sunasterisk.moviedb_51.databinding.ItemMovieRecentBinding
import kotlinx.android.synthetic.main.item_movie_recent.view.*

class MovieRecentAdapter :
    BaseRecyclerAdapter<MovieRecent, MovieRecentAdapter.ViewHolder>(MovieDiffUtilCallback()) {
    var onItemClick: (MovieRecent) -> Unit = { _ -> }

    override fun getItemViewType(position: Int) = R.layout.item_movie_recent

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemMovieRecentBinding =
            DataBindingUtil.inflate(layoutInflater, viewType, parent, false)
        return ViewHolder(binding, onItemClick)
    }

    class ViewHolder(
        itemView: ViewDataBinding,
        private val onItemClick: (MovieRecent) -> Unit
    ) : BaseViewHolder<MovieRecent>(itemView) {

        override fun onBindData(itemData: MovieRecent) {
            super.onBindData(itemData)
            itemView.movieRecentCardView.setOnClickListener {
                onItemClick(itemData)
            }
        }

        override fun getVariableID() = BR.movieRecent
    }

    class MovieDiffUtilCallback : DiffUtil.ItemCallback<MovieRecent>() {
        override fun areItemsTheSame(oldItem: MovieRecent, newItem: MovieRecent): Boolean =
            oldItem.movieID == newItem.movieID

        override fun areContentsTheSame(oldItem: MovieRecent, newItem: MovieRecent): Boolean =
            oldItem == newItem
    }
}
