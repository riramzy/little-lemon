package com.example.littlelemon.ui.screens.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.littlelemon.data.local.cart.LocalCartItem
import com.example.littlelemon.data.local.orders.LocalOrder
import com.example.littlelemon.data.local.orders.LocalOrderItem
import com.example.littlelemon.data.local.orders.LocalOrderWithItems
import com.example.littlelemon.data.repos.OrdersRepo
import com.example.littlelemon.ui.screens.reservation.ReservationVm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OrdersVm(
    private val ordersRepo: OrdersRepo,
    private val reservationVm: ReservationVm
) : ViewModel() {

    // 1️⃣ StateFlow of orders WITH items
    private val _ordersWithItems = MutableStateFlow<List<LocalOrderWithItems>>(emptyList())
    val ordersWithItems: StateFlow<List<LocalOrderWithItems>> = _ordersWithItems

    init {
        // 2️⃣ Collect orders with items from repository
        viewModelScope.launch(Dispatchers.IO) {
            ordersRepo.getAllOrdersWithItems().collect { list ->
                _ordersWithItems.value = list
            }
        }
    }

    // 3️⃣ Get order count
    suspend fun getLocalOrdersCount(): Int {
        return ordersRepo.getLocalOrdersCount()
    }

    // 4️⃣ Get a single order with items
    suspend fun getOrderWithItems(orderId: Int): LocalOrderWithItems {
        return ordersRepo.getOrderWithItems(orderId)
    }

    // 5️⃣ Place a new order (cart -> order + items)
    fun placeOrder(cartItems: List<LocalCartItem>) {
        viewModelScope.launch(Dispatchers.IO) {

            // Calculate total price
            val totalPrice = cartItems.sumOf { it.price * it.quantity } + 5
            val paymentMethod = reservationVm.selectedPaymentMethod.value

            // Insert new order and get generated ID
            val orderId = ordersRepo.insertOrder(LocalOrder(totalPrice = totalPrice, paymentMethod = paymentMethod.toString())).toInt()

            // Convert cart items to order items
            val orderItems = cartItems.map { cartItem ->
                LocalOrderItem(
                    orderId = orderId,
                    dishId = cartItem.id,
                    title = cartItem.title,
                    price = cartItem.price,
                    image = cartItem.image,
                    quantity = cartItem.quantity
                )
            }

            // Insert all order items
            ordersRepo.insertOrderWithItems(orderItems)
        }
    }

    // 6️⃣ Clear all orders
    fun clearOrders() {
        viewModelScope.launch(Dispatchers.IO) {
            ordersRepo.clearOrders()
        }
    }
}
