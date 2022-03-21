package com.example.weatherapp.data.remote

import com.example.weatherapp.pojo.model.weather.WeatherResponse
import com.example.weatherapp.util.BASE_URL
import com.google.android.gms.maps.model.LatLng
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ConnectionProvider : RemoteSource {

    private val retrofit: Retrofit = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val weatherApi: WeatherApi = retrofit.create(WeatherApi::class.java)
    
    override suspend fun getWeatherByLatAndLing(latLng: LatLng, language : String): WeatherResponse =
        weatherApi.getWeatherData(
            lat = latLng.latitude.toString(),
            lon = latLng.longitude.toString(),
            lang = language
        )

}