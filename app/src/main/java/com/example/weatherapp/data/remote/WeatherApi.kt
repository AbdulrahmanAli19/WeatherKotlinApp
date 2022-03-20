package com.example.weatherapp.data.remote

import com.example.weatherapp.pojo.model.weather.WeatherResponse
import com.example.weatherapp.util.MY_API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {


    @GET("onecall")
    suspend fun getWeatherData(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appid: String = MY_API_KEY
    ): WeatherResponse
}