package com.riramzy.littlelemon.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.riramzy.littlelemon.data.local.cart.LocalCartDao
import com.riramzy.littlelemon.data.local.cart.LocalCartItem
import com.riramzy.littlelemon.data.local.menu.LocalMenuDao
import com.riramzy.littlelemon.data.local.menu.LocalMenuItem
import com.riramzy.littlelemon.data.local.orders.LocalOrder
import com.riramzy.littlelemon.data.local.orders.LocalOrderItem
import com.riramzy.littlelemon.data.local.orders.LocalOrdersDao
import com.riramzy.littlelemon.data.local.reservations.LocalReservation
import com.riramzy.littlelemon.data.local.reservations.LocalReservationsDao
import com.riramzy.littlelemon.data.local.search.LocalSearchDao

@Database(
    entities = [
        LocalMenuItem::class,
        LocalCartItem::class,
        LocalOrder::class,
        LocalOrderItem::class,
        LocalReservation::class,
    ],
    version = 22,
    exportSchema = false
)
abstract class LocalDatabase: RoomDatabase() {
    abstract fun localMenuDao(): LocalMenuDao
    abstract fun localCartDao(): LocalCartDao
    abstract fun localSearchDao(): LocalSearchDao
    abstract fun localOrdersDao(): LocalOrdersDao
    abstract fun localReservationsDao(): LocalReservationsDao
}