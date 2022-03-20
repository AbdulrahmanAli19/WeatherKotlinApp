package com.example.weatherapp.data.local

import com.example.weatherapp.pojo.model.dbentities.CashedEntity
import com.example.weatherapp.pojo.model.dbentities.FavoriteEntity

interface LocalSource {

    suspend fun insertFavorite(favoriteEntity: FavoriteEntity)

    fun deleteFavorite(favoriteEntity: FavoriteEntity)

    fun updateFavorite(favoriteEntity: FavoriteEntity)

    suspend fun getAllFavorites(): List<FavoriteEntity>

    fun insertCashed(cashed: CashedEntity)

    fun deleteCashed(cashed: CashedEntity)

    fun updateCashed(cashed: CashedEntity)

    suspend fun getAllCashed(): List<CashedEntity>

}