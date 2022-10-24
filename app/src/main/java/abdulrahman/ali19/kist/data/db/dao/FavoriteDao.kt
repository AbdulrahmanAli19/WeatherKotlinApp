package abdulrahman.ali19.kist.data.db.dao

import abdulrahman.ali19.kist.data.pojo.model.dbentities.FavoriteEntity
import androidx.room.*

@Dao
interface FavoriteDao {

    @Update
    fun updateFav(favoriteEntity: FavoriteEntity)

    @Insert
    fun insertFav(favoriteEntity: FavoriteEntity)

    @Delete
    fun deleteFav(favoriteEntity: FavoriteEntity)

    @Query("SELECT * FROM favorite_table")
    fun getAllFavorites(): List<FavoriteEntity>
}