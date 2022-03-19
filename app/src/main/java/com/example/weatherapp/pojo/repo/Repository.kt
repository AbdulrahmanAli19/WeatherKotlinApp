package com.example.weatherapp.pojo.repo

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import com.example.weatherapp.data.local.LocalSource
import com.example.weatherapp.data.remote.RemoteSource
import com.example.weatherapp.data.remote.Resource
import com.example.weatherapp.pojo.model.dbentities.CashedEntity
import com.example.weatherapp.pojo.model.dbentities.FavoriteEntity
import com.example.weatherapp.pojo.model.weather.WeatherResponse
import com.google.android.gms.maps.model.LatLng

private const val TAG = "Repository.dev"

class Repository private constructor(
    private val remoteSource: RemoteSource,
    private val localSource: LocalSource
) : RepositoryInterface {

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: Repository? = null
        fun getInstance(remoteSource: RemoteSource, localSource: LocalSource): Repository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Repository(remoteSource, localSource).also { INSTANCE = it }
            }
    }

    override fun insertFavorite(favoriteEntity: FavoriteEntity) {
        localSource.insertFavorite(favoriteEntity)
    }

    override fun deleteFavorite(favoriteEntity: FavoriteEntity) {
        localSource.deleteFavorite(favoriteEntity)
    }

    override fun updateFavorite(favoriteEntity: FavoriteEntity) {
        localSource.updateFavorite(favoriteEntity)
    }

    override fun getAllFavorites(): LiveData<List<FavoriteEntity>> {
        return localSource.getAllFavorites()
    }

    override fun insertCashed(cashedEntity: CashedEntity) {
        localSource.insertCashed(cashedEntity)
    }

    override fun deleteCashed(cashedEntity: CashedEntity) {
        localSource.deleteCashed(cashedEntity)
    }

    override fun updateCashed(cashedEntity: CashedEntity) {
        localSource.updateCashed(cashedEntity)
    }

    override fun getAllCashed(): LiveData<List<CashedEntity>> {
        return localSource.getAllCashed()
    }

    override suspend fun getWeatherByLatLon(latLng: LatLng): WeatherResponse {
        return remoteSource.getWeatherByLatAndLing(latLng)
    }


}