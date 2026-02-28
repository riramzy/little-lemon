package com.riramzy.littlelemon.data.local.menu

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//Room database entity represent a table in the database

@Entity(tableName = "menu_items")
data class LocalMenuItem(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "price")
    val price: Double,
    @ColumnInfo(name = "image")
    val image: String,
    @ColumnInfo(name = "category")
    val category: String
)