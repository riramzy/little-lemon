package com.example.littlelemon.ui.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.littlelemon.di.AppContainer

class DetailsVmFactory(
    private val appContainer: AppContainer
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsVm::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailsVm(appContainer.menuRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
