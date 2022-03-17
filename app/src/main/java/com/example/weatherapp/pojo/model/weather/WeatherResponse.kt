package com.example.weatherapp.pojo.model.weather


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class WeatherResponse(
    @SerializedName("current")
    val current: Current = Current(),
    @SerializedName("daily")
    val daily: List<Daily> =  arrayListOf(),
    @SerializedName("hourly")
    val hourly: List<Hourly> = arrayListOf(),
    @SerializedName("alerts")
    val alerts: List<Alert> = arrayListOf(),
    @SerializedName("lat")
    val lat: Double = 0.0,
    @SerializedName("lon")
    val lon: Double = 0.0,
    @SerializedName("minutely")
    val minutely: List<Minutely> = arrayListOf(),
    @SerializedName("timezone")
    val timezone: String = "Cairo",
    @SerializedName("timezone_offset")
    val timezoneOffset: Int = 0
) : Serializable