package com.sunasterisk.moviedb_51.ui.details.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MovieDetailsViewPagerAdapter(
    private val categories: Array<String>,
    manager: FragmentManager
) : FragmentPagerAdapter(
    manager,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return categories[position]
    }

    fun addFragment(fragment: Fragment) {
        fragments.add(fragment)
    }

    companion object {
        private val fragments = mutableListOf<Fragment>()
    }
}
