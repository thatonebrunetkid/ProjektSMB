package com.example.projektsmb.Data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Parent")
data class Parent(
    @PrimaryKey(autoGenerate = true)val id : Int = 0,
    val ListName : String
)
