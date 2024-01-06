package com.example.projektsmb.Data

import kotlinx.coroutines.flow.Flow

class LocationDataRepository(private val locationDataDao : LocationDataDao) {

    val allLocations : Flow<List<LocationData>> = locationDataDao.getAll()

    suspend fun insert(locationData : LocationData)
    {
        locationDataDao.insert(locationData)
    }

    suspend fun delete(locationData: LocationData)
    {
        locationDataDao.delete(locationData)
    }

    suspend fun getParticular(pId : Int)
    {
        locationDataDao.getParticular(pId)
    }
}