package com.example.weatherapp.data.local

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import com.example.weatherapp.pojo.model.dbentities.CashedEntity
import com.example.weatherapp.pojo.model.dbentities.FavoriteEntity

class ConcreteLocalSource private constructor(
    context: Context,
) : LocalSource {

    private val cashedDao: CashedDao
    private val favoriteDao: FavoriteDao

    init {
        val appDatabase: AppDatabase = AppDatabase.getDatabase(context)
        cashedDao = appDatabase.cashedDao()
        favoriteDao = appDatabase.favoriteDao()
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: ConcreteLocalSource? = null
        fun getInstance(context: Context): ConcreteLocalSource =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: ConcreteLocalSource(context).also { INSTANCE = it }
            }
    }

    override fun insertFavorite(favoriteEntity: FavoriteEntity) {
        favoriteDao.insertFav(favoriteEntity)
    }

    override fun deleteFavorite(favoriteEntity: FavoriteEntity) {
        favoriteDao.deleteFav(favoriteEntity)
    }

    override fun updateFavorite(favoriteEntity: FavoriteEntity) {
        favoriteDao.updateFav(favoriteEntity)
    }

    override fun getAllFavorites(): LiveData<List<FavoriteEntity>> {
        return favoriteDao.getAllFavorites()
    }

    override fun insertCashed(cashed: CashedEntity) {
        cashedDao.insertCashed(cashed)
    }

    override fun deleteCashed(cashed: CashedEntity) {
        cashedDao.deleteCashed(cashed)
    }

    override fun updateCashed(cashed: CashedEntity) {
        cashedDao.updateCashed(cashed)
    }

    override fun getAllCashed(): LiveData<List<CashedEntity>> {
        return cashedDao.getAllCashed()
    }
}