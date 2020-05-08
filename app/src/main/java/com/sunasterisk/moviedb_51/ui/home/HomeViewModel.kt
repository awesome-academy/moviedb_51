package com.sunasterisk.moviedb_51.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sunasterisk.moviedb_51.data.repository.MovieRepository
import com.sunasterisk.moviedb_51.data.source.remote.response.BaseResponse
import com.sunasterisk.moviedb_51.data.source.remote.response.GenresResponse
import com.sunasterisk.moviedb_51.data.source.remote.response.MoviesResponse
import com.sunasterisk.moviedb_51.utils.Status
import com.sunasterisk.moviedb_51.utils.Types
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeViewModel(private val repository: MovieRepository) : ViewModel() {
    private var _moviesResponse = MutableLiveData<MoviesResponse>()
    private var _genresResponse = MutableLiveData<GenresResponse>()
    private var _categoriesResponse = MutableLiveData<List<String>>()
    private var _isAllLoaded = MutableLiveData<Boolean>()
    private var _messageError = MutableLiveData<String>()
    private var isLoadedMoviesResponse = false
    private var isLoadedGenresResponse = false
    private var isLoadedCategoriesResponse = false
    val moviesResponse: LiveData<MoviesResponse> get() = _moviesResponse
    val genresResponse: LiveData<GenresResponse> get() = _genresResponse
    val categoriesResponse: LiveData<List<String>> get() = _categoriesResponse
    val isAllLoaded: LiveData<Boolean> get() = _isAllLoaded
    val messageError: LiveData<String> get() = _messageError

    fun onStart() {
        getCategories()
        getGenres()
    }

    fun getMovies(type: Types) = repository.getMovies(type.data)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { _ ->
            handleData(type, BaseResponse.loading<MoviesResponse>())
        }
        .subscribe({ data ->
            handleData(type, BaseResponse.success(data))
        }, { throwable ->
            handleData(type, BaseResponse.error<MoviesResponse>(throwable.message))
        })


    private fun getGenres() = repository.getGenres()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { _ ->
            handleData(Types.GENRES, BaseResponse.loading<GenresResponse>())
        }.subscribe({ data ->
            handleData(Types.GENRES, BaseResponse.success(data))
        }, { throwable ->
            handleData(Types.GENRES, BaseResponse.error<GenresResponse>(throwable.message))
        })


    private fun getCategories() = repository.getCategories()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { _ ->
            handleData(Types.CATEGORIES, BaseResponse.loading<List<String>>())
        }.subscribe({ data ->
            handleData(Types.CATEGORIES, BaseResponse.success(data))
        }, { throwable ->
            handleData(Types.CATEGORIES, BaseResponse.error<List<String>>(throwable.message))
        })

    private fun isAllLoaded() =
        isLoadedCategoriesResponse && isLoadedGenresResponse && isLoadedMoviesResponse

    private fun <T> handleData(type: Types, response: BaseResponse<T>) {
        when (response.status) {
            Status.SUCCESS -> {
                when (type) {
                    Types.GENRES -> {
                        _genresResponse.value = response.data as GenresResponse
                        isLoadedGenresResponse = true
                        _isAllLoaded.value = isAllLoaded()
                    }
                    Types.UPCOMING, Types.TOP_RATED, Types.NOW_PLAYING, Types.POPULAR -> {
                        _moviesResponse.value = response.data as MoviesResponse
                        isLoadedMoviesResponse = true
                        _isAllLoaded.value = isAllLoaded()
                    }
                    Types.CATEGORIES -> {
                        _categoriesResponse.value = response.data as List<String>
                        isLoadedCategoriesResponse = true
                        _isAllLoaded.value = isAllLoaded()
                    }
                }
            }
            Status.ERROR -> {
                _messageError.value = response.messageError
            }
            Status.LOADING -> {
                _isAllLoaded.value = false
            }
        }
    }
}
