package com.riramzy.littlelemon.data.local.menu

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "menu_items",
    indices = [
        Index(value = ["title"]),
        Index(value = ["category"])
    ]
)
data class LocalMenuItem(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "title", collate = ColumnInfo.NOCASE)
    val title: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "price")
    val price: Double,
    @ColumnInfo(name = "image")
    val image: String,
    @ColumnInfo(name = "category", collate = ColumnInfo.NOCASE)
    val category: String
)