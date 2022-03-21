package com.example.weatherapp.data.local

import androidx.room.*
import com.example.weatherapp.pojo.model.dbentities.CashedEntity

@Dao
interface CashedDao {

    @Update
    fun updateCashed(cashedEntity: CashedEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCashed(cashedEntity: CashedEntity)

    @Delete
    fun deleteCashed(cashedEntity: CashedEntity)

    @Query("SELECT * FROM cashed_table")
    fun getAllCashed(): List<CashedEntity>

}