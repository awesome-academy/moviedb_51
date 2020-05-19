package com.sunasterisk.moviedb_51.data.source.remote

import com.sunasterisk.moviedb_51.data.model.Movie
import com.sunasterisk.moviedb_51.data.source.MovieDataSource
import com.sunasterisk.moviedb_51.data.source.remote.api.MovieService
import com.sunasterisk.moviedb_51.data.source.remote.response.GenresResponse
import com.sunasterisk.moviedb_51.data.source.remote.response.MoviesResponse
import com.sunasterisk.moviedb_51.utils.MoviesTypes
import io.reactivex.Observable

class MovieRemoteDataSource private constructor(private val movieService: MovieService) :
    MovieDataSource.Remote {

    override fun getMovies(
        type: String,
        query: String?,
        countPage: Int
    ): Observable<MoviesResponse> {
        return if (query.isNullOrEmpty()) movieService.getMoviesByCategory(type, countPage)
        else when (type) {
            MoviesTypes.GENRES.value -> movieService.getMoviesByGenresID(query, countPage)
            MoviesTypes.CAST.value -> movieService.getMoviesCastID(query, countPage)
            MoviesTypes.PRODUCER.value -> movieService.getMoviesByProduceID(query, countPage)
            else -> movieService.getMoviesByQuery(query, countPage)
        }
    }

    override fun getGenres(): Observable<GenresResponse> {
        return movieService.getGenres()
    }

    override fun getMovieDetails(movieId: Int): Observable<Movie> {
        return movieService.getMovieDetails(movieId)
    }

    companion object {
        private var INSTANCE: MovieRemoteDataSource? = null
        fun getInstance(movieService: MovieService): MovieRemoteDataSource =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: MovieRemoteDataSource(movieService).also { INSTANCE = it }
            }
    }
}
