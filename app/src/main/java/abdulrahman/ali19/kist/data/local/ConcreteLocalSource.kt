package abdulrahman.ali19.kist.data.local

import abdulrahman.ali19.kist.pojo.model.dbentities.AlertEntity
import abdulrahman.ali19.kist.pojo.model.dbentities.CashedEntity
import abdulrahman.ali19.kist.pojo.model.dbentities.FavoriteEntity
import android.annotation.SuppressLint
import android.content.Context

class ConcreteLocalSource private constructor(
    context: Context,
) : LocalSource {

    private val cashedDao: CashedDao
    private val favoriteDao: FavoriteDao
    private val alertDao: AlertDao

    init {
        val appDatabase: AppDatabase = AppDatabase.getDatabase(context)
        cashedDao = appDatabase.cashedDao()
        favoriteDao = appDatabase.favoriteDao()
        alertDao = appDatabase.alertDao()
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: ConcreteLocalSource? = null
        fun getInstance(context: Context): ConcreteLocalSource =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: ConcreteLocalSource(context.applicationContext).also { INSTANCE = it }
            }
    }

    override suspend fun insertFavorite(favoriteEntity: FavoriteEntity) {
        favoriteDao.insertFav(favoriteEntity)
    }

    override suspend fun deleteFavorite(favoriteEntity: FavoriteEntity) {
        favoriteDao.deleteFav(favoriteEntity)
    }

    override suspend fun updateFavorite(favoriteEntity: FavoriteEntity) {
        favoriteDao.updateFav(favoriteEntity)
    }

    override suspend fun getAllFavorites(): List<FavoriteEntity> {
        return favoriteDao.getAllFavorites()
    }

    override suspend fun insertCashed(cashed: CashedEntity) {
        cashedDao.insertCashed(cashed)
    }

    override suspend fun deleteCashed(cashed: CashedEntity) {
        cashedDao.deleteCashed(cashed)
    }

    override suspend fun updateCashed(cashed: CashedEntity) {
        cashedDao.updateCashed(cashed)
    }

    override suspend fun getAllCashed(): List<CashedEntity> {
        return cashedDao.getAllCashed()
    }

    override suspend fun insertAlert(alertEntity: AlertEntity) {
        alertDao.insertAlert(alertEntity)
    }

    override suspend fun deleteAlert(alertEntity: AlertEntity) {
        alertDao.deleteAlert(alertEntity)
    }

    override suspend fun updateAlert(alertEntity: AlertEntity) {
        alertDao.updateAlert(alertEntity)
    }

    override suspend fun getAllAlerts(): List<AlertEntity> {
        return alertDao.getAllAlerts()
    }
}