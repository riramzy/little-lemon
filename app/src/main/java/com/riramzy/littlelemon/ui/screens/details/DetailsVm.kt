package com.riramzy.littlelemon.ui.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riramzy.littlelemon.data.local.menu.LocalMenuItem
import com.riramzy.littlelemon.data.repos.MenuRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsVm @Inject constructor(
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