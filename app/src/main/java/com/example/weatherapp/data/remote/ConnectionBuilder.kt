package com.example.weatherapp.data.remote

import com.example.weatherapp.util.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ConnectionBuilder {

    private var retrofit: Retrofit = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private var weatherApi: WeatherApi = retrofit.create(WeatherApi::class.java)

    fun get() {

    }
}