package com.sunasterisk.moviedb_51.ui.details.producer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.sunasterisk.moviedb_51.R
import com.sunasterisk.moviedb_51.databinding.FragmentProducerBinding
import com.sunasterisk.moviedb_51.ui.details.MovieDetailsFragment
import com.sunasterisk.moviedb_51.ui.details.MovieDetailsViewModel
import com.sunasterisk.moviedb_51.ui.details.producer.adapter.ProducerAdapter
import com.sunasterisk.moviedb_51.utils.ItemOffsetDecoration

class ProducerFragment : Fragment() {
    private lateinit var binding: FragmentProducerBinding
    private lateinit var viewModel: MovieDetailsViewModel
    private val producerAdapter by lazy { ProducerAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_producer, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.dataResponse.observe(viewLifecycleOwner, Observer {
            producerAdapter.submitList(it.producer)
        })
    }

    private fun initView() {
        val layoutManager = GridLayoutManager(this.context, MovieDetailsFragment.COUNT_SPAN)
        binding.producerRecyclerView.layoutManager = layoutManager
        val itemDecoration =
            ItemOffsetDecoration(resources.getDimensionPixelOffset(R.dimen.dp_8))
        binding.producerRecyclerView.addItemDecoration(itemDecoration)
        binding.producerRecyclerView.adapter = producerAdapter.apply {
            onItemClick = { _ ->
                //TODO switch to the movies screen by cast id
            }
        }
    }

    fun setViewModel(viewModel: MovieDetailsViewModel) {
        this.viewModel = viewModel
    }

    companion object {
        fun getInstance() = ProducerFragment()
    }
}
