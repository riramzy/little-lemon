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
    object Cart : Screen("cart")
    object Checkout : Screen("checkout")
    object CartPayment: Screen("cart_payment")
    object ReservationPayment: Screen("reservation_payment")
    object Search : Screen("search")
    object GenreSearch : Screen("genre_search")
}