package com.example.weatherapp.data.local

import com.example.weatherapp.pojo.model.dbentities.CashedEntity
import com.example.weatherapp.pojo.model.dbentities.FavoriteEntity

interface LocalSource {

    suspend fun insertFavorite(favoriteEntity: FavoriteEntity)

    suspend fun deleteFavorite(favoriteEntity: FavoriteEntity)

    suspend fun updateFavorite(favoriteEntity: FavoriteEntity)

    suspend fun getAllFavorites(): List<FavoriteEntity>

    suspend fun insertCashed(cashed: CashedEntity)

    suspend fun deleteCashed(cashed: CashedEntity)

    suspend fun updateCashed(cashed: CashedEntity)

    suspend fun getAllCashed(): List<CashedEntity>

}