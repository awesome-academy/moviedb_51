package com.sunasterisk.moviedb_51.ui.details

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment

class MovieDetailsFragment : Fragment() {

    companion object {
        private const val ARGUMENT_MOVIE_ID = "ARGUMENT_MOVIE_ID"
        private const val ARGUMENT_MOVIE_TITLE = "ARGUMENT_MOVIE_ID"

        fun getInstance(id: Int, title: String) = MovieDetailsFragment().apply {
            arguments = bundleOf(ARGUMENT_MOVIE_ID to id, ARGUMENT_MOVIE_TITLE to title)
        }
    }
}
