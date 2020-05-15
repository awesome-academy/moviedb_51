package com.sunasterisk.moviedb_51.ui.details

import com.sunasterisk.moviedb_51.base.BaseViewModel
import com.sunasterisk.moviedb_51.data.model.Movie
import com.sunasterisk.moviedb_51.data.repository.MovieRepository
import com.sunasterisk.moviedb_51.data.source.remote.response.BaseResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MovieDetailsViewModel(private val repository: MovieRepository) : BaseViewModel<Movie>() {

    fun getMovieDetails(movieId: Int) = repository.getMovieDetails(movieId)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { _ ->
            handleData(BaseResponse.loading())
        }
        .subscribe({ data ->
            handleData(BaseResponse.success(data))
        }, { throwable ->
            handleData(BaseResponse.error(throwable.message))
        })
}
