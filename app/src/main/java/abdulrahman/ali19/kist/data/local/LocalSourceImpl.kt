package abdulrahman.ali19.kist.data.local

import abdulrahman.ali19.kist.data.db.AppDatabase
import abdulrahman.ali19.kist.data.pojo.model.dbentities.AlertEntity
import abdulrahman.ali19.kist.data.pojo.model.dbentities.CashedEntity
import abdulrahman.ali19.kist.data.pojo.model.dbentities.FavoriteEntity

class LocalSourceImpl constructor(
    private val appDatabase: AppDatabase
) : LocalSource {

    override suspend fun insertFavorite(favoriteEntity: FavoriteEntity) {
        appDatabase.favoriteDao().insertFav(favoriteEntity)
    }

    override suspend fun deleteFavorite(favoriteEntity: FavoriteEntity) {
        appDatabase.favoriteDao().deleteFav(favoriteEntity)
    }

    override suspend fun updateFavorite(favoriteEntity: FavoriteEntity) {
        appDatabase.favoriteDao().updateFav(favoriteEntity)
    }

    override suspend fun getAllFavorites(): List<FavoriteEntity> {
        return appDatabase.favoriteDao().getAllFavorites()
    }

    override suspend fun insertCashed(cashed: CashedEntity) {
        appDatabase.cashedDao().insertCashed(cashed)
    }

    override suspend fun deleteCashed(cashed: CashedEntity) {
        appDatabase.cashedDao().deleteCashed(cashed)
    }

    override suspend fun updateCashed(cashed: CashedEntity) {
        appDatabase.cashedDao().updateCashed(cashed)
    }

    override suspend fun getAllCashed(): List<CashedEntity> {
        return appDatabase.cashedDao().getAllCashed()
    }

    override suspend fun insertAlert(alertEntity: AlertEntity) {
        appDatabase.alertDao().insertAlert(alertEntity)
    }

    override suspend fun deleteAlert(alertEntity: AlertEntity) {
        appDatabase.alertDao().deleteAlert(alertEntity)
    }

    override suspend fun updateAlert(alertEntity: AlertEntity) {
        appDatabase.alertDao().updateAlert(alertEntity)
    }

    override suspend fun getAllAlerts(): List<AlertEntity> {
        return appDatabase.alertDao().getAllAlerts()
    }
}