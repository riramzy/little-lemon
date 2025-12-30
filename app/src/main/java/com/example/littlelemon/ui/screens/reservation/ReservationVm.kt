package com.example.littlelemon.ui.screens.reservation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.littlelemon.data.local.reservations.LocalReservation
import com.example.littlelemon.data.repos.ReservationsRepo
import com.example.littlelemon.ui.viewmodel.UserVm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import java.time.LocalDate
import java.time.LocalTime

class ReservationVm(
    private val reservationsRepo: ReservationsRepo,
    private val userVm: UserVm
): ViewModel() {
    var selectedDate = mutableStateOf<LocalDate?>(null)
    var selectedTime = mutableStateOf<LocalTime?>(null)
    var selectedNumberOfDiners = mutableStateOf<Int?>(null)
    var selectedDuration = mutableStateOf<String?>(null)
    var selectedPaymentMethod = mutableStateOf<String?>(null)
    var phoneNumber = mutableStateOf<String?>(null)
    var orderId = mutableStateOf<String?>(null)

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

    //Clear all reservations
    fun clearReservations() {
        viewModelScope.launch(Dispatchers.IO) {
            reservationsRepo.clearReservations()
        }
    }
}