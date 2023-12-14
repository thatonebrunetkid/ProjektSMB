package com.example.projektsmb

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.projektsmb.Data.Child
import com.example.projektsmb.Data.ChildRepository
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChildViewModel(application: Application, parentId : String) : AndroidViewModel(application) {

    private val repository : ChildRepository
    var firebaseDatabase : FirebaseDatabase
    val children : StateFlow<HashMap<String, Child>>

    init{
        firebaseDatabase = FirebaseDatabase.getInstance()
        repository = ChildRepository(firebaseDatabase, parentId)
        children = repository.allChildren
    }
    fun addChild(child: Child)
    {
        viewModelScope.launch { repository.insert(child) }
    }

    fun deleteChildren(child: Child)
    {
        viewModelScope.launch { repository.delete(child) }
    }

}