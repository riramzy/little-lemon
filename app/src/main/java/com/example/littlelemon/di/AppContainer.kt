package com.example.littlelemon.di

import android.content.Context
import com.example.littlelemon.data.local.LocalDatabase
import com.example.littlelemon.data.preferences.UserPreferences
import com.example.littlelemon.data.repos.CartRepo
import com.example.littlelemon.data.repos.MenuRepo
import com.example.littlelemon.data.repos.OrdersRepo
import com.example.littlelemon.data.repos.SearchRepo
import com.example.littlelemon.data.repos.UserRepo
import com.example.littlelemon.ui.screens.cart.CartVm
import com.example.littlelemon.ui.screens.home.HomeVm
import com.example.littlelemon.ui.screens.orders.OrdersVm
import com.example.littlelemon.ui.screens.payment.PaymentVm
import com.example.littlelemon.ui.screens.reservation.ReservationVm
import com.example.littlelemon.ui.screens.search.SearchVm
import com.example.littlelemon.ui.viewmodel.UserVm

class AppContainer(context: Context) {
    private val database = LocalDatabase.getInstance(context)
    private val userPreferences = UserPreferences(context)

    val menuRepo = MenuRepo(database.localMenuDao())
    val userRepo = UserRepo(userPreferences)
    val cartRepo = CartRepo(database.localCartDao())
    val searchRepo = SearchRepo(database.localSearchDao())
    val ordersRepo = OrdersRepo(database.localOrdersDao())

    val userVm = UserVm(userRepo)
    val homeVm = HomeVm(menuRepo)
    val reservationVm = ReservationVm()
    val cartVm = CartVm(cartRepo)
    val searchVm = SearchVm(searchRepo)
    val paymentVm = PaymentVm(userVm, reservationVm)
    val ordersVm = OrdersVm(ordersRepo, reservationVm)
}