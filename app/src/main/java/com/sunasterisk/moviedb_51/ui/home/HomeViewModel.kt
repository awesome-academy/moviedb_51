package com.sunasterisk.moviedb_51.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sunasterisk.moviedb_51.data.model.Movie
import com.sunasterisk.moviedb_51.data.repository.MovieRepository
import com.sunasterisk.moviedb_51.data.source.remote.response.BaseResponse
import com.sunasterisk.moviedb_51.data.source.remote.response.GenresResponse
import com.sunasterisk.moviedb_51.data.source.remote.response.MoviesResponse
import com.sunasterisk.moviedb_51.utils.MoviesTypes
import com.sunasterisk.moviedb_51.utils.Status
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeViewModel(private val repository: MovieRepository) : ViewModel() {
    private var _movies = MutableLiveData<List<Movie>>()
    private var _genresResponse = MutableLiveData<GenresResponse>()
    private var _isAllLoaded = MutableLiveData<Boolean>()
    private var _messageError = MutableLiveData<String>()
    private var isLoadedMoviesResponse = false
    private var isLoadedGenresResponse = false
    val movies: LiveData<List<Movie>> get() = _movies
    val genresResponse: LiveData<GenresResponse> get() = _genresResponse
    val isAllLoaded: LiveData<Boolean> get() = _isAllLoaded
    val messageError: LiveData<String> get() = _messageError

    fun onStart() {
        getGenres()
    }

    fun getMovies(type: MoviesTypes) = repository.getMovies(type.value)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { _ ->
            handleData(type.value, BaseResponse.loading<MoviesResponse>())
        }
        .subscribe({ data ->
            handleData(type.value, BaseResponse.success(data))
        }, { throwable ->
            handleData(type.value, BaseResponse.error<MoviesResponse>(throwable.message))
        })


    private fun getGenres() = repository.getGenres()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { _ ->
            handleData(MoviesTypes.GENRES.value, BaseResponse.loading<GenresResponse>())
        }.subscribe({ data ->
            handleData(MoviesTypes.GENRES.value, BaseResponse.success(data))
        }, { throwable ->
            handleData(
                MoviesTypes.GENRES.value,
                BaseResponse.error<GenresResponse>(throwable.message)
            )
        })

    private fun isAllLoaded() = isLoadedGenresResponse && isLoadedMoviesResponse

    private fun <T> handleData(type: String, response: BaseResponse<T>) {
        when (response.status) {
            Status.SUCCESS -> {
                when (type) {
                    MoviesTypes.GENRES.value -> {
                        _genresResponse.value = response.data as GenresResponse
                        isLoadedGenresResponse = true
                        _isAllLoaded.value = isAllLoaded()
                    }
                    MoviesTypes.UPCOMING.value,
                    MoviesTypes.POPULAR.value,
                    MoviesTypes.TOP_RATED.value,
                    MoviesTypes.NOW_PLAYING.value
                    -> {
                        val moviesResponse = response.data as MoviesResponse
                        _movies.value = moviesResponse.movies
                        isLoadedMoviesResponse = true
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
