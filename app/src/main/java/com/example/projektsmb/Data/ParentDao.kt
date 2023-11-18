package com.example.projektsmb.Data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ParentDao {

    @Insert
    suspend fun insert(parent : Parent)

    @Delete
    suspend fun delete(parent : Parent)

    @Query("SELECT * FROM Parent")
    fun selectAll() : Flow<List<Parent>>


}