package com.sunasterisk.moviedb_51.data.source.local

import com.sunasterisk.moviedb_51.data.source.MovieDataSource
import com.sunasterisk.moviedb_51.utils.Types
import io.reactivex.Flowable

class MovieLocalDataSource private constructor() : MovieDataSource.Local {

    override fun getCategoriesLocal(): Flowable<List<String>> {
        return Flowable.just(
            listOf(
                Types.POPULAR.title,
                Types.NOW_PLAYING.title,
                Types.TOP_RATED.title,
                Types.UPCOMING.title
            )
        )
    }

    companion object {
        private var INSTANCE: MovieLocalDataSource? = null
        val instance: MovieLocalDataSource = INSTANCE ?: synchronized(this) {
            INSTANCE ?: MovieLocalDataSource().also { INSTANCE = it }
        }
    }
}
