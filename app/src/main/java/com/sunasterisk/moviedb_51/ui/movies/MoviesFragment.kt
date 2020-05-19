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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.sunasterisk.moviedb_51.R
import com.sunasterisk.moviedb_51.data.base.ViewModelFactory
import com.sunasterisk.moviedb_51.data.repository.MovieRepository
import com.sunasterisk.moviedb_51.data.source.local.MovieLocalDataSource
import com.sunasterisk.moviedb_51.data.source.local.MoviesDatabase
import com.sunasterisk.moviedb_51.data.source.remote.MovieRemoteDataSource
import com.sunasterisk.moviedb_51.data.source.remote.api.MovieFactory
import com.sunasterisk.moviedb_51.databinding.FragmentMoviesBinding
import com.sunasterisk.moviedb_51.ui.details.MovieDetailsFragment
import com.sunasterisk.moviedb_51.ui.main.MainActivity
import com.sunasterisk.moviedb_51.ui.movies.adapter.MovieAdapter
import com.sunasterisk.moviedb_51.utils.AnimationTypes
import com.sunasterisk.moviedb_51.utils.Constant
import com.sunasterisk.moviedb_51.utils.addFragment
import kotlinx.android.synthetic.main.partial_empty_movies.*

class MoviesFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {
    private lateinit var viewModel: MoviesViewModel
    private lateinit var binding: FragmentMoviesBinding
    private val movieAdapter by lazy { MovieAdapter() }

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
        binding.marginTop = getStatusBarHeight()
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        initToolbar()
        initView()
        getMovies()
        observeViewModel()
    }

    override fun onRefresh() {
        getMovies()
    }

    private fun initView() {
        binding.moviesRecyclerView.adapter = movieAdapter.apply {
            onItemClick = { movie ->
                val fragment = MovieDetailsFragment.getInstance(
                    movie.movieID,
                    movie.movieTitle
                )
                activity?.addFragment(R.id.mainFrameLayout, fragment, AnimationTypes.OPEN, true)
            }
        }
    }

    private fun observeViewModel() = with(viewModel) {
        dataResponse.observe(viewLifecycleOwner, Observer {
            movieAdapter.submitList(it.movies)
        })
        messageError.observe(viewLifecycleOwner, Observer {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        })
        showEmptyMovies.observe(viewLifecycleOwner, Observer {
            if (it) messageEmptyTextView.text = getString(R.string.message_empty_movies)
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

    private fun initToolbar() {
        (activity as? MainActivity)?.run {
            setSupportActionBar(binding.toolbar)
            supportActionBar?.run {
                setDisplayShowTitleEnabled(true)
                title = arguments?.getString(ARGUMENT_TITLE)
            }
            binding.toolbar.setNavigationOnClickListener {
                supportFragmentManager.popBackStackImmediate()
            }
        }
    }

    private fun getStatusBarHeight(): Int {
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId)
        }
        return 0
    }

    companion object {
        private const val ARGUMENT_QUERY = "ARGUMENT_QUERY"
        private const val ARGUMENT_TITLE = "ARGUMENT_TITLE"
        private const val ARGUMENT_TYPES = "ARGUMENT_TYPES"
        const val COUNT_CHIP_MOVIES = 2

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
