package com.sunasterisk.moviedb_51.ui.movies

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment

class MoviesFragment : Fragment() {

    companion object {
        private const val ARGUMENT_QUERY = "ARGUMENT_QUERY"
        private const val ARGUMENT_TITLE = "ARGUMENT_TITLE"
        private const val ARGUMENT_TYPES = "ARGUMENT_TYPES"

        fun getInstance(type: String, query: String, title: String) =
            MoviesFragment().apply {
                arguments = bundleOf(
                    ARGUMENT_TYPES to type,
                    ARGUMENT_QUERY to query,
                    ARGUMENT_TITLE to title
                )
            }
    }
}
