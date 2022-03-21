package com.example.weatherapp.pojo.repo

import android.annotation.SuppressLint
import com.example.weatherapp.data.local.LocalSource
import com.example.weatherapp.data.preferences.PreferenceInterface
import com.example.weatherapp.data.remote.RemoteSource
import com.example.weatherapp.pojo.model.dbentities.AlertEntity
import com.example.weatherapp.pojo.model.dbentities.CashedEntity
import com.example.weatherapp.pojo.model.dbentities.FavoriteEntity
import com.example.weatherapp.pojo.model.weather.WeatherResponse
import com.google.android.gms.maps.model.LatLng

private const val TAG = "Repository.dev"

class Repository private constructor(
    private val remoteSource: RemoteSource,
    private val localSource: LocalSource,
    private val preferences: PreferenceInterface
) : RepositoryInterface {

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: Repository? = null
        fun getInstance(
            remoteSource: RemoteSource,
            localSource: LocalSource,
            preferences: PreferenceInterface
        ): Repository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Repository(remoteSource, localSource, preferences).also {
                    INSTANCE = it
                }
            }
    }

    override suspend fun insertFavorite(favoriteEntity: FavoriteEntity) {
        localSource.insertFavorite(favoriteEntity)
    }

    override suspend fun deleteFavorite(favoriteEntity: FavoriteEntity) {
        localSource.deleteFavorite(favoriteEntity)
    }

    override suspend fun updateFavorite(favoriteEntity: FavoriteEntity) {
        localSource.updateFavorite(favoriteEntity)
    }

    override suspend fun getAllFavorites(): List<FavoriteEntity> {
        return localSource.getAllFavorites()
    }

    override suspend fun insertCashed(cashedEntity: CashedEntity) {
        localSource.insertCashed(cashedEntity)
    }

    override suspend fun deleteCashed(cashedEntity: CashedEntity) {
        localSource.deleteCashed(cashedEntity)
    }

    override suspend fun updateCashed(cashedEntity: CashedEntity) {
        localSource.updateCashed(cashedEntity)
    }

    override suspend fun getAllCashed(): List<CashedEntity> {
        return localSource.getAllCashed()
    }

    override suspend fun getWeatherByLatLon(latLng: LatLng, language : String): WeatherResponse {
        return remoteSource.getWeatherByLatAndLing(latLng, language)
    }

    override suspend fun insertAlert(alertEntity: AlertEntity) {
        localSource.insertAlert(alertEntity)
    }

    override suspend fun deleteAlert(alertEntity: AlertEntity) {
        localSource.deleteAlert(alertEntity)
    }

    override suspend fun updateAlert(alertEntity: AlertEntity) {
        localSource.updateAlert(alertEntity)
    }

    override suspend fun getAllAlerts(): List<AlertEntity> {
        return localSource.getAllAlerts()
    }

    override fun getTimestamp(): Long? {
        return preferences.getTimestamp()
    }

    override fun setTimestamp(timestamp: Long) {
        preferences.setTimestamp(timestamp)
    }

    override fun getLatLon(): LatLng {
        return preferences.getLatLon()
    }

    override fun setLatLon(latLng: LatLng) {
        preferences.setLatLon(latLng)
    }

    override fun setTempUnit(tempUnit: String) {
        preferences.setTempUnit(tempUnit)
    }

    override fun getTempUnit(): String {
        return preferences.getTempUnit()
    }

    override fun getWindSpeedUnit(): String {
        return preferences.getWindSpeedUnit()
    }

    override fun setWindSpeedUnit(windSpeedUnit: String) {
        preferences.setWindSpeedUnit(windSpeedUnit)
    }

    override fun getLanguage(): String {
        return preferences.getLanguage()
    }

    override fun setLanguage(string: String) {
        preferences.setLanguage(string)
    }


}