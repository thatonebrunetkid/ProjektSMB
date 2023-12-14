package com.example.projektsmb.Data

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.room.Dao
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext

class Repository(private val firebaseDatabase: FirebaseDatabase) {

    val allProducts = MutableStateFlow(HashMap<String, Parent>())
    var auth = FirebaseAuth.getInstance()
    private val path = "${auth.currentUser!!.uid}/parents"

    init{
        firebaseDatabase.getReference(path).addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val parent = Parent(
                    id = snapshot.ref.key as String,
                    ListName = snapshot.child("listName").value as String
                )
                allProducts.value = allProducts.value.toMutableMap().apply {
                    put(parent.id, parent)
                }as HashMap<String, Parent>
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val parent = Parent(
                    id = snapshot.ref.key as String,
                    ListName = snapshot.child("listName").value as String
                )

                allProducts.value = allProducts.value.toMutableMap().apply {
                    put(parent.id, parent)
                } as HashMap<String, Parent>
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val parent = Parent(
                    id = snapshot.ref.key as String,
                    ListName = snapshot.child("listName").value as String
                )

                allProducts.value = allProducts.value.toMutableMap().apply {
                    put(parent.id, parent)
                } as HashMap<String, Parent>
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                val parent = Parent(
                    id = snapshot.ref.key as String,
                    ListName = snapshot.child("listName").value as String
                )

                allProducts.value = allProducts.value.toMutableMap().apply {
                    put(parent.id, parent)
                } as HashMap<String, Parent>
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    suspend fun insert(parent: Parent)
    {
        firebaseDatabase.getReference(path).push().also {
            parent.id = it.ref.key!!
            it.setValue(parent)
        }
    }

    suspend fun delete(parent: Parent)
    {
        firebaseDatabase.getReference("$path/${parent.id}").removeValue()
    }






    /*
    suspend fun insert(parent : Parent)
    {
        val key = database.child(auth.currentUser!!.uid).push().key
        database.child(auth.currentUser!!.uid).child("parents").child(key!!).setValue(parent.ListName)
    }

     */






}
