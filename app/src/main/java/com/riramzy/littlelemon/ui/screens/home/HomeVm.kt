package com.riramzy.littlelemon.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riramzy.littlelemon.data.local.menu.LocalMenuItem
import com.riramzy.littlelemon.data.repos.MenuRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeVm @Inject constructor(
    private val menuRepo: MenuRepo
): ViewModel() {
    private val _menuItems = MutableStateFlow<List<LocalMenuItem>>(emptyList())
    val menuItems: StateFlow<List<LocalMenuItem>> = _menuItems

    init {
        viewModelScope.launch(Dispatchers.IO) {
            menuRepo.refreshMenuIfNeeded()
            menuRepo.getLocalMenu().collect { items ->
                _menuItems.value = items
            }
        }
    }
}