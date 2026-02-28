package com.riramzy.littlelemon.utils

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding1")
    object Home : Screen("home")
    object Details : Screen("details/{itemId}") {
        fun createRoute(itemId: Int) = "details/$itemId"
    }
    object Profile : Screen("profile")
    object ProfileDetails: Screen("profile_details")
    object Login : Screen("login")
    object Register : Screen("register")
    object ReservationTableDetails : Screen("reservationTableDetails")
    object ReservationConfirmation : Screen("reservationConfirmation")
    object CartConfirmation: Screen("cartConfirmation")
    object Cart : Screen("cart")
    object Checkout : Screen("checkout")
    object CartPayment: Screen("cart_payment")
    object ReservationPayment: Screen("reservation_payment")
    object Search : Screen("search")
    object CategorySearch : Screen("category_search/{category}") {
        fun createRoute(category: String) = "category_search/$category"
    }
    object Orders : Screen("orders")
    object Reservations : Screen("reservations")
}