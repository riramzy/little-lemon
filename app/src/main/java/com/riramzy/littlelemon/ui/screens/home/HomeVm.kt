package com.riramzy.littlelemon.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riramzy.littlelemon.data.local.menu.LocalMenuItem
import com.riramzy.littlelemon.data.repos.MenuRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeVm @Inject constructor(
    private val menuRepo: MenuRepo
): ViewModel() {
    val menuItems: StateFlow<List<LocalMenuItem>> = menuRepo.getLocalMenu()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    init {
        viewModelScope.launch {
            try {
                menuRepo.refreshMenuIfNeeded()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}