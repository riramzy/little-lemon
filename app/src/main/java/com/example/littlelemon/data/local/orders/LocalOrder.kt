package com.example.littlelemon.data.local.orders

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class LocalOrder(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "created_at") val createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "total_price") val totalPrice: Double,
    @ColumnInfo(name = "payment_method") val paymentMethod: String,
)