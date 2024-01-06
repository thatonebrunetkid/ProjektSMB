package com.example.projektsmb.Data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDataDao{
    @Insert
    suspend fun insert(location : LocationData)

    @Delete
    suspend fun delete(location : LocationData)

    @Query("SELECT * FROM LocationName")
    fun getAll() : Flow<List<LocationData>>

    @Query("SELECT * FROM LocationName WHERE id = :pId")
    fun getParticular(pId : Int) : LocationData
}