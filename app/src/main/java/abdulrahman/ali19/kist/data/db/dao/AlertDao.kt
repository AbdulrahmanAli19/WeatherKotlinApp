package abdulrahman.ali19.kist.data.db.dao

import abdulrahman.ali19.kist.data.pojo.model.dbentities.AlertEntity
import androidx.room.*

@Dao
interface AlertDao {
    @Update
    fun updateAlert(alertEntity: AlertEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAlert(alertEntity: AlertEntity)

    @Delete
    fun deleteAlert(alertEntity: AlertEntity)

    @Query("SELECT * FROM alert_table")
    fun getAllAlerts(): List<AlertEntity>


}