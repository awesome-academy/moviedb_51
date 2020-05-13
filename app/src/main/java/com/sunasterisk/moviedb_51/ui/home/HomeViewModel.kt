package com.sunasterisk.moviedb_51.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sunasterisk.moviedb_51.data.model.Movie
import com.sunasterisk.moviedb_51.data.model.MovieRecent
import com.sunasterisk.moviedb_51.data.repository.MovieRepository
import com.sunasterisk.moviedb_51.data.source.remote.response.BaseResponse
import com.sunasterisk.moviedb_51.data.source.remote.response.GenresResponse
import com.sunasterisk.moviedb_51.data.source.remote.response.MoviesResponse
import com.sunasterisk.moviedb_51.utils.MoviesTypes
import com.sunasterisk.moviedb_51.utils.Status
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeViewModel(private val repository: MovieRepository) : ViewModel() {
    private val _movies = MutableLiveData<List<Movie>>()
    private val _genresResponse = MutableLiveData<GenresResponse>()
    private val _isAllLoaded = MutableLiveData<Boolean>()
    private val _messageError = MutableLiveData<String>()
    private val _moviesRecent = MutableLiveData<List<MovieRecent>>()
    private val _showEmptyMovieRecent = MutableLiveData<Boolean>()
    private var isLoadedMoviesResponse = false
    private var isLoadedGenresResponse = false
    private var isLoadedMoviesRecent = false
    val movies: LiveData<List<Movie>> get() = _movies
    val genresResponse: LiveData<GenresResponse> get() = _genresResponse
    val isAllLoaded: LiveData<Boolean> get() = _isAllLoaded
    val messageError: LiveData<String> get() = _messageError
    val moviesRecent: LiveData<List<MovieRecent>> get() = _moviesRecent
    val showEmptyMovieRecent: LiveData<Boolean> get() = _showEmptyMovieRecent

    fun onStart() {
        getGenres()
        getAllMoviesLocal()
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

    private fun getAllMoviesLocal() = repository.getAllMoviesLocal()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { _ ->
            handleData(MoviesTypes.RECENT.value, BaseResponse.loading<List<MovieRecent>>())
        }.subscribe({ data ->
            _showEmptyMovieRecent.value = data.isEmpty()
            handleData(MoviesTypes.RECENT.value, BaseResponse.success(data))
        }, { throwable ->
            handleData(
                MoviesTypes.RECENT.value,
                BaseResponse.error<List<MovieRecent>>(throwable.message)
            )
        })

    private fun addMovieLocal(movie: MovieRecent) {
        val movieRecent = MovieRecent(movie.movieID, movie.movieTitle, movie.moviePosterPath)
        Observable.fromCallable { repository.addMovieLocal(movieRecent) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _ ->
                _isAllLoaded.value = false
            }
            .doAfterTerminate {
                _isAllLoaded.value = true
            }
            .subscribe()
    }

    fun deleteAllMoviesLocal() {
        Observable.fromCallable { repository.deleteAllMoviesLocal() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _ ->
                _isAllLoaded.value = false
            }
            .doAfterTerminate {
                _isAllLoaded.value = true
            }
            .subscribe()
    }

    fun handleAddMovieLocal(movie: Movie) =
        repository.searchMoveLocal(movie.movieID).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _ ->
                _isAllLoaded.value = false
            }.subscribe({ data ->
                _isAllLoaded.value = true
                val movieRecent =
                    MovieRecent(movie.movieID, movie.movieTitle, movie.moviePosterPath)
                if (data == 0) addMovieLocal(movieRecent)
            }, { throwable ->
                _isAllLoaded.value = true
                _messageError.value = throwable.message
            })

    private fun isAllLoaded() =
        isLoadedGenresResponse && isLoadedMoviesResponse && isLoadedMoviesRecent

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
                    MoviesTypes.RECENT.value -> {
                        _moviesRecent.value = response.data as List<MovieRecent>
                        isLoadedMoviesRecent = true
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
