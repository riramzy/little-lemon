package com.riramzy.littlelemon.data.local.search

import androidx.room.Dao
import androidx.room.Query
import com.riramzy.littlelemon.data.local.menu.LocalMenuItem
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalSearchDao {
    @Query("SELECT * FROM menu_items WHERE title LIKE '%' || :name || '%'")
    fun searchMenuItemsByName(name: String): Flow<List<LocalMenuItem>>

    @Query("SELECT * FROM menu_items")
    fun getAllMenuItems(): Flow<List<LocalMenuItem>>

    @Query("SELECT * FROM menu_items WHERE category = :category")
    fun searchMenuItemsByCategory(category: String): Flow<List<LocalMenuItem>>

    @Query(
        """
        SELECT * FROM menu_items 
        WHERE (:category IS NULL OR :category = '' OR category = :category)
          AND (:name IS NULL OR :name = '' OR title LIKE '%' || :name || '%')
    """
    )
    fun getFilteredMenuItems(name: String?, category: String?): Flow<List<LocalMenuItem>>
}
