package com.example.weatherapp.data.remote

import com.example.weatherapp.pojo.model.WeatherResponse
import retrofit2.http.GET

interface WeatherApi {


    @GET("onecall?lat=38.257778&lon=-122.054169&appid=5ef5f8bf184ca5e7d86a59392ca23a7c")
    suspend fun getDayWithLatAndLon(): WeatherResponse

    @GET("")
    suspend fun getWeekWithLatAndLon(): WeatherResponse
}