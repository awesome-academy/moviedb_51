package com.sunasterisk.moviedb_51.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sunasterisk.moviedb_51.R
import com.sunasterisk.moviedb_51.data.base.ViewModelFactory
import com.sunasterisk.moviedb_51.data.repository.MovieRepository
import com.sunasterisk.moviedb_51.data.source.local.MovieLocalDataSource
import com.sunasterisk.moviedb_51.data.source.local.MoviesDatabase
import com.sunasterisk.moviedb_51.data.source.remote.MovieRemoteDataSource
import com.sunasterisk.moviedb_51.data.source.remote.api.MovieFactory
import com.sunasterisk.moviedb_51.databinding.FragmentMoviesBinding
import com.sunasterisk.moviedb_51.utils.Constant

class MoviesFragment : Fragment() {
    private lateinit var viewModel: MoviesViewModel
    private lateinit var binding: FragmentMoviesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let {
            val movieRepository =
                MovieRepository.getInstance(
                    MovieRemoteDataSource.getInstance(MovieFactory(it).instance),
                    MovieLocalDataSource.getInstance(MoviesDatabase.getInstance(it))
                )
            viewModel = ViewModelProvider(
                this,
                ViewModelFactory { MoviesViewModel(movieRepository) })[MoviesViewModel::class.java]
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getMovies()
        observeViewModel()
    }

    private fun observeViewModel() = with(viewModel) {
        dataResponse.observe(viewLifecycleOwner, Observer {
            //TODO get MovieResponse
        })
        isLoaded.observe(viewLifecycleOwner, Observer {
            //TODO handle loaded
        })
        messageError.observe(viewLifecycleOwner, Observer {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        })
        showEmptyMovies.observe(viewLifecycleOwner, Observer {
            //TODO handle show Empty Movies
        })
    }

    private fun getMovies() {
        arguments?.run {
            val type = getString(ARGUMENT_TYPES)
            val query = getString(ARGUMENT_QUERY)
            if (type.isNullOrEmpty() || query.isNullOrEmpty()) return
            viewModel.getMovies(type, query, Constant.BASE_COUNT_PAGE)
        }
    }

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
