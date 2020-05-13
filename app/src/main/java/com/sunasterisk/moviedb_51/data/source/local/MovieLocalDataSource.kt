package com.sunasterisk.moviedb_51.data.source.local

import com.sunasterisk.moviedb_51.data.model.Movie
import com.sunasterisk.moviedb_51.data.model.MovieRecent
import com.sunasterisk.moviedb_51.data.source.MovieDataSource
import io.reactivex.Flowable
import io.reactivex.Maybe

class MovieLocalDataSource private constructor(private val moviesDatabase: MoviesDatabase) :
    MovieDataSource.Local {

    override fun addMovieLocal(movie: MovieRecent) {
        moviesDatabase.movieDao().addMovieLocal(movie)
    }

    override fun deleteAllMoviesLocal() {
        moviesDatabase.movieDao().deleteAllMoviesLocal()
    }

    override fun getAllMoviesLocal(): Flowable<List<MovieRecent>> {
        return moviesDatabase.movieDao().getAllMoviesLocal()
    }

    override fun searchMoveLocal(movieId: Int): Maybe<Int> {
        return moviesDatabase.movieDao().searchMoveLocal(movieId)
    }

    companion object {
        private var INSTANCE: MovieLocalDataSource? = null
        fun getInstance(moviesDatabase: MoviesDatabase): MovieLocalDataSource =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: MovieLocalDataSource(moviesDatabase).also { INSTANCE = it }
            }
    }
}
