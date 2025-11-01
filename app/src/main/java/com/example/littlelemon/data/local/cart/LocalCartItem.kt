package com.example.littlelemon.data.local.cart

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class LocalCartItem(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "price")
    val price: Double,
    @ColumnInfo(name = "image")
    val image: String,
    @ColumnInfo(name = "quantity")
    val quantity: Int = 0
)