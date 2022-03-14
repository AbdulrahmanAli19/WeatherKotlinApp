package com.example.weatherapp.data.remote

import com.example.weatherapp.pojo.model.WeatherResponse
import retrofit2.http.GET

interface WeatherApi {


    @GET("")
    suspend fun getDayWithLatAndLon(): WeatherResponse

    @GET("")
    suspend fun getWeekWithLatAndLon(): WeatherResponse
}