package com.example.littlelemon.ui.screens.reservation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.littlelemon.data.repos.MenuRepo
import java.time.LocalDate
import java.time.LocalTime

class ReservationVm(): ViewModel() {
    var selectedDate = mutableStateOf<LocalDate?>(null)
    var selectedTime = mutableStateOf<LocalTime?>(null)
    var selectedNumberOfDiners = mutableStateOf<Int?>(null)
    var selectedDuration = mutableStateOf<String?>(null)
}