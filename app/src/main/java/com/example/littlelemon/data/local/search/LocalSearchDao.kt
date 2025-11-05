package com.example.littlelemon.data.local.search

import android.view.MenuItem
import androidx.room.Dao
import androidx.room.Query
import com.example.littlelemon.data.local.menu.LocalMenuItem
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalSearchDao {
    @Query("SELECT * FROM menu_items WHERE title LIKE '%' || :name || '%'")
    fun searchMenuItemsByName(name: String): Flow<List<LocalMenuItem>>

    @Query("SELECT * FROM menu_items")
    fun getAllMenuItems(): Flow<List<LocalMenuItem>>
}
