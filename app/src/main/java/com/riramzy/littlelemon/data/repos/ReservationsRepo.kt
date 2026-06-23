package com.riramzy.littlelemon.data.repos

import com.riramzy.littlelemon.data.local.reservations.LocalReservation
import com.riramzy.littlelemon.data.local.reservations.LocalReservationsDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReservationsRepo @Inject constructor(
    private val dao: LocalReservationsDao
) {
    fun getAllLocalReservations(): Flow<List<LocalReservation>> {
        return dao.getAllLocalReservations()
    }

    fun getReservationById(id: Int): Flow<LocalReservation> {
        return dao.getReservationById(id)
    }

    suspend fun getLocalReservationsCount(): Int {
        return dao.getLocalReservationsCount()
    }

    suspend fun insertReservation(item: LocalReservation) {
        dao.insertReservation(item)
    }

    suspend fun clearReservations() {
        dao.clearReservations()
    }
}