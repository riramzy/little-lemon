package com.example.littlelemon.data.local.menu

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

//Room database DAO interface that defines the database operations

@Dao
interface LocalMenuDao {
    @Query("SELECT * FROM menu_items")
    fun getAllLocalMenuItems(): Flow<List<LocalMenuItem>>

    @Query("SELECT * FROM menu_items WHERE id = :itemId")
    suspend fun getItemById(itemId: Int): LocalMenuItem

    @Insert(onConflict = REPLACE)
    suspend fun insertIntoLocalMenuItems(items: List<LocalMenuItem>)

    @Query("DELETE FROM menu_items")
    suspend fun deleteLocalMenuItems()

    @Query("SELECT COUNT(*) FROM menu_items")
    suspend fun getLocalMenuCount(): Int
}