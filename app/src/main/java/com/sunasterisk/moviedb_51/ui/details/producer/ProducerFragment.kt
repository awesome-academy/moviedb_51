package com.sunasterisk.moviedb_51.ui.details.producer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.sunasterisk.moviedb_51.R
import com.sunasterisk.moviedb_51.databinding.FragmentProducerBinding
import com.sunasterisk.moviedb_51.ui.details.MovieDetailsViewModel

class ProducerFragment : Fragment() {
    private lateinit var binding: FragmentProducerBinding
    private lateinit var viewModel: MovieDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_producer, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    fun setViewModel(viewModel: MovieDetailsViewModel) {
        this.viewModel = viewModel
    }

    companion object {
        fun getInstance() = ProducerFragment()
    }
}
