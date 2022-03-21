package com.example.weatherapp.data.remote

import com.example.weatherapp.pojo.model.weather.WeatherResponse
import com.google.android.gms.maps.model.LatLng

interface RemoteSource {

    suspend fun getWeatherByLatAndLing(latLng: LatLng, language: String): WeatherResponse
}