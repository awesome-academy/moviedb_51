package com.sunasterisk.moviedb_51.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.sunasterisk.moviedb_51.R
import com.sunasterisk.moviedb_51.data.model.Genres
import com.sunasterisk.moviedb_51.ui.home.HomeFragment.Companion.COUNT_CHIP_HOME
import com.sunasterisk.moviedb_51.ui.movies.MoviesFragment.Companion.COUNT_CHIP_MOVIES

@BindingAdapter("bindImageGenres")
fun ImageView.bindImageGenres(genres: Int?) {
    genres ?: return
    val genresType = GenresTypes.values().find { it.genreId == genres }
    genresType?.let { this.setImageResource(it.genreImageResource) }
}

@BindingAdapter("bindImage")
fun ImageView.loadImageUrl(imagePath: String?) {
    if (imagePath.isNullOrEmpty()) {
        setImageResource(R.drawable.ic_no_image)
        return
    }
    Glide.with(context)
        .load(Constant.BASE_URL_IMAGE + imagePath)
        .placeholder(R.drawable.ic_no_image)
        .into(this)
}

@BindingAdapter("items")
fun ChipGroup.setItems(genreIds: List<Int>?) {
    genreIds ?: return
    if (childCount > 0) removeAllViews()
    for (i in genreIds.indices) {
        if (i < COUNT_CHIP_HOME) {
            val genresChip = LayoutInflater.from(context)
                .inflate(R.layout.item_chip_home, this, false) as Chip
            val genresType = GenresTypes.values().find { it.genreId == genreIds[i] }
            genresChip.text = genresType?.let { context.getString(it.genreNameId) }
            addView(genresChip)
        }
    }
}

@BindingAdapter("setCollapsingToolbar", "setTitle")
fun AppBarLayout.handleCollapsedToolbarTitle(
    collapsingToolbar: CollapsingToolbarLayout?,
    title: String?
) {
    addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
        var isShow = true
        var scrollRange = -1
        override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
            if (scrollRange == -1) scrollRange = appBarLayout.totalScrollRange
            if (scrollRange + verticalOffset == 0) {
                collapsingToolbar?.title =
                    title
                isShow = true
            } else if (isShow) {
                collapsingToolbar?.title = ""
                isShow = false
            }
        }
    })
}

@BindingAdapter("itemGenreDetails")
fun ChipGroup.setItemGenreDetails(genres: List<Genres>?) {
    genres ?: return
    for (item in genres) {
        val genresChip = LayoutInflater.from(context)
            .inflate(R.layout.item_chip_details, this, false) as Chip
        genresChip.run {
            id = item.genresID
            text = item.genresName
        }
        addView(genresChip)
    }
}

@BindingAdapter("setMarginTop")
fun AppBarLayout.setMarginTop(margin: Int?) {
    margin ?: return
    val params = this.layoutParams as ViewGroup.MarginLayoutParams
    params.topMargin = margin
    this.layoutParams = params
}

@BindingAdapter("itemsGenreMovies")
fun ChipGroup.setItemsGenreMovies(genreIds: List<Int>?) {
    genreIds ?: return
    if (childCount > 0) removeAllViews()
    for (i in genreIds.indices) {
        if (i < COUNT_CHIP_MOVIES) {
            val genresChip = LayoutInflater.from(context)
                .inflate(R.layout.item_chip_details, this, false) as Chip
            val genresType = GenresTypes.values().find { it.genreId == genreIds[i] }
            genresChip.text = genresType?.let { context.getString(it.genreNameId) }
            addView(genresChip)
        }
    }
}
