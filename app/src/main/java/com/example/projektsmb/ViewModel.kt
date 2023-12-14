package com.example.projektsmb

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projektsmb.Data.Child
import com.example.projektsmb.Data.ChildRepository
import com.example.projektsmb.Data.Parent
import com.example.projektsmb.Data.ParentDb
import com.example.projektsmb.Data.Repository
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class ViewModel(application: Application) : AndroidViewModel(application) {

    private val repository :Repository
    var firebaseDatabase : FirebaseDatabase
    val parents : StateFlow<HashMap<String, Parent>>

    init{
        firebaseDatabase = FirebaseDatabase.getInstance()
        repository = Repository(firebaseDatabase)
        parents = repository.allProducts
    }

    fun addParent(parent: Parent){
        viewModelScope.launch { repository.insert(parent) }
    }

    fun deleteParent(parent : Parent)
    {
        viewModelScope.launch { repository.delete(parent) }
    }

}





/*
class ViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = Repository(app.applicationContext)
    private val childRepo = ChildRepository(app.applicationContext)

    fun getAllParents() : Flow<List<Parent>>
    {
        return repo.selectAll()
    }

    fun addParent(parent: Parent)
    {
        viewModelScope.launch { repo.fbInsert(parent) }
    }

    fun deleteParent(parent: Parent)
    {
        viewModelScope.launch { repo.delete(parent) }
    }

    fun getAllChilds(pId : Int) : Flow<List<Child>>
    {
        return childRepo.getAll(pId)
    }

    fun addChild(child : Child)
    {
        viewModelScope.launch { childRepo.insert(child) }
    }

    fun deleteChild(child : Child)
    {
        viewModelScope.launch { childRepo.delete(child) }
    }

    fun updateChild(child: Child)
    {
        viewModelScope.launch { childRepo.update(child) }
    }

 */
