package com.example.littlelemon.ui.screens.payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.littlelemon.ui.screens.reservation.ReservationVm
import com.example.littlelemon.ui.viewmodel.UserVm

class PaymentVmFactory(
    private val userVm: UserVm,
    private val reservationVm: ReservationVm
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PaymentVm::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PaymentVm(userVm, reservationVm) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
