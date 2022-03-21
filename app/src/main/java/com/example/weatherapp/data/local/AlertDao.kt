package com.example.weatherapp.data.local

import androidx.room.*
import com.example.weatherapp.pojo.model.dbentities.AlertEntity

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