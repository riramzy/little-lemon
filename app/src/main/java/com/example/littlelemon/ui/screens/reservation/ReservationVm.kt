package com.example.littlelemon.ui.screens.reservation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.time.LocalTime

class ReservationVm(): ViewModel() {
    var selectedDate = mutableStateOf<LocalDate?>(null)
    var selectedTime = mutableStateOf<LocalTime?>(null)
    var selectedNumberOfDiners = mutableStateOf<Int?>(null)
    var selectedDuration = mutableStateOf<String?>(null)
    var selectedPaymentMethod = mutableStateOf<String?>(null)
    var phoneNumber = mutableStateOf<String?>(null)
    var orderId = mutableStateOf<String?>(null)
}