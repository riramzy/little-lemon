package com.riramzy.littlelemon.data.local.reservations

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalReservationsDao {
    @Query("SELECT * FROM reservations")
    fun getAllLocalReservations(): Flow<List<LocalReservation>>

    @Query("SELECT * FROM reservations WHERE id = :id")
    fun getReservationById(id: Int): Flow<LocalReservation>

    @Query("SELECT COUNT(*) FROM reservations")
    suspend fun getLocalReservationsCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReservation(item: LocalReservation)

    @Query("DELETE FROM reservations")
    suspend fun clearReservations()
}