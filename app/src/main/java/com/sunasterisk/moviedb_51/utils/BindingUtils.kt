package com.sunasterisk.moviedb_51.utils

import android.view.LayoutInflater
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

@BindingAdapter("bindImageGenres")
fun ImageView.bindImageGenres(genres: Int?) {
    genres ?: return
    this.setImageResource(
        when (genres) {
            GenresTypes.ACTION.genreId -> GenresTypes.ACTION.genreImageResource
            GenresTypes.ADVENTURE.genreId -> GenresTypes.ADVENTURE.genreImageResource
            GenresTypes.ANIMATION.genreId -> GenresTypes.ANIMATION.genreImageResource
            GenresTypes.COMEDY.genreId -> GenresTypes.COMEDY.genreImageResource
            GenresTypes.CRIME.genreId -> GenresTypes.CRIME.genreImageResource
            GenresTypes.DOCUMENTARY.genreId -> GenresTypes.DOCUMENTARY.genreImageResource
            GenresTypes.DRAMA.genreId -> GenresTypes.DRAMA.genreImageResource
            GenresTypes.FAMILY.genreId -> GenresTypes.FAMILY.genreImageResource
            GenresTypes.FANTASY.genreId -> GenresTypes.FANTASY.genreImageResource
            GenresTypes.HISTORY.genreId -> GenresTypes.HISTORY.genreImageResource
            GenresTypes.HORROR.genreId -> GenresTypes.HORROR.genreImageResource
            GenresTypes.MUSIC.genreId -> GenresTypes.MUSIC.genreImageResource
            GenresTypes.MYSTERY.genreId -> GenresTypes.MYSTERY.genreImageResource
            GenresTypes.ROMANCE.genreId -> GenresTypes.ROMANCE.genreImageResource
            GenresTypes.SCIENCE_FICTION.genreId -> GenresTypes.SCIENCE_FICTION.genreImageResource
            GenresTypes.TV_MOVIE.genreId -> GenresTypes.TV_MOVIE.genreImageResource
            GenresTypes.THRILLER.genreId -> GenresTypes.THRILLER.genreImageResource
            GenresTypes.WAR.genreId -> GenresTypes.WAR.genreImageResource
            GenresTypes.WESTERN.genreId -> GenresTypes.WESTERN.genreImageResource
            else -> GenresTypes.NONE.genreImageResource
        }
    )
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
            genresChip.text = when (genreIds[i]) {
                GenresTypes.ACTION.genreId -> context.getString(GenresTypes.ACTION.genreNameId)
                GenresTypes.ADVENTURE.genreId -> context.getString(GenresTypes.ADVENTURE.genreNameId)
                GenresTypes.ANIMATION.genreId -> context.getString(GenresTypes.ANIMATION.genreNameId)
                GenresTypes.COMEDY.genreId -> context.getString(GenresTypes.COMEDY.genreNameId)
                GenresTypes.CRIME.genreId -> context.getString(GenresTypes.CRIME.genreNameId)
                GenresTypes.DOCUMENTARY.genreId -> context.getString(GenresTypes.DOCUMENTARY.genreNameId)
                GenresTypes.DRAMA.genreId -> context.getString(GenresTypes.DRAMA.genreNameId)
                GenresTypes.FAMILY.genreId -> context.getString(GenresTypes.FAMILY.genreNameId)
                GenresTypes.FANTASY.genreId -> context.getString(GenresTypes.FANTASY.genreNameId)
                GenresTypes.HISTORY.genreId -> context.getString(GenresTypes.HISTORY.genreNameId)
                GenresTypes.HORROR.genreId -> context.getString(GenresTypes.HORROR.genreNameId)
                GenresTypes.MUSIC.genreId -> context.getString(GenresTypes.MUSIC.genreNameId)
                GenresTypes.MYSTERY.genreId -> context.getString(GenresTypes.MYSTERY.genreNameId)
                GenresTypes.ROMANCE.genreId -> context.getString(GenresTypes.ROMANCE.genreNameId)
                GenresTypes.SCIENCE_FICTION.genreId -> context.getString(GenresTypes.SCIENCE_FICTION.genreNameId)
                GenresTypes.TV_MOVIE.genreId -> context.getString(GenresTypes.TV_MOVIE.genreNameId)
                GenresTypes.THRILLER.genreId -> context.getString(GenresTypes.THRILLER.genreNameId)
                GenresTypes.WAR.genreId -> context.getString(GenresTypes.WAR.genreNameId)
                GenresTypes.WESTERN.genreId -> context.getString(GenresTypes.WESTERN.genreNameId)
                else -> context.getString(GenresTypes.NONE.genreNameId)
            }
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
