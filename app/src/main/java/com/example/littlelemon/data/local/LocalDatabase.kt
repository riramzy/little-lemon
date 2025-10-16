package com.example.littlelemon.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.littlelemon.data.local.cart.LocalCartDao
import com.example.littlelemon.data.local.cart.LocalCartItem
import com.example.littlelemon.data.local.menu.LocalMenuDao
import com.example.littlelemon.data.local.menu.LocalMenuItem

//Room database instance that builds the database

@Database(
    entities = [
        LocalMenuItem::class,
        LocalCartItem::class
    ],
    version = 3
)
abstract class LocalDatabase: RoomDatabase() {
    abstract fun localMenuDao(): LocalMenuDao
    abstract fun localCartDao(): LocalCartDao

    companion object {
        @Volatile private var INSTANCE: LocalDatabase? = null

        fun getInstance(context: Context): LocalDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LocalDatabase::class.java,
                    "little_lemon_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}