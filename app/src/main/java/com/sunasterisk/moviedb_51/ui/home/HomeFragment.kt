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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.tabs.TabLayout
import com.sunasterisk.moviedb_51.R
import com.sunasterisk.moviedb_51.data.base.ViewModelFactory
import com.sunasterisk.moviedb_51.data.repository.MovieRepository
import com.sunasterisk.moviedb_51.data.source.local.MovieLocalDataSource
import com.sunasterisk.moviedb_51.data.source.remote.MovieRemoteDataSource
import com.sunasterisk.moviedb_51.data.source.remote.api.MovieFactory
import com.sunasterisk.moviedb_51.databinding.FragmentHomeBinding
import com.sunasterisk.moviedb_51.ui.details.MovieDetailsFragment
import com.sunasterisk.moviedb_51.ui.home.adapter.GenreAdapter
import com.sunasterisk.moviedb_51.ui.home.adapter.MovieCategoryAdapter
import com.sunasterisk.moviedb_51.ui.movies.MoviesFragment
import com.sunasterisk.moviedb_51.utils.AnimationTypes
import com.sunasterisk.moviedb_51.utils.MoviesTypes
import com.sunasterisk.moviedb_51.utils.addFragment

class HomeFragment : Fragment(), TabLayout.OnTabSelectedListener,
    SwipeRefreshLayout.OnRefreshListener {
    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private val genreAdapter by lazy { GenreAdapter() }
    private val movieCategoryAdapter by lazy { MovieCategoryAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let {
            val movieRepository =
                MovieRepository.getInstance(
                    MovieRemoteDataSource.getInstance(MovieFactory(it).instance),
                    MovieLocalDataSource.instance
                )
            viewModel = ViewModelProvider(
                this,
                ViewModelFactory { HomeViewModel(movieRepository) })[HomeViewModel::class.java]
        }
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
        initView()
        viewModel.onStart()
        observeViewModel()
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        when (tab?.text) {
            resources.getString(R.string.title_upcoming) ->
                viewModel.getMovies(MoviesTypes.UPCOMING)
            resources.getString(R.string.title_top_rated) ->
                viewModel.getMovies(MoviesTypes.TOP_RATED)
            resources.getString(R.string.title_now_playing) ->
                viewModel.getMovies(MoviesTypes.NOW_PLAYING)
            resources.getString(R.string.title_popular) ->
                viewModel.getMovies(MoviesTypes.POPULAR)
        }
    }

    override fun onRefresh() {
        viewModel.onStart()
    }

    private fun observeViewModel() = with(viewModel) {
        movies.observe(viewLifecycleOwner, Observer(movieCategoryAdapter::submitList))
        genresResponse.observe(viewLifecycleOwner, Observer { genreAdapter.submitList(it.genres) })
        isAllLoaded.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.homeSwipeRefreshLayout.isRefreshing = false
                binding.homeProgressBar.visibility = View.GONE
            } else binding.homeProgressBar.visibility = View.VISIBLE
        })
        messageError.observe(viewLifecycleOwner, Observer {
            it?.let { Toast.makeText(activity, it, Toast.LENGTH_SHORT).show() }
        })
    }

    private fun setupTabCategory() {
        val categories = resources.getStringArray(R.array.categories_array)
        val tabCategory = binding.categoryTabLayout
        if (tabCategory.tabCount > 0) return
        viewModel.getMovies(MoviesTypes.POPULAR)
        tabCategory.getTabAt(0)?.select()
        for (item in categories)
            tabCategory.addTab(tabCategory.newTab().setText(item))
        for (i in 0 until tabCategory.tabCount) {
            val tab = (tabCategory.getChildAt(0) as ViewGroup).getChildAt(i)
            val params = tab.layoutParams as ViewGroup.MarginLayoutParams
            params.setMargins(0, 0, 50, 0)
            tab.requestLayout()
        }
    }

    private fun initView() {
        binding.run {
            genresRecyclerView.adapter = genreAdapter.apply {
                onItemClick = { genres ->
                    val fragment = MoviesFragment.getInstance(
                        MoviesTypes.GENRES.value,
                        genres.genresID.toString(),
                        genres.genresName
                    )
                    activity?.addFragment(R.id.mainFrameLayout, fragment, AnimationTypes.NONE, true)
                }
            }
            moviesByCategoryRecyclerView.adapter = movieCategoryAdapter.apply {
                onItemClick = { movie ->
                    val fragment = MovieDetailsFragment.getInstance(movie.movieID, movie.movieTitle)
                    activity?.addFragment(R.id.mainFrameLayout, fragment, AnimationTypes.NONE, true)
                }
            }
            categoryTabLayout.addOnTabSelectedListener(this@HomeFragment)
            homeSwipeRefreshLayout.setOnRefreshListener(this@HomeFragment)
            homeSwipeRefreshLayout.setColorSchemeResources(R.color.colorPurpleDark)
        }
        setupTabCategory()
    }

    companion object {
        const val COUNT_CHIP_HOME = 3

        fun newInstance() = HomeFragment()
    }
}
