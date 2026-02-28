package com.riramzy.littlelemon.di

import android.content.Context
import androidx.room.Room
import com.riramzy.littlelemon.data.local.LocalDatabase
import com.riramzy.littlelemon.data.local.cart.LocalCartDao
import com.riramzy.littlelemon.data.local.menu.LocalMenuDao
import com.riramzy.littlelemon.data.local.orders.LocalOrdersDao
import com.riramzy.littlelemon.data.local.reservations.LocalReservationsDao
import com.riramzy.littlelemon.data.local.search.LocalSearchDao
import com.riramzy.littlelemon.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): LocalDatabase {
        return Room.databaseBuilder(
            context,
            LocalDatabase::class.java,
            Constants.DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideMenuDao(database: LocalDatabase): LocalMenuDao = database.localMenuDao()

    @Singleton
    @Provides
    fun provideCartDao(database: LocalDatabase): LocalCartDao = database.localCartDao()

    @Singleton
    @Provides
    fun provideOrdersDao(database: LocalDatabase): LocalOrdersDao = database.localOrdersDao()

    @Singleton
    @Provides
    fun provideSearchDao(database: LocalDatabase): LocalSearchDao = database.localSearchDao()

    @Singleton
    @Provides
    fun provideReservationDao(database: LocalDatabase): LocalReservationsDao = database.localReservationsDao()
}