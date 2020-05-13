package com.sunasterisk.moviedb_51.data.source

import com.sunasterisk.moviedb_51.data.model.Movie
import com.sunasterisk.moviedb_51.data.model.MovieRecent
import com.sunasterisk.moviedb_51.data.source.remote.response.GenresResponse
import com.sunasterisk.moviedb_51.data.source.remote.response.MoviesResponse
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable

interface MovieDataSource {
    /**
     * Local
     */
    interface Local {
        fun addMovieLocal(movie: MovieRecent)
        fun deleteAllMoviesLocal()
        fun getAllMoviesLocal(): Flowable<List<MovieRecent>>
        fun searchMoveLocal(movieId: Int): Maybe<Int>
    }

    /**
     * Remote
     */
    interface Remote {
        fun getMovies(type: String, query: String?, countPage: Int): Observable<MoviesResponse>
        fun getGenres(): Observable<GenresResponse>
        fun getMovieDetails(movieId: Int): Observable<Movie>
    }
}
