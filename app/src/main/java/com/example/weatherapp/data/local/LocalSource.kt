package com.example.weatherapp.data.local

import androidx.lifecycle.LiveData
import com.example.weatherapp.pojo.model.dbentities.CashedEntity
import com.example.weatherapp.pojo.model.dbentities.FavoriteEntity

interface LocalSource {

    fun insertFavorite(favoriteEntity: FavoriteEntity)

    fun deleteFavorite(favoriteEntity: FavoriteEntity)

    fun updateFavorite(favoriteEntity: FavoriteEntity)

    fun getAllFavorites(): LiveData<List<FavoriteEntity>>

    fun insertCashed(cashed: CashedEntity)

    fun deleteCashed(cashed: CashedEntity)

    fun updateCashed(cashed: CashedEntity)

    fun getAllCashed(): LiveData<List<CashedEntity>>

}