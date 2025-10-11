package com.example.littlelemon.ui.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.littlelemon.data.local.LocalMenuItem
import com.example.littlelemon.data.repos.MenuRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class DetailsVm(
    private val repo: MenuRepo
): ViewModel() {
    private val _menuItem = MutableStateFlow<LocalMenuItem?>(null)
    val menuItem: StateFlow<LocalMenuItem?> = _menuItem

    fun loadItem(itemId: Int) {
        viewModelScope.launch {
            val item = repo.getItemById(itemId)
            _menuItem.value = item
        }
    }
}