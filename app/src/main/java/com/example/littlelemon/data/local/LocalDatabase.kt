package com.example.littlelemon.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.littlelemon.data.local.cart.LocalCartDao
import com.example.littlelemon.data.local.cart.LocalCartItem
import com.example.littlelemon.data.local.menu.LocalMenuDao
import com.example.littlelemon.data.local.menu.LocalMenuItem
import com.example.littlelemon.data.local.orders.LocalOrder
import com.example.littlelemon.data.local.orders.LocalOrderItem
import com.example.littlelemon.data.local.orders.LocalOrdersDao
import com.example.littlelemon.data.local.reservations.LocalReservation
import com.example.littlelemon.data.local.reservations.LocalReservationsDao
import com.example.littlelemon.data.local.search.LocalSearchDao

//Room database instance that builds the database
@Database(
    entities = [
        LocalMenuItem::class,
        LocalCartItem::class,
        LocalOrder::class,
        LocalOrderItem::class,
        LocalReservation::class,
    ],
    version = 20
)
abstract class LocalDatabase: RoomDatabase() {
    abstract fun localMenuDao(): LocalMenuDao
    abstract fun localCartDao(): LocalCartDao
    abstract fun localSearchDao(): LocalSearchDao
    abstract fun localOrdersDao(): LocalOrdersDao
    abstract fun localReservationsDao(): LocalReservationsDao
}