package com.riramzy.littlelemon.ui.screens.reservation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riramzy.littlelemon.data.local.reservations.LocalReservation
import com.riramzy.littlelemon.data.repos.ReservationsRepo
import com.riramzy.littlelemon.ui.viewmodel.UserVm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class ReservationVm @Inject constructor(
    private val reservationsRepo: ReservationsRepo,
    private val userVm: UserVm
): ViewModel() {
    var selectedDate = mutableStateOf<LocalDate?>(null)
    var selectedTime = mutableStateOf<LocalTime?>(null)
    var selectedNumberOfDiners = mutableStateOf<Int?>(null)
    var selectedDuration = mutableStateOf<String?>(null)
    var selectedPaymentMethod: StateFlow<String?> = reservationsRepo.paymentMethod
    val phoneNumber: StateFlow<String?> = reservationsRepo.phoneNumber
    val orderId: StateFlow<String?> = reservationsRepo.orderId

    //User name for reservation
    private val _firstName = MutableStateFlow(if (userVm.isLoggedIn()) userVm.getFirstName() ?: "" else "")
    val firstName = _firstName.asStateFlow()

    private val _lastName = MutableStateFlow(if (userVm.isLoggedIn()) userVm.getLastName() ?: "" else "")
    val lastName = _lastName.asStateFlow()

    //StateFlow of reservations WITH items
    private val _reservations = MutableStateFlow<List<LocalReservation>>(emptyList())
    val reservations: StateFlow<List<LocalReservation>> = _reservations

    init {
        //Collect reservations with items from repository
        viewModelScope.launch(Dispatchers.IO) {
            reservationsRepo.getAllLocalReservations().collect { list ->
                _reservations.value = list
            }
        }
    }

    //Get reservation by id as Flow
    fun getReservationById(id: Int): Flow<LocalReservation> {
        return reservationsRepo.getReservationById(id)
    }

    //Get reservations count
    suspend fun getLocalReservationsCount(): Int {
        return reservationsRepo.getLocalReservationsCount()
    }

    //Insert a new reservation and return its generated ID
    fun insertReservation(item: LocalReservation) {
        viewModelScope.launch(Dispatchers.IO) {
            reservationsRepo.insertReservation(item)
        }
    }

    fun setPaymentMethod(method: String) {
        reservationsRepo.setPaymentMethod(method)
    }

    //Clear all reservations
    fun clearReservations() {
        viewModelScope.launch(Dispatchers.IO) {
            reservationsRepo.clearReservations()
        }
    }
}