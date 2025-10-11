package com.example.littlelemon.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//Room database instance that builds the database

@Database(
    entities = [LocalMenuItem::class],
    version = 1
)
abstract class LocalDatabase: RoomDatabase() {
    abstract fun localMenuDao(): LocalMenuDao

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