package com.example.projektsmb.Data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng

@Entity(tableName = "LocationName")
data class LocationData (
    @PrimaryKey(autoGenerate = true) var id : Int = 0,
    val shopName : String,
    val lat : Double,
    val lng : Double,
    val radius : Float
    )