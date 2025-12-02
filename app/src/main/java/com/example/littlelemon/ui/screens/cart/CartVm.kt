package com.example.littlelemon.ui.screens.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.littlelemon.data.local.cart.LocalCartItem
import com.example.littlelemon.data.repos.CartRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartVm(
    private val cartRepo: CartRepo
): ViewModel() {
    private val _cartItems = MutableStateFlow<List<LocalCartItem>>(emptyList())
    val cartItems: StateFlow<List<LocalCartItem>> = _cartItems

    init {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepo.getLocalCartItems().collect { items ->
                _cartItems.value = items
            }
        }
    }

    fun increaseQuantity(itemId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val item = cartRepo.getCartItemById(itemId)
            cartRepo.updateQuantity(itemId, item.quantity + 1)
        }
    }

    fun decreaseQuantity(itemId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val item = cartRepo.getCartItemById(itemId)
            if (item.quantity > 1) {
                cartRepo.updateQuantity(itemId, item.quantity - 1)
            } else {
                cartRepo.removeFromCart(itemId)
            }
        }
    }

    fun addToCart(item: LocalCartItem) {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepo.addToCart(item)
        }
    }

    fun removeFromCart(itemId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepo.removeFromCart(itemId)
        }
    }

    suspend fun getLocalCartCount(): Int {
        return cartRepo.getLocalCartCount()
    }

    suspend fun getCartItemById(itemId: Int): LocalCartItem {
        return cartRepo.getCartItemById(itemId)
    }

    fun updateQuantity(itemId: Int, newQuantity: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepo.updateQuantity(itemId, newQuantity)
        }
    }

    fun clearCart() {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepo.clearCart()
        }
    }
}
