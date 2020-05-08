package com.sunasterisk.moviedb_51.ui.container

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.sunasterisk.moviedb_51.R
import com.sunasterisk.moviedb_51.databinding.FragmentContainerBinding
import com.sunasterisk.moviedb_51.ui.main.MainActivity
import kotlinx.android.synthetic.main.toolbar.view.*

class ContainerFragment : Fragment() {
    private lateinit var binding: FragmentContainerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_container, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolBar()
    }

    private fun initToolBar() {
        view?.toolbar?.let {
            (activity as? MainActivity)?.run { setSupportActionBar(it) }
        }
    }

    companion object {
        fun newInstance() = ContainerFragment()
    }
}
