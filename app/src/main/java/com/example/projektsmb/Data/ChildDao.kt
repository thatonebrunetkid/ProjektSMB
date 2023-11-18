package com.example.projektsmb.Data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface ChildDao{

    @Insert
    suspend fun insert(child: Child)

    @Delete
    suspend fun delete(child: Child)

    @Query("SELECT * FROM Child WHERE parentId = :pId")
    fun getAll(pId : Int) : Flow<List<Child>>

    @Update
    suspend fun update(child: Child)

}