package com.riramzy.littlelemon.ui.screens.reservation

import java.time.LocalDate
import java.time.LocalTime

data class ReservationFormState(
    val selectedDate: LocalDate? = null,
    val selectedTime: LocalTime? = null,
    val selectedNumberOfDiners: Int? = null,
    val selectedDuration: String? = null
)