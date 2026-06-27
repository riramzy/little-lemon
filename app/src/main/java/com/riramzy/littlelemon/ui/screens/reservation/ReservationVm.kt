package com.riramzy.littlelemon.ui.screens.reservation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riramzy.littlelemon.data.local.reservations.LocalReservation
import com.riramzy.littlelemon.data.repos.ReservationsRepo
import com.riramzy.littlelemon.data.repos.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class ReservationVm @Inject constructor(
    private val reservationsRepo: ReservationsRepo,
    private val userRepo: UserRepo,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _reservationFormState = MutableStateFlow(
        ReservationFormState(
            selectedDate = savedStateHandle.get<String>("selected_date")
                ?.let { LocalDate.parse(it) },
            selectedTime = savedStateHandle.get<String>("selected_time")
                ?.let { LocalTime.parse(it) },
            selectedNumberOfDiners = savedStateHandle.get<Int>("selected_number_of_diners"),
            selectedDuration = savedStateHandle.get<String>("selected_duration")
        )
    )

    val reservationFormState = _reservationFormState.asStateFlow()

    private val _selectedPaymentMethod =
        MutableStateFlow(savedStateHandle.get<String>("selected_payment_method"))
    val selectedPaymentMethod: StateFlow<String?> = _selectedPaymentMethod.asStateFlow()

    private val _phoneNumber = MutableStateFlow(savedStateHandle.get<String>("phone_number"))
    val phoneNumber: StateFlow<String?> = _phoneNumber.asStateFlow()

    private val _orderId = MutableStateFlow(savedStateHandle.get<String>("order_id"))
    val orderId: StateFlow<String?> = _orderId.asStateFlow()

    private val _firstName = MutableStateFlow("")
    val firstName = _firstName.asStateFlow()

    private val _lastName = MutableStateFlow("")
    val lastName = _lastName.asStateFlow()

    val reservations: StateFlow<List<LocalReservation>> = reservationsRepo.getAllLocalReservations()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    init {
        viewModelScope.launch {
            if (userRepo.isLoggedIn()) {
                _firstName.value = userRepo.getFirstName() ?: ""
                _lastName.value = userRepo.getLastName() ?: ""
            }
        }
    }

    fun getReservationById(id: Int): Flow<LocalReservation?> {
        return reservationsRepo.getReservationById(id)
    }

    suspend fun getLocalReservationsCount(): Int {
        return reservationsRepo.getLocalReservationsCount()
    }

    fun insertReservation(item: LocalReservation) {
        viewModelScope.launch {
            reservationsRepo.insertReservation(item)
        }
    }

    fun confirmReservation(paymentMethod: String) {
        val form = _reservationFormState.value
        val date = form.selectedDate ?: LocalDate.now()
        val time = form.selectedTime ?: LocalTime.now()
        val duration = form.selectedDuration ?: ""
        val diners = form.selectedNumberOfDiners ?: 1
        val name =
            "${_firstName.value.replaceFirstChar { it.uppercase() }} ${_lastName.value.replaceFirstChar { it.uppercase() }}"

        viewModelScope.launch {
            reservationsRepo.insertReservation(
                LocalReservation(
                    date = date.toString(),
                    time = time.toString(),
                    duration = duration,
                    numberOfDiners = diners.toString(),
                    createdAt = System.currentTimeMillis(),
                    nameOfReserver = name,
                    paymentMethod = paymentMethod,
                    totalPrice = 0.00
                )
            )
        }
    }

    fun setPaymentMethod(method: String) {
        _selectedPaymentMethod.value = method
        savedStateHandle["selected_payment_method"] = method
    }

    fun setPhoneNumber(phone: String) {
        _phoneNumber.value = phone
        savedStateHandle["phone_number"] = phone
    }

    fun setOrderId(id: String) {
        _orderId.value = id
        savedStateHandle["order_id"] = id
    }

    fun updateDate(date: LocalDate) {
        _reservationFormState.value = _reservationFormState.value.copy(selectedDate = date)
        savedStateHandle["selected_date"] = date.toString()
    }

    fun updateTime(time: LocalTime) {
        _reservationFormState.value = _reservationFormState.value.copy(selectedTime = time)
        savedStateHandle["selected_time"] = time.toString()
    }

    fun updateNumberOfDiners(diners: Int) {
        _reservationFormState.value =
            _reservationFormState.value.copy(selectedNumberOfDiners = diners)
        savedStateHandle["selected_number_of_diners"] = diners
    }

    fun updateDuration(duration: String) {
        _reservationFormState.value = _reservationFormState.value.copy(selectedDuration = duration)
        savedStateHandle["selected_duration"] = duration
    }

    fun clearReservations() {
        viewModelScope.launch {
            reservationsRepo.clearReservations()
        }
    }
}