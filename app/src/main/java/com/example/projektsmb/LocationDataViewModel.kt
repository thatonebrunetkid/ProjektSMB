package com.example.projektsmb

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.projektsmb.Data.LocationData
import com.example.projektsmb.Data.LocationDataRepository
import com.example.projektsmb.Data.LocationDb
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class LocationDataViewModel(application: Application) : AndroidViewModel(application) {

    private val repository : LocationDataRepository
    val allLocations: Flow<List<LocationData>>

    init {
        val locationDataDao = LocationDb.getDatabase(application).locationDataDao()
        repository = LocationDataRepository(locationDataDao)
        allLocations = repository.allLocations
    }

     fun insert(locationData : LocationData) = viewModelScope.launch { repository.insert(locationData) }
     fun delete(locationData : LocationData) = viewModelScope.launch { repository.delete(locationData) }

    fun getParticular(pId : Int) = viewModelScope.launch { repository.getParticular(pId) }
}