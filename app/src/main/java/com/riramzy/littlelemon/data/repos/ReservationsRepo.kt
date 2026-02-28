package com.riramzy.littlelemon.data.repos

import com.riramzy.littlelemon.data.local.reservations.LocalReservation
import com.riramzy.littlelemon.data.local.reservations.LocalReservationsDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ReservationsRepo(private val dao: LocalReservationsDao) {
    private val _paymentMethod = MutableStateFlow<String?>(null)
    val paymentMethod: StateFlow<String?> = _paymentMethod

    private val _orderId = MutableStateFlow<String?>(null)
    val orderId: StateFlow<String?> = _orderId

    private val _phoneNumber = MutableStateFlow<String?>(null)
    val phoneNumber: StateFlow<String?> = _phoneNumber

    fun setPaymentMethod(method: String) {
        _paymentMethod.value = method
    }

    fun setOrderId(id: String) {
        _orderId.value = id
    }

    fun setPhoneNumber(phone: String) {
        _phoneNumber.value = phone
    }

    //Get all reservations as Flow
    fun getAllLocalReservations(): Flow<List<LocalReservation>> {
        return dao.getAllLocalReservations()
    }

    //Get reservation by id as Flow
    fun getReservationById(id: Int): Flow<LocalReservation> {
        return dao.getReservationById(id)
    }

    //Get count of reservations
    suspend fun getLocalReservationsCount(): Int {
        return dao.getLocalReservationsCount()
    }

    //Insert a new reservation and return its generated ID
    fun insertReservation(item: LocalReservation) {
        dao.insertReservation(item)
    }

    //Clear all reservations
    suspend fun clearReservations() {
        dao.clearReservations()
    }
}