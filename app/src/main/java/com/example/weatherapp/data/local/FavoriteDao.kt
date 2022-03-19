package com.example.weatherapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.weatherapp.pojo.model.dbentities.FavoriteEntity

@Dao
interface FavoriteDao {

    @Update
    fun updateFav(favoriteEntity: FavoriteEntity)

    @Insert
    fun insertFav(favoriteEntity: FavoriteEntity)

    @Delete
    fun deleteFav(favoriteEntity: FavoriteEntity)

    @Query("SELECT * FROM favorite_table")
    fun getAllFavorites(): LiveData<List<FavoriteEntity>>
}