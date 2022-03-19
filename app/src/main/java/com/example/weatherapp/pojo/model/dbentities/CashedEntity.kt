package com.example.weatherapp.pojo.model.dbentities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weatherapp.pojo.model.weather.WeatherResponse

@Entity(tableName = "cashed_table")
data class CashedEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var lat: String,
    var lon: String,
    var cashedData: WeatherResponse
)