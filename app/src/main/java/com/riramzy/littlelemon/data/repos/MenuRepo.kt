package com.riramzy.littlelemon.data.repos

import android.util.Log
import com.riramzy.littlelemon.data.local.menu.LocalMenuDao
import com.riramzy.littlelemon.data.local.menu.LocalMenuItem
import com.riramzy.littlelemon.data.model.toEntity
import com.riramzy.littlelemon.data.remote.MenuApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MenuRepo @Inject constructor(
    private val menuDao: LocalMenuDao,
    private val apiService: MenuApiService
) {
    fun getLocalMenu(): Flow<List<LocalMenuItem>> {
        return menuDao.getAllLocalMenuItems()
    }

    suspend fun getItemById(itemId: Int): LocalMenuItem? {
        return menuDao.getItemById(itemId)
    }

    suspend fun refreshMenuIfNeeded() {
        val localCount = menuDao.getLocalMenuCount()
        if (localCount == 0) {
            refreshMenu()
        }
    }

    suspend fun refreshMenu() = kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
        try {
            val networkMenu = apiService.fetchMenu()
            networkMenu.menu.let { items ->
                val entities =
                    kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.Default) {
                        items.map { it.toEntity() }
                    }
                menuDao.refreshMenu(entities)
                Log.d("MenuRepo", "Inserted ${entities.size} items into the database")
            }
        } catch (e: Exception) {
            Log.e("MenuRepo", "Error refreshing menu", e)
        }
    }
}