package com.example.littlelemon.data.local.orders

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalOrdersDao {
    // 1️⃣ Get all orders as Flow
    @Query("SELECT * FROM orders")
    fun getAllLocalOrders(): Flow<List<LocalOrder>>

    // 2️⃣ Get count of orders
    @Query("SELECT COUNT(*) FROM orders")
    suspend fun getLocalOrdersCount(): Int

    // 3️⃣ Insert a new order and return its generated ID
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(item: LocalOrder): Long

    // 4️⃣ Insert order items
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderWithItems(items: List<LocalOrderItem>)

    // 5️⃣ Get a single order with its items
    @Transaction
    @Query("SELECT * FROM orders WHERE id = :orderId")
    suspend fun getOrderWithItems(orderId: Int): LocalOrderWithItems

    // 6️⃣ Get all orders with their items
    @Transaction
    @Query("SELECT * FROM orders")
    fun getAllOrdersWithItems(): Flow<List<LocalOrderWithItems>>

    // 7️⃣ Clear all orders (and cascade deletes order items)
    @Query("DELETE FROM orders")
    suspend fun clearOrders()
}
