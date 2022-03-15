package com.example.weatherapp.pojo.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Minutely(
    @SerializedName("dt")
    val dt: Int,
    @SerializedName("precipitation")
    val precipitation: Int
) : Serializable