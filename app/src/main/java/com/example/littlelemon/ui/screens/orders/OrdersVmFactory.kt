package com.example.littlelemon.ui.screens.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.littlelemon.di.AppContainer
import com.example.littlelemon.ui.screens.cart.CartVm

class OrdersVmFactory(
    private val appContainer: AppContainer
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrdersVm::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OrdersVm(appContainer.ordersRepo, appContainer.reservationVm) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}