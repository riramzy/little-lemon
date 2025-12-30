package com.example.littlelemon.data.local.reservations

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reservations")
data class LocalReservation(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name_of_reserver") val nameOfReserver: String = "John Doe",
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "time") val time: String,
    @ColumnInfo(name = "duration") val duration: String,
    @ColumnInfo(name = "number_of_diners") val numberOfDiners: String,
    @ColumnInfo(name = "created_at") val createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "total_price") val totalPrice: Double = 50.00,
    @ColumnInfo(name = "payment_method") val paymentMethod: String = "Mastercard",
)