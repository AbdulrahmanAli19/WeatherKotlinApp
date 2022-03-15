package com.example.weatherapp.data.remote

import com.example.weatherapp.pojo.model.WeatherResponse
import com.example.weatherapp.util.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ConnectionBuilder {

    private val retrofit: Retrofit = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val weatherApi: WeatherApi = retrofit.create(WeatherApi::class.java)

    suspend fun get() : WeatherResponse {
        return weatherApi.getDayWithLatAndLon()
    }
}