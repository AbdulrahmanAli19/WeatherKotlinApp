package com.example.weatherapp.ui.splash.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.remote.ConnectionProvider
import com.example.weatherapp.data.remote.Resource
import com.example.weatherapp.pojo.model.dbentities.CashedEntity
import com.example.weatherapp.pojo.model.weather.WeatherResponse
import com.example.weatherapp.pojo.repo.RepositoryInterface
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

private const val TAG = "SplashViewModel"

class SplashViewModel(private val repository: RepositoryInterface) : ViewModel() {

    fun getDataFromRepo(
        latLng: LatLng = LatLng(
            30.02401127333763,
            31.564412713050846
        )
    ) = liveData(Dispatchers.IO) {
        Log.d(TAG, "getDataFromRepo: called and its loading ")
        emit(Resource.Loading(isLoading = true, _data = null))
        try {
            emit(Resource.Success(_data = repository.getWeatherByLatLon(latLng)))
            Log.d(TAG, "getDataFromRepo: scs")
        } catch (exception: Exception) {
            Log.d(TAG, "getDataFromRepo: Exception ${exception.message}")
            emit(
                Resource.Error(
                    exception.message ?: "SomethingWong happened",
                )
            )
        }
    }

    fun saveResponse(weatherResponse: WeatherResponse) {
        GlobalScope.launch {
            repository.insertCashed(CashedEntity(cashedData = weatherResponse))
        }
    }


    fun setTimeStamp(msTime: Long) = repository.setTimestamp(msTime)

    fun setLatLon(latLng: LatLng) = repository.setLatLon(latLng)

}