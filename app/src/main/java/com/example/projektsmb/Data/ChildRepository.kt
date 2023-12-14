package com.example.projektsmb.Data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow

class ChildRepository(private val firebaseDatabase: FirebaseDatabase, parentId : String) {

    val allChildren = MutableStateFlow(HashMap<String, Child>())
    var auth = FirebaseAuth.getInstance()
    private val path = "${auth.currentUser!!.uid}/parents/$parentId"

    init{
        firebaseDatabase.getReference(path).addChildEventListener(object : ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val child = Child(
                    id = snapshot.ref.key as String,
                    productName = snapshot.child("productName").value as String,
                    price = snapshot.child("price").value as Double,
                    quantity = snapshot.child("quantity").value as String,
                    bought = snapshot.child("bought").value as Boolean,
                    parentId = snapshot.child("parentId").value as String
                )

                allChildren.value = allChildren.value.toMutableMap().apply {
                    put(child.id, child)
                }as HashMap<String, Child>
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val child = Child(
                    id = snapshot.ref.key as String,
                    productName = snapshot.child("productName").value as String,
                    price = snapshot.child("price").value as Double,
                    quantity = snapshot.child("quantity").value as String,
                    bought = snapshot.child("bought").value as Boolean,
                    parentId = snapshot.child("parentId").value as String
                )

                allChildren.value = allChildren.value.toMutableMap().apply {
                    put(child.id, child)
                }as HashMap<String, Child>
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val child = Child(
                    id = snapshot.ref.key as String,
                    productName = snapshot.child("productName").value as String,
                    price = snapshot.child("price").value as Double,
                    quantity = snapshot.child("quantity").value as String,
                    bought = snapshot.child("bought").value as Boolean,
                    parentId = snapshot.child("parentId").value as String
                )

                allChildren.value = allChildren.value.toMutableMap().apply {
                    put(child.id, child)
                }as HashMap<String, Child>
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                val child = Child(
                    id = snapshot.ref.key as String,
                    productName = snapshot.child("productName").value as String,
                    price = snapshot.child("price").value as Double,
                    quantity = snapshot.child("quantity").value as String,
                    bought = snapshot.child("bought").value as Boolean,
                    parentId = snapshot.child("parentId").value as String
                )

                allChildren.value = allChildren.value.toMutableMap().apply {
                    put(child.id, child)
                }as HashMap<String, Child>
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }


    suspend fun insert(child : Child)
    {
        firebaseDatabase.getReference(path).push().also {
            child.id = it.ref.key!!
            it.setValue(child)
        }
    }

    suspend fun delete(child: Child)
    {
        firebaseDatabase.getReference("$path/${child.id}").removeValue()
    }

}