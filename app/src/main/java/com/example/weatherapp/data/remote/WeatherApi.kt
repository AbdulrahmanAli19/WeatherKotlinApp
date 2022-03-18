package com.example.weatherapp.data.remote

import com.example.weatherapp.pojo.model.weather.WeatherResponse
import com.example.weatherapp.util.MY_API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {


    @GET("onecall?lat=25.933347&lon=31.200018&appid=5ef5f8bf184ca5e7d86a59392ca23a7c")
    suspend fun getDayWithLatAndLon(): WeatherResponse

    @GET("onecall")
    suspend fun getWeatherData(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appid: String = MY_API_KEY
    ): WeatherResponse
}