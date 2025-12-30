package com.example.littlelemon.data.repos

import com.example.littlelemon.data.local.reservations.LocalReservation
import com.example.littlelemon.data.local.reservations.LocalReservationsDao
import kotlinx.coroutines.flow.Flow

class ReservationsRepo(private val dao: LocalReservationsDao) {
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