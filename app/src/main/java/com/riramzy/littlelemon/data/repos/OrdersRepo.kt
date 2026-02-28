package com.riramzy.littlelemon.data.repos

import com.riramzy.littlelemon.data.local.orders.LocalOrder
import com.riramzy.littlelemon.data.local.orders.LocalOrderItem
import com.riramzy.littlelemon.data.local.orders.LocalOrderWithItems
import com.riramzy.littlelemon.data.local.orders.LocalOrdersDao
import kotlinx.coroutines.flow.Flow

class OrdersRepo(private val dao: LocalOrdersDao) {
    // 1️⃣ Get all orders as Flow
    fun getLocalOrders(): Flow<List<LocalOrder>> {
        return dao.getAllLocalOrders()
    }

    // 2️⃣ Get all orders with their items
    fun getAllOrdersWithItems(): Flow<List<LocalOrderWithItems>> {
        return dao.getAllOrdersWithItems()
    }

    // 3️⃣ Get count of orders
    suspend fun getLocalOrdersCount(): Int {
        return dao.getLocalOrdersCount()
    }

    // 4️⃣ Get a single order with its items
    suspend fun getOrderWithItems(orderId: Int): LocalOrderWithItems {
        return dao.getOrderWithItems(orderId)
    }

    // 5️⃣ Insert a new order and return its ID
    suspend fun insertOrder(item: LocalOrder): Long {
        return dao.insertOrder(item)
    }

    // 6️⃣ Insert multiple order items
    suspend fun insertOrderWithItems(items: List<LocalOrderItem>) {
        dao.insertOrderWithItems(items)
    }

    // 7️⃣ Clear all orders
    suspend fun clearOrders() {
        dao.clearOrders()
    }
}
