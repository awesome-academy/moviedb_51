package com.sunasterisk.moviedb_51.data.source

import com.sunasterisk.moviedb_51.data.source.remote.response.GenresResponse
import com.sunasterisk.moviedb_51.data.source.remote.response.MoviesResponse
import io.reactivex.Observable

interface MovieDataSource {
    /**
     * Local
     */
    interface Local {
    }

    /**
     * Remote
     */
    interface Remote {
        fun getMovies(type: String, query: String?, countPage: Int): Observable<MoviesResponse>
        fun getGenres(): Observable<GenresResponse>
    }
}
