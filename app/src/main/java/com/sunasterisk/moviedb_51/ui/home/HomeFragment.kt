package com.sunasterisk.moviedb_51.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sunasterisk.moviedb_51.R
import com.sunasterisk.moviedb_51.data.base.ViewModelFactory
import com.sunasterisk.moviedb_51.data.repository.MovieRepository
import com.sunasterisk.moviedb_51.data.source.local.MovieLocalDataSource
import com.sunasterisk.moviedb_51.data.source.remote.MovieRemoteDataSource
import com.sunasterisk.moviedb_51.data.source.remote.api.MovieFactory
import com.sunasterisk.moviedb_51.databinding.FragmentHomeBinding
import com.sunasterisk.moviedb_51.utils.NetworkUtil

class HomeFragment : Fragment() {
    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val movieRepository =
            MovieRepository.getInstance(
                MovieRemoteDataSource.getInstance(MovieFactory.instance),
                MovieLocalDataSource.instance
            )
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory { HomeViewModel(movieRepository) })[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this.viewLifecycleOwner
        activity?.let {
            if (NetworkUtil.isConnectedToNetwork(it)) {
                viewModel.onStart()
            } else {
                val message = getString(R.string.check_internet_fail)
                Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
            }
        }
        observeViewModel()
    }

    private fun observeViewModel() = with(viewModel) {
        moviesResponse.observe(viewLifecycleOwner, Observer {
            //TODO handle data by CATEGORY
        })
        genresResponse.observe(viewLifecycleOwner, Observer {
            //TODO handle data GENRES
        })
        categoriesResponse.observe(viewLifecycleOwner, Observer {
            //Todo handle data CATEGORIES
        })
        isAllLoaded.observe(viewLifecycleOwner, Observer {
            //Todo handle LOADED
        })
        messageError.observe(viewLifecycleOwner, Observer {
            //Todo show message error
        })
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}
