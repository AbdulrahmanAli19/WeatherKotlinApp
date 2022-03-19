package com.example.weatherapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.weatherapp.pojo.model.dbentities.CashedEntity

@Dao
interface CashedDao {

    @Update
    fun updateCashed(cashedEntity: CashedEntity)

    @Insert
    fun insertCashed(cashedEntity: CashedEntity)

    @Delete
    fun deleteCashed(cashedEntity: CashedEntity)

    @Query("SELECT * FROM favorite_table")
    fun getAllCashed(): LiveData<List<CashedEntity>>

}