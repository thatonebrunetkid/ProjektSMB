package com.example.projektsmb.Data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Child")
data class Child(
    @PrimaryKey(autoGenerate = true)val id : Int = 0,
    val productName : String,
    val price : Double,
    val quantity : Int,
    var bought : Boolean = false,
    val parentId : Int

)