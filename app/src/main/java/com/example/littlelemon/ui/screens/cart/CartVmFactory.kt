package com.example.littlelemon.ui.screens.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.littlelemon.di.AppContainer
import com.example.littlelemon.ui.screens.home.HomeVm

class CartVmFactory(
    private val appContainer: AppContainer
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartVm::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CartVm(appContainer.cartRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}