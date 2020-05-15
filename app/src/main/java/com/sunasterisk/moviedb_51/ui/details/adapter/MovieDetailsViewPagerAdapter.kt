package com.sunasterisk.moviedb_51.ui.details.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.sunasterisk.moviedb_51.R
import com.sunasterisk.moviedb_51.ui.details.MovieDetailsViewModel
import com.sunasterisk.moviedb_51.ui.details.casts.CastsFragment
import com.sunasterisk.moviedb_51.ui.details.information.InformationFragment
import com.sunasterisk.moviedb_51.ui.details.producer.ProducerFragment
import com.sunasterisk.moviedb_51.utils.TabsDetails


class MovieDetailsViewPagerAdapter(
    context: Context,
    private val viewModel: MovieDetailsViewModel,
    manager: FragmentManager
) : FragmentStatePagerAdapter(
    manager,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {
    private val categories =
        context.resources.getStringArray(R.array.movie_details_categories_array)

    override fun getItem(position: Int): Fragment {
        val informationFragment = InformationFragment.getInstance()
        val castsFragment = CastsFragment.getInstance()
        val producerFragment = ProducerFragment.getInstance()
        return when (position) {
            TabsDetails.INFORMATION.position -> {
                informationFragment.setViewModel(viewModel)
                informationFragment
            }
            TabsDetails.CASTS.position -> {
                castsFragment.setViewModel(viewModel)
                castsFragment
            }
            else -> {
                producerFragment.setViewModel(viewModel)
                producerFragment
            }
        }
    }

    override fun getCount(): Int {
        return COUNT_TAB_DETAILS
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return categories[position]
    }

    companion object {
        const val COUNT_TAB_DETAILS = 3
    }
}
