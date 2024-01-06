package com.example.projektsmb.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [LocationData::class], version = 1)
public abstract class LocationDb : RoomDatabase() {
    abstract fun locationDataDao() : LocationDataDao

    companion object{
        private var instance : LocationDb? = null

        fun getDatabase(context: Context) : LocationDb{
            if(instance != null){
                return instance as LocationDb
            }

            instance = Room.databaseBuilder(
                context.applicationContext,
                LocationDb::class.java,
                "Database"
            ).allowMainThreadQueries().build()

            return instance as LocationDb
        }
    }
}