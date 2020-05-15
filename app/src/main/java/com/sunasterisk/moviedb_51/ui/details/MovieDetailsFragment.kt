package com.sunasterisk.moviedb_51.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sunasterisk.moviedb_51.R
import com.sunasterisk.moviedb_51.data.base.ViewModelFactory
import com.sunasterisk.moviedb_51.data.repository.MovieRepository
import com.sunasterisk.moviedb_51.data.source.local.MovieLocalDataSource
import com.sunasterisk.moviedb_51.data.source.local.MoviesDatabase
import com.sunasterisk.moviedb_51.data.source.remote.MovieRemoteDataSource
import com.sunasterisk.moviedb_51.data.source.remote.api.MovieFactory
import com.sunasterisk.moviedb_51.databinding.FragmentDetailsBinding
import com.sunasterisk.moviedb_51.ui.details.adapter.MovieDetailsViewPagerAdapter
import com.sunasterisk.moviedb_51.ui.main.MainActivity

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
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        initToolBar()
        initViewPaper()
        arguments?.let { viewModel.getMovieDetails(it.getInt(ARGUMENT_MOVIE_ID)) }
    }

    private fun initViewPaper() {
        activity?.run {
            val viewPaperAdapter =
                MovieDetailsViewPagerAdapter(this, viewModel, supportFragmentManager)
            binding.movieDetailsViewPaper.adapter = viewPaperAdapter
            binding.categoryDetailsTabLayout.setupWithViewPager(binding.movieDetailsViewPaper)
            setupTabCategory()
        }
    }

    private fun initToolBar() {
        (activity as? MainActivity)?.run {
            setSupportActionBar(binding.toolbar)
            supportActionBar?.run {
                setDisplayShowTitleEnabled(true)
                title = arguments?.getString(ARGUMENT_MOVIE_TITLE)
            }
            binding.toolbar.setNavigationOnClickListener {
                supportFragmentManager.popBackStackImmediate()
            }
        }
    }

    private fun setupTabCategory() {
        val tabCategory = binding.categoryDetailsTabLayout
        tabCategory.getTabAt(0)?.select()
        for (i in 0 until tabCategory.tabCount) {
            val tab = (tabCategory.getChildAt(0) as ViewGroup).getChildAt(i)
            val params = tab.layoutParams as ViewGroup.MarginLayoutParams
            params.setMargins(0, 0, 50, 0)
            tab.requestLayout()
        }
    }

    companion object {
        private const val ARGUMENT_MOVIE_ID = "ARGUMENT_MOVIE_ID"
        private const val ARGUMENT_MOVIE_TITLE = "ARGUMENT_MOVIE_TITLE"

        fun getInstance(id: Int, title: String) = MovieDetailsFragment().apply {
            arguments = bundleOf(ARGUMENT_MOVIE_ID to id, ARGUMENT_MOVIE_TITLE to title)
        }
    }
}
