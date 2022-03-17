package com.example.weatherapp.ui.splash.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.weatherapp.data.remote.ConnectionBuilder
import com.example.weatherapp.data.remote.Resource
import com.example.weatherapp.pojo.repo.Repository
import kotlinx.coroutines.Dispatchers

class SplashViewModel(repository: Repository) : ViewModel() {
    private val TAG = "SplashViewModel"

    fun getData() = liveData(Dispatchers.IO) {
        val data = ConnectionBuilder
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = data.get()))
        } catch (exception: Exception) {
            emit(
                Resource.error(
                    message = exception.message ?: "SomethingWong happened",
                    data = null
                )
            )
        }
    }
}