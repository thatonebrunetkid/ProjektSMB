package com.example.projektsmb.Data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Child")
data class Child(
    @PrimaryKey var id : String,
    val productName : String,
    val price : Double,
    val quantity : String,
    var bought : Boolean = false,
    val parentId : String

)