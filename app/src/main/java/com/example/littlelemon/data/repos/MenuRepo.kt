package com.example.littlelemon.data.repos

import android.util.Log
import com.example.littlelemon.data.local.LocalMenuDao
import com.example.littlelemon.data.local.LocalMenuItem
import com.example.littlelemon.data.model.toEntity
import com.example.littlelemon.data.remote.NetworkClient
import kotlinx.coroutines.flow.Flow

//Repository class that handles data operations for the menu

class MenuRepo(private val dao: LocalMenuDao) {
    suspend fun getLocalMenu(): Flow<List<LocalMenuItem>> {
        return dao.getAllLocalMenuItems()
    }

    suspend fun refreshMenu() {
        val networkMenu = NetworkClient.fetchMenu()

        networkMenu?.menu?.let { items ->
            //Convert NetworkMenuItem to LocalMenuItem
            val entities = items.map { it.toEntity() }

            dao.deleteLocalMenuItems()
            dao.insertIntoLocalMenuItems(entities)
            Log.d("MenuRepo", "Inserted ${entities.size} items into the database")
        }
    }

}