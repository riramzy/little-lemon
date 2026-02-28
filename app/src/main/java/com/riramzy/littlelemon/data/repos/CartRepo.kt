package com.riramzy.littlelemon.data.repos

import com.riramzy.littlelemon.data.local.cart.LocalCartDao
import com.riramzy.littlelemon.data.local.cart.LocalCartItem
import kotlinx.coroutines.flow.Flow

class CartRepo(private val dao: LocalCartDao) {
    suspend fun getLocalCartItems(): Flow<List<LocalCartItem>> {
        return dao.getAllLocalCartItems()
    }

    fun updateQuantity(itemId: Int, quantity: Int) {
        dao.updateQuantity(itemId, quantity)
    }

    suspend fun getCartItemById(itemId: Int): LocalCartItem {
        return dao.getItemById(itemId)
    }

    suspend fun addToCart(item: LocalCartItem) {
        dao.insertIntoLocalCartItem(item)
    }

    suspend fun removeFromCart(itemId: Int) {
        dao.deleteLocalCartItem(itemId)
    }

    suspend fun getLocalCartCount(): Int {
        return dao.getLocalCartCount()
    }

    suspend fun clearCart() {
        dao.clearCart()
    }
}
