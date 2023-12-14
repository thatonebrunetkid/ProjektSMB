package com.example.projektsmb.Data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
@Entity("Parent")
data class Parent(
    @PrimaryKey var id : String,
    val ListName : String? = null
)
