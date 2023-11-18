package com.example.projektsmb.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Parent::class, Child::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun parentDao() : ParentDao
    abstract fun childDao() : ChildDao

}

object ParentDb{
    private var db: com.example.projektsmb.Data.Database ? = null


    fun getInstance(context: Context): com.example.projektsmb.Data.Database {
        if(db == null)
        {
            db = Room.databaseBuilder(context, com.example.projektsmb.Data.Database::class.java, "database")
                .build()
        }
        return db!!
    }
}