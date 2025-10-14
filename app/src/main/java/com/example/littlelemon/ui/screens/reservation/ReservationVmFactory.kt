package com.example.littlelemon.ui.screens.reservation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.littlelemon.di.AppContainer

class ReservationVmFactory(): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReservationVm::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ReservationVm() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}