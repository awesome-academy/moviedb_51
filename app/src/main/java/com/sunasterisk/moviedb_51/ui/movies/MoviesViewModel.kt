package com.sunasterisk.moviedb_51.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sunasterisk.moviedb_51.base.BaseViewModel
import com.sunasterisk.moviedb_51.data.repository.MovieRepository
import com.sunasterisk.moviedb_51.data.source.remote.response.BaseResponse
import com.sunasterisk.moviedb_51.data.source.remote.response.MoviesResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MoviesViewModel(private val repository: MovieRepository) : BaseViewModel<MoviesResponse>() {
    private val _showEmptyMovies = MutableLiveData<Boolean>()
    val showEmptyMovies: LiveData<Boolean> get() = _showEmptyMovies

    fun getMovies(type: String, query: String, countPage: Int) =
        repository.getMovies(type, query, countPage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _ ->
                handleData(BaseResponse.loading())
            }
            .subscribe({ data ->
                _showEmptyMovies.value = data.movies.isEmpty()
                handleData(BaseResponse.success(data))
            }, { throwable ->
                handleData(BaseResponse.error(throwable.message))
            })
}
