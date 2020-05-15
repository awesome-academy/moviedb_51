package com.sunasterisk.moviedb_51.ui.details.information

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.sunasterisk.moviedb_51.R
import com.sunasterisk.moviedb_51.databinding.FragmentInformationBinding
import com.sunasterisk.moviedb_51.ui.details.MovieDetailsViewModel

class InformationFragment : Fragment() {
    private lateinit var binding: FragmentInformationBinding
    private lateinit var viewModel: MovieDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_information, container, false)
        return binding.root
    }

    fun setViewModel(viewModel: MovieDetailsViewModel) {
        this.viewModel = viewModel
    }

    companion object {
        fun getInstance() = InformationFragment()
    }
}
