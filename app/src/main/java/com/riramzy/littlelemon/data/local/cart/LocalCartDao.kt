package com.riramzy.littlelemon.data.local.cart

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalCartDao {
    @Query("SELECT * FROM cart_items")
    fun getAllLocalCartItems(): Flow<List<LocalCartItem>>

    @Query("UPDATE cart_items SET quantity = :quantity WHERE id = :itemId")
    suspend fun updateQuantity(itemId: Int, quantity: Int)

    @Query("SELECT * FROM cart_items WHERE id = :itemId")
    suspend fun getItemById(itemId: Int): LocalCartItem?

    @Insert(onConflict = REPLACE)
    suspend fun insertIntoLocalCartItem(item: LocalCartItem)

    @Query("DELETE FROM cart_items WHERE id = :itemId")
    suspend fun deleteLocalCartItem(itemId: Int)

    @Query("SELECT COUNT(*) FROM cart_items")
    suspend fun getLocalCartCount(): Int

    @Query("DELETE FROM cart_items")
    suspend fun clearCart()
}
