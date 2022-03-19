package com.example.weatherapp.ui.splash.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.weatherapp.data.remote.ConnectionBuilder
import com.example.weatherapp.data.remote.Resource
import com.example.weatherapp.pojo.model.weather.WeatherResponse
import com.example.weatherapp.pojo.repo.RepositoryInterface
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers

private const val TAG = "SplashViewModel"

class SplashViewModel(private val repository: RepositoryInterface) : ViewModel() {

    fun getData() = liveData(Dispatchers.IO) {
        val data = ConnectionBuilder
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = data.get()))
        } catch (exception: Exception) {
            emit(
                Resource.error(
                    message = exception.message ?: "Something Wrong happened",
                    data = null
                )
            )
        }
    }

    fun getDataFromRepo(
        latLng: LatLng = LatLng(
            30.02401127333763,
            31.564412713050846
        )
    ) = liveData<Resource<WeatherResponse>>(Dispatchers.IO) {
        val request = repository.getWeatherByLatLon(latLng)
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = request))
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