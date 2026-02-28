package com.riramzy.littlelemon.data.local.reservations

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalReservationsDao {
    //Get all reservations as Flow
    @Query("SELECT * FROM reservations")
    fun getAllLocalReservations(): Flow<List<LocalReservation>>

    //Get reservation by id as Flow
    @Query("SELECT * FROM reservations WHERE id = :id")
    fun getReservationById(id: Int): Flow<LocalReservation>

    //Get count of reservations
    @Query("SELECT COUNT(*) FROM reservations")
    suspend fun getLocalReservationsCount(): Int

    //Insert a new reservation and return its generated ID
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertReservation(item: LocalReservation)

    //Clear all reservations (and cascade deletes order items)
    @Query("DELETE FROM reservations")
    suspend fun clearReservations()
}