package com.example.weatherapp.data.remote


sealed class Resource<out T>(val status: Status, val data: T?, val message: String?) {

    data class Success<out R>(val _data: R?) : Resource<R>(
        status = Status.SUCCESS,
        data = _data,
        message = null
    )

    data class Error(val exception: String) : Resource<Nothing>(
        status = Status.ERROR,
        data = null,
        message = exception
    )

    data class Loading<out R>(val _data: R?, val isLoading: Boolean) : Resource<R>(
        status = Status.LOADING,
        data = _data,
        message = null
    )
}