package com.sunasterisk.moviedb_51.ui.details.casts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.sunasterisk.moviedb_51.R
import com.sunasterisk.moviedb_51.databinding.FragmentCastsBinding
import com.sunasterisk.moviedb_51.ui.details.MovieDetailsFragment
import com.sunasterisk.moviedb_51.ui.details.MovieDetailsViewModel
import com.sunasterisk.moviedb_51.ui.details.casts.adapter.CastAdapter
import com.sunasterisk.moviedb_51.utils.ItemOffsetDecoration

class CastsFragment : Fragment() {
    private lateinit var binding: FragmentCastsBinding
    private lateinit var viewModel: MovieDetailsViewModel
    private val castAdapter by lazy { CastAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_casts, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.dataResponse.observe(viewLifecycleOwner, Observer {
            castAdapter.submitList(it.casts.casts)
        })
    }

    private fun initView() {
        val layoutManager = GridLayoutManager(this.context, MovieDetailsFragment.COUNT_SPAN)
        binding.castsRecyclerView.layoutManager = layoutManager
        val itemDecoration =
            ItemOffsetDecoration(resources.getDimensionPixelOffset(R.dimen.dp_8))
        binding.castsRecyclerView.addItemDecoration(itemDecoration)
        binding.castsRecyclerView.adapter = castAdapter.apply {
            onItemClick = { _ ->
                //TODO switch to the movies screen by cast id
            }
        }
    }

    fun setViewModel(viewModel: MovieDetailsViewModel) {
        this.viewModel = viewModel
    }

    companion object {
        fun getInstance() = CastsFragment()
    }
}
