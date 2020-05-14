package com.sunasterisk.moviedb_51.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sunasterisk.moviedb_51.data.source.remote.response.BaseResponse
import com.sunasterisk.moviedb_51.utils.Status

open class BaseViewModel<T> : ViewModel() {
    private val _isLoaded = MutableLiveData<Boolean>()
    private val _messageError = MutableLiveData<String>()
    private val _dataResponse = MutableLiveData<T>()
    val dataResponse: LiveData<T> get() = _dataResponse
    val isLoaded: LiveData<Boolean> get() = _isLoaded
    val messageError: LiveData<String> get() = _messageError

    fun handleData(response: BaseResponse<T>) {
        when (response.status) {
            Status.SUCCESS -> {
                _isLoaded.value = true
                _dataResponse.value = response.data
            }
            Status.ERROR -> {
                _messageError.value = response.messageError
            }
            Status.LOADING -> {
                _isLoaded.value = false
            }
        }
    }
}
