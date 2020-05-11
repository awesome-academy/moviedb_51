package com.sunasterisk.moviedb_51.ui.container

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sunasterisk.moviedb_51.R
import com.sunasterisk.moviedb_51.databinding.FragmentContainerBinding
import com.sunasterisk.moviedb_51.ui.container.adapter.ContainerViewPagerAdapter
import com.sunasterisk.moviedb_51.ui.main.MainActivity
import com.sunasterisk.moviedb_51.utils.Constant
import kotlinx.android.synthetic.main.toolbar.view.*

class ContainerFragment : Fragment(), BottomNavigationView.OnNavigationItemSelectedListener,
    ViewPager.OnPageChangeListener {
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
        initView()
        binding.bottomNavigation.setOnNavigationItemSelectedListener(this)
        binding.containerViewPaper.addOnPageChangeListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.home -> {
                binding.containerViewPaper.currentItem = Constant.POSITION_HOME_NAVIGATE
                true
            }
            R.id.favorite -> {
                binding.containerViewPaper.currentItem = Constant.POSITION_FAVORITE_NAVIGATE
                true
            }
            else -> false
        }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        if (position == Constant.POSITION_HOME_NAVIGATE)
            binding.bottomNavigation.menu.findItem(R.id.home).isChecked = true
        else binding.bottomNavigation.menu.findItem(R.id.favorite).isChecked = true
    }

    private fun initView() {
        view?.toolbar?.let {
            (activity as? MainActivity)?.run { setSupportActionBar(it) }
        }
        activity?.run {
            val adapter = ContainerViewPagerAdapter(supportFragmentManager)
            binding.containerViewPaper.adapter = adapter
        }
    }

    companion object {
        fun newInstance() = ContainerFragment()
    }
}
