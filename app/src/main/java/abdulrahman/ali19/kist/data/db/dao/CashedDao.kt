package abdulrahman.ali19.kist.data.db.dao

import abdulrahman.ali19.kist.data.pojo.model.dbentities.CashedEntity
import androidx.room.*

@Dao
interface CashedDao {

    @Update
    fun updateCashed(cashedEntity:CashedEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCashed(cashedEntity: CashedEntity)

    @Delete
    fun deleteCashed(cashedEntity: CashedEntity)

    @Query("SELECT * FROM cashed_table")
    fun getAllCashed(): List<CashedEntity>

}