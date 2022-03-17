package com.example.weatherapp.pojo.model.weather


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FeelsLike(
    @SerializedName("day")
    val day: Double,
    @SerializedName("eve")
    val eve: Double,
    @SerializedName("morn")
    val morn: Double,
    @SerializedName("night")
    val night: Double
) : Serializable