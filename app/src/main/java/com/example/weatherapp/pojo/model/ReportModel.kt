package com.example.weatherapp.pojo.model

import com.example.weatherapp.pojo.model.weather.Daily
import com.example.weatherapp.pojo.model.weather.Hourly

data class ReportModel(
    val week: ArrayList<Daily>,
    val day: ArrayList<Hourly>
)