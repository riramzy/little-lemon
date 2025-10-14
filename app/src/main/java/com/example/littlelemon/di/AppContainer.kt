package com.example.littlelemon.di

import android.content.Context
import com.example.littlelemon.data.local.LocalDatabase
import com.example.littlelemon.data.preferences.UserPreferences
import com.example.littlelemon.data.repos.MenuRepo
import com.example.littlelemon.data.repos.UserRepo
import com.example.littlelemon.ui.screens.home.HomeVm
import com.example.littlelemon.ui.screens.reservation.ReservationVm
import com.example.littlelemon.ui.viewmodel.UserVm

class AppContainer(context: Context) {
    private val database = LocalDatabase.getInstance(context)
    val repository = MenuRepo(database.localMenuDao())

    private val userPreferences = UserPreferences(context)
    val userRepository = UserRepo(userPreferences)

    val userVm = UserVm(userRepository)
    val homeVm = HomeVm(repository)
    val reservationVm = ReservationVm()
}