package com.example.littlelemon.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.littlelemon.data.local.LocalMenuItem
import com.example.littlelemon.data.repos.MenuRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeVm(
    private val repo: MenuRepo
): ViewModel() {
    private val _menuItems = MutableStateFlow<List<LocalMenuItem>>(emptyList())
    val menuItems: StateFlow<List<LocalMenuItem>> = _menuItems

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repo.refreshMenuIfNeeded()
            repo.getLocalMenu().collect { items ->
                _menuItems.value = items
            }
        }
    }
}