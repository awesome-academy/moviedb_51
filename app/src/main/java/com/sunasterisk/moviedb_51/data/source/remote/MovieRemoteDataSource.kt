package com.sunasterisk.moviedb_51.data.source.remote

import com.sunasterisk.moviedb_51.data.source.MovieDataSource
import com.sunasterisk.moviedb_51.data.source.remote.api.MovieService
import com.sunasterisk.moviedb_51.data.source.remote.response.GenresResponse
import com.sunasterisk.moviedb_51.data.source.remote.response.MoviesResponse
import io.reactivex.Observable

class MovieRemoteDataSource private constructor(private val movieService: MovieService) :
    MovieDataSource.Remote {

    override fun getMovies(type: String, query: String?, countPage: Int): Observable<MoviesResponse> {
        return movieService.getMoviesByCategory(type, countPage)
    }

    override fun getGenres(): Observable<GenresResponse> {
        return movieService.getGenres()
    }

    companion object {
        private var INSTANCE: MovieRemoteDataSource? = null
        fun getInstance(movieService: MovieService): MovieRemoteDataSource = INSTANCE ?: synchronized(this) {
            INSTANCE ?: MovieRemoteDataSource(movieService).also { INSTANCE = it }
        }
    }
}
