package com.example.littlelemon.utils

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding1")
    object Home : Screen("home")
    object Details : Screen("details/{itemId}") {
        fun createRoute(itemId: Int) = "details/$itemId"
    }
    object Profile : Screen("profile")
    object Login : Screen("login")
    object Register : Screen("register")
    object ReservationTableDetails : Screen("reservationTableDetails")
    object ReservationTableConfirmation : Screen("reservationTableConfirmation")
    object ReservationPayment : Screen("reservationPayment")
    object Cart : Screen("cart")
}