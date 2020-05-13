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
import com.sunasterisk.moviedb_51.data.model.Movie
import com.sunasterisk.moviedb_51.databinding.ItemMovieByCategoryBinding

class MovieCategoryAdapter :
    BaseRecyclerAdapter<Movie, MovieCategoryAdapter.ViewHolder>(MovieDiffUtilCallback()) {

    override fun getItemViewType(position: Int) = R.layout.item_movie_by_category

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ItemMovieByCategoryBinding>(
                layoutInflater,
                viewType,
                parent,
                false
            )
        return ViewHolder(binding)
    }

    class ViewHolder(
        itemView: ViewDataBinding
    ) : BaseViewHolder<Movie>(itemView) {

        override fun getVariableID() = BR.movie
    }

    class MovieDiffUtilCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
            oldItem.movieID == newItem.movieID

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
            oldItem == newItem
    }
}
