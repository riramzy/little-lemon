package com.example.littlelemon.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.littlelemon.data.repos.MenuRepo

class HomeVmFactory(
    private val repo: MenuRepo
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeVm::class.java)) {
            return HomeVm(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}