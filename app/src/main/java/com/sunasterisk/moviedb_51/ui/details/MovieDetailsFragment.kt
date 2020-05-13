package com.sunasterisk.moviedb_51.ui.details

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
import com.sunasterisk.moviedb_51.databinding.FragmentDetailsBinding

class MovieDetailsFragment : Fragment() {
    private lateinit var viewModel: MovieDetailsViewModel
    private lateinit var binding: FragmentDetailsBinding
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
                ViewModelFactory { MovieDetailsViewModel(movieRepository) })[MovieDetailsViewModel::class.java]
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this.viewLifecycleOwner
        observeViewModel()
    }

    private fun observeViewModel() = with(viewModel) {
        arguments?.let { getMovieDetails(it.getInt(ARGUMENT_MOVIE_ID)) }
        dataResponse.observe(viewLifecycleOwner, Observer {
            //TODO get data Movie Details
        })
        isLoaded.observe(viewLifecycleOwner, Observer {
            //TODO handle progressbar
        })
        messageError.observe(viewLifecycleOwner, Observer {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        })
    }

    companion object {
        private const val ARGUMENT_MOVIE_ID = "ARGUMENT_MOVIE_ID"
        private const val ARGUMENT_MOVIE_TITLE = "ARGUMENT_MOVIE_TITLE"

        fun getInstance(id: Int, title: String) = MovieDetailsFragment().apply {
            arguments = bundleOf(ARGUMENT_MOVIE_ID to id, ARGUMENT_MOVIE_TITLE to title)
        }
    }
}
