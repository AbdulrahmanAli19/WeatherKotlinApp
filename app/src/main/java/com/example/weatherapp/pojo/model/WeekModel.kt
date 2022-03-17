package com.example.weatherapp.pojo.model

import com.example.weatherapp.pojo.model.weather.Temp

data class WeekModel(
    var date: Long,
    var temp: Temp,
    var icon: String
)
