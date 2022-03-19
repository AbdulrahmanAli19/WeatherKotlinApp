package com.example.weatherapp.pojo.repo

import androidx.lifecycle.LiveData
import com.example.weatherapp.data.remote.Resource
import com.example.weatherapp.pojo.model.dbentities.CashedEntity
import com.example.weatherapp.pojo.model.dbentities.FavoriteEntity
import com.example.weatherapp.pojo.model.weather.WeatherResponse
import com.google.android.gms.maps.model.LatLng

interface RepositoryInterface {

    fun insertFavorite(favoriteEntity: FavoriteEntity)
    fun deleteFavorite(favoriteEntity: FavoriteEntity)
    fun updateFavorite(favoriteEntity: FavoriteEntity)
    fun getAllFavorites(): LiveData<List<FavoriteEntity>>

    fun insertCashed(cashedEntity: CashedEntity)
    fun deleteCashed(cashedEntity: CashedEntity)
    fun updateCashed(cashedEntity: CashedEntity)
    fun getAllCashed(): LiveData<List<CashedEntity>>

    suspend fun getWeatherByLatLon(latLng: LatLng): WeatherResponse
}