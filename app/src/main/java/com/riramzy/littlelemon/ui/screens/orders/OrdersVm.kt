package com.riramzy.littlelemon.ui.screens.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riramzy.littlelemon.data.local.cart.LocalCartItem
import com.riramzy.littlelemon.data.local.orders.LocalOrder
import com.riramzy.littlelemon.data.local.orders.LocalOrderItem
import com.riramzy.littlelemon.data.local.orders.LocalOrderWithItems
import com.riramzy.littlelemon.data.repos.OrdersRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersVm @Inject constructor(
    private val ordersRepo: OrdersRepo,
) : ViewModel() {
    val ordersWithItems: StateFlow<List<LocalOrderWithItems>> = ordersRepo.getAllOrdersWithItems()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    suspend fun getLocalOrdersCount(): Int {
        return ordersRepo.getLocalOrdersCount()
    }

    suspend fun getOrderWithItems(orderId: Int): LocalOrderWithItems? {
        return ordersRepo.getOrderWithItems(orderId)
    }

    fun placeOrder(cartItems: List<LocalCartItem>, paymentMethod: String) {
        viewModelScope.launch {
            try {
                val totalPrice = cartItems.sumOf { it.price * it.quantity } + 5
                val order = LocalOrder(
                    totalPrice = totalPrice,
                    paymentMethod = paymentMethod
                )
                val orderItems = cartItems.map { cartItem ->
                    LocalOrderItem(
                        orderId = 0, // Placeholder, mapped inside transaction
                        dishId = cartItem.id,
                        title = cartItem.title,
                        price = cartItem.price,
                        image = cartItem.image,
                        quantity = cartItem.quantity
                    )
                }
                ordersRepo.placeOrderTransaction(order, orderItems)
            } catch (e: Exception) {
                android.util.Log.e("OrdersVm", "Failed to place order transaction", e)
            }
        }
    }

    fun clearOrders() {
        viewModelScope.launch {
            try {
                ordersRepo.clearOrders()
            } catch (e: Exception) {
                android.util.Log.e("OrdersVm", "Failed to clear orders", e)
            }
        }
    }
}
