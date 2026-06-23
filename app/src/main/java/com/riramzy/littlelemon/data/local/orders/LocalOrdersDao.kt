package com.riramzy.littlelemon.data.local.orders

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalOrdersDao {
    @Query("SELECT * FROM orders")
    fun getAllLocalOrders(): Flow<List<LocalOrder>>

    @Query("SELECT COUNT(*) FROM orders")
    suspend fun getLocalOrdersCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(item: LocalOrder): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderWithItems(items: List<LocalOrderItem>)

    @Transaction
    @Query("SELECT * FROM orders WHERE id = :orderId")
    suspend fun getOrderWithItems(orderId: Int): LocalOrderWithItems

    @Transaction
    @Query("SELECT * FROM orders")
    fun getAllOrdersWithItems(): Flow<List<LocalOrderWithItems>>

    @Query("DELETE FROM orders")
    suspend fun clearOrders()
}
