package com.sunasterisk.moviedb_51.data.source.remote.response

import com.sunasterisk.moviedb_51.utils.Status

data class BaseResponse<T>(
    val status: Status,
    val data: T?,
    val messageError: String?
) {
    companion object {
        fun <T> success(data: T?): BaseResponse<T> =
            BaseResponse(Status.SUCCESS, data, null)

        fun <T> error(msg: String?): BaseResponse<T> =
            BaseResponse(Status.ERROR, null, msg)

        fun <T> loading(): BaseResponse<T> =
            BaseResponse(Status.LOADING, null, null)
    }
}
