package com.sunasterisk.moviedb_51.ui.container.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.sunasterisk.moviedb_51.ui.container.ContainerFragment.Companion.COUNT_FRAGMENT_NAVIGATE
import com.sunasterisk.moviedb_51.ui.container.ContainerFragment.Companion.POSITION_HOME_NAVIGATE
import com.sunasterisk.moviedb_51.ui.favorite.FavoriteFragment
import com.sunasterisk.moviedb_51.ui.home.HomeFragment

class ContainerViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(
    manager,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {

    override fun getItem(position: Int): Fragment {
        return if (position == POSITION_HOME_NAVIGATE) HomeFragment.newInstance()
        else FavoriteFragment.newInstance()
    }

    override fun getCount(): Int {
        return COUNT_FRAGMENT_NAVIGATE
    }
}
