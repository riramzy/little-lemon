package com.example.littlelemon.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.littlelemon.di.AppContainer
import com.example.littlelemon.ui.screens.home.HomeVm

class SearchVmFactory(
    private val appContainer: AppContainer
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchVm::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchVm(appContainer.searchRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}