package com.example.weatherapp.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.weatherapp.data.remote.ConnectionBuilder
import com.example.weatherapp.data.remote.Resource
import com.example.weatherapp.pojo.repo.RepositoryInterface
import kotlinx.coroutines.Dispatchers

class HomeViewModel(private val repositoryInterface: RepositoryInterface) : ViewModel() {
    private val TAG = "HomeViewModel"

    fun getData() = liveData(Dispatchers.IO) {
        val data = ConnectionBuilder.get()
        emit(Resource.loading(data = data))
        try {
            emit(Resource.success(data = data))
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