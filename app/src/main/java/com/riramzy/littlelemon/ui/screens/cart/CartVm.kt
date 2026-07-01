package com.riramzy.littlelemon.ui.screens.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riramzy.littlelemon.data.local.cart.LocalCartItem
import com.riramzy.littlelemon.data.repos.CartRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartVm @Inject constructor(
    private val cartRepo: CartRepo
): ViewModel() {
    val cartItems: StateFlow<List<LocalCartItem>> = cartRepo.getLocalCartItems()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun increaseQuantity(itemId: Int) {
        viewModelScope.launch {
            val item = cartRepo.getCartItemById(itemId)
            if (item != null) {
                cartRepo.updateQuantity(itemId, item.quantity + 1)
            }
        }
    }

    fun decreaseQuantity(itemId: Int) {
        viewModelScope.launch {
            val item = cartRepo.getCartItemById(itemId)
            if (item != null) {
                if (item.quantity > 1) {
                    cartRepo.updateQuantity(itemId, item.quantity - 1)
                } else {
                    cartRepo.removeFromCart(itemId)
                }
            }
        }
    }

    fun addToCart(item: LocalCartItem) {
        viewModelScope.launch {
            cartRepo.addToCart(item)
        }
    }

    fun removeFromCart(itemId: Int) {
        viewModelScope.launch {
            cartRepo.removeFromCart(itemId)
        }
    }

    suspend fun getLocalCartCount(): Int {
        return cartRepo.getLocalCartCount()
    }

    suspend fun getCartItemById(itemId: Int): LocalCartItem? {
        return cartRepo.getCartItemById(itemId)
    }

    fun updateQuantity(itemId: Int, newQuantity: Int) {
        viewModelScope.launch {
            cartRepo.updateQuantity(itemId, newQuantity)
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            cartRepo.clearCart()
        }
    }
}
