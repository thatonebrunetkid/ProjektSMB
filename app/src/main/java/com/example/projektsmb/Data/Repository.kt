package com.example.projektsmb.Data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class Repository(context: Context) : ParentDao {

    private val dao = ParentDb.getInstance(context).parentDao()

    override suspend fun insert(parent: Parent) = withContext(Dispatchers.IO) {
       dao.insert(parent)
    }

    override suspend fun delete(parent: Parent) = withContext(Dispatchers.IO) {
        dao.delete(parent)
    }

    override fun selectAll(): Flow<List<Parent>> {
       return dao.selectAll()
    }
}

class ChildRepository(context: Context) : ChildDao{

    private val childDao = ParentDb.getInstance(context).childDao()

    override suspend fun insert(child: Child) = withContext(Dispatchers.IO){
        childDao.insert(child)
    }

    override suspend fun delete(child: Child) = withContext(Dispatchers.IO) {
        childDao.delete(child)
    }

    override fun getAll(pId: Int): Flow<List<Child>> {
        return childDao.getAll(pId)
    }

    override suspend fun update(child: Child) = withContext(Dispatchers.IO) {
        childDao.update(child)
    }

}