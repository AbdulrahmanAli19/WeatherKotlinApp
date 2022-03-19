package com.example.weatherapp.pojo.model.dbentities

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weatherapp.pojo.model.weather.WeatherResponse

@Entity(tableName = "favorite_table")
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id: Int,
    var lat: String,
    var lon: String,
    var locationName: String,
    var cashedData: WeatherResponse
)