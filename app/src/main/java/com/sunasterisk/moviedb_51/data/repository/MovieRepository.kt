package com.sunasterisk.moviedb_51.data.repository

import com.sunasterisk.moviedb_51.data.model.Movie
import com.sunasterisk.moviedb_51.data.model.MovieRecent
import com.sunasterisk.moviedb_51.data.source.MovieDataSource
import com.sunasterisk.moviedb_51.data.source.remote.response.GenresResponse
import com.sunasterisk.moviedb_51.data.source.remote.response.MoviesResponse
import com.sunasterisk.moviedb_51.utils.Constant
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable

class MovieRepository private constructor(
    private val remote: MovieDataSource.Remote,
    private val local: MovieDataSource.Local
) {

    fun getMovies(
        type: String,
        query: String? = null,
        countPage: Int = Constant.BASE_COUNT_PAGE
    ): Observable<MoviesResponse> {
        return remote.getMovies(type, query, countPage)
    }

    fun getGenres(): Observable<GenresResponse> {
        return remote.getGenres()
    }

    fun addMovieLocal(movie: MovieRecent) {
        local.addMovieLocal(movie)
    }

    fun deleteAllMoviesLocal() {
        local.deleteAllMoviesLocal()
    }

    fun getAllMoviesLocal(): Flowable<List<MovieRecent>> {
        return local.getAllMoviesLocal()
    }

    fun searchMoveLocal(movieId: Int): Maybe<Int> {
        return local.searchMoveLocal(movieId)
    }

    fun getMovieDetails(movieId: Int): Observable<Movie> {
        return remote.getMovieDetails(movieId)
    }

    companion object {
        private var INSTANCE: MovieRepository? = null
        fun getInstance(
            remote: MovieDataSource.Remote,
            local: MovieDataSource.Local
        ) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: MovieRepository(remote, local).also { INSTANCE = it }
        }
    }
}
