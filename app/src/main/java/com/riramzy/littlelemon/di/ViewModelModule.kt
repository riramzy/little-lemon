package com.riramzy.littlelemon.di

import com.riramzy.littlelemon.data.repos.CartRepo
import com.riramzy.littlelemon.data.repos.MenuRepo
import com.riramzy.littlelemon.data.repos.OrdersRepo
import com.riramzy.littlelemon.data.repos.ReservationsRepo
import com.riramzy.littlelemon.data.repos.SearchRepo
import com.riramzy.littlelemon.data.repos.UserRepo
import com.riramzy.littlelemon.ui.screens.cart.CartVm
import com.riramzy.littlelemon.ui.screens.details.DetailsVm
import com.riramzy.littlelemon.ui.screens.home.HomeVm
import com.riramzy.littlelemon.ui.screens.orders.OrdersVm
import com.riramzy.littlelemon.ui.screens.payment.PaymentVm
import com.riramzy.littlelemon.ui.screens.reservation.ReservationVm
import com.riramzy.littlelemon.ui.screens.search.SearchVm
import com.riramzy.littlelemon.ui.viewmodel.UserVm
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {
    @ViewModelScoped
    @Provides
    fun provideCartVm(cartRepo: CartRepo): CartVm {
        return CartVm(cartRepo)
    }

    @ViewModelScoped
    @Provides
    fun provideDetailsVm(menuRepo: MenuRepo): DetailsVm {
        return DetailsVm(menuRepo)
    }

    @ViewModelScoped
    @Provides
    fun provideHomeVm(menuRepo: MenuRepo): HomeVm {
        return HomeVm(menuRepo)
    }

    @ViewModelScoped
    @Provides
    fun provideUserVm(userRepo: UserRepo): UserVm {
        return UserVm(userRepo)
    }

    @ViewModelScoped
    @Provides
    fun provideReservationVm(reservationsRepo: ReservationsRepo, userVm: UserVm): ReservationVm {
        return ReservationVm(
            reservationsRepo,
            userVm
        )
    }

    @ViewModelScoped
    @Provides
    fun provideOrdersVm(ordersRepo: OrdersRepo, reservationsRepo: ReservationsRepo): OrdersVm {
        return OrdersVm(
            ordersRepo,
            reservationsRepo
        )
    }

    @ViewModelScoped
    @Provides
    fun providePaymentVm(userVm: UserVm, reservationsRepo: ReservationsRepo): PaymentVm {
        return PaymentVm(
            userVm,
            reservationsRepo
        )
    }

    @ViewModelScoped
    @Provides
    fun provideSearchVm(searchRepo: SearchRepo): SearchVm {
        return SearchVm(searchRepo)
    }
}