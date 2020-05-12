package com.sunasterisk.moviedb_51.data.source.local

import com.sunasterisk.moviedb_51.data.source.MovieDataSource

class MovieLocalDataSource private constructor() : MovieDataSource.Local {

    companion object {
        private var INSTANCE: MovieLocalDataSource? = null
        val instance: MovieLocalDataSource = INSTANCE ?: synchronized(this) {
            INSTANCE ?: MovieLocalDataSource().also { INSTANCE = it }
        }
    }
}
