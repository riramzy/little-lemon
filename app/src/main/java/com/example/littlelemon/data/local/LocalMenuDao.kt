package com.example.littlelemon.data.local

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

    @Insert(onConflict = REPLACE)
    suspend fun insertIntoLocalMenuItems(items: List<LocalMenuItem>)

    @Query("DELETE FROM menu_items")
    suspend fun deleteLocalMenuItems()
}