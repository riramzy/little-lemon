package com.riramzy.littlelemon.di

import com.riramzy.littlelemon.data.local.cart.LocalCartDao
import com.riramzy.littlelemon.data.local.menu.LocalMenuDao
import com.riramzy.littlelemon.data.local.orders.LocalOrdersDao
import com.riramzy.littlelemon.data.local.reservations.LocalReservationsDao
import com.riramzy.littlelemon.data.local.search.LocalSearchDao
import com.riramzy.littlelemon.data.preferences.UserPreferences
import com.riramzy.littlelemon.data.repos.CartRepo
import com.riramzy.littlelemon.data.repos.MenuRepo
import com.riramzy.littlelemon.data.repos.OrdersRepo
import com.riramzy.littlelemon.data.repos.ReservationsRepo
import com.riramzy.littlelemon.data.repos.SearchRepo
import com.riramzy.littlelemon.data.repos.UserRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ReposModule {
    @Singleton
    @Provides
    fun provideMenuRepo(menuDao: LocalMenuDao): MenuRepo {
        return MenuRepo(menuDao)
    }

    @Singleton
    @Provides
    fun provideOrdersRepo(ordersDao: LocalOrdersDao): OrdersRepo {
        return OrdersRepo(ordersDao)
    }

    @Singleton
    @Provides
    fun provideCartRepo(cartDao: LocalCartDao): CartRepo {
        return CartRepo(cartDao)
    }

    @Singleton
    @Provides
    fun provideSearchRepo(searchDao: LocalSearchDao): SearchRepo {
        return SearchRepo(searchDao)
    }

    @Singleton
    @Provides
    fun provideReservationRepo(reservationDao: LocalReservationsDao): ReservationsRepo {
        return ReservationsRepo(reservationDao)
    }

    @Singleton
    @Provides
    fun provideUserRepo(prefs: UserPreferences): UserRepo {
        return UserRepo(prefs)
    }
}