package com.riramzy.littlelemon

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.riramzy.littlelemon.ui.screens.cart.CartScreen
import com.riramzy.littlelemon.ui.screens.cart.CartVm
import com.riramzy.littlelemon.ui.screens.checkout.CheckoutScreen
import com.riramzy.littlelemon.ui.screens.confirmation.ConfirmationScreen
import com.riramzy.littlelemon.ui.screens.details.ItemDetailsScreen
import com.riramzy.littlelemon.ui.screens.home.HomeScreen
import com.riramzy.littlelemon.ui.screens.home.HomeVm
import com.riramzy.littlelemon.ui.screens.login.LoginScreen
import com.riramzy.littlelemon.ui.screens.onboarding.OnboardingBrowseScreen
import com.riramzy.littlelemon.ui.screens.onboarding.OnboardingFindScreen
import com.riramzy.littlelemon.ui.screens.onboarding.OnboardingQuickScreen
import com.riramzy.littlelemon.ui.screens.onboarding.OnboardingWelcomeScreen
import com.riramzy.littlelemon.ui.screens.orders.OrdersScreen
import com.riramzy.littlelemon.ui.screens.orders.OrdersVm
import com.riramzy.littlelemon.ui.screens.payment.PaymentScreen
import com.riramzy.littlelemon.ui.screens.profile.ProfileDetailsScreen
import com.riramzy.littlelemon.ui.screens.profile.ProfileScreen
import com.riramzy.littlelemon.ui.screens.reservation.ReservationTableDetailsScreen
import com.riramzy.littlelemon.ui.screens.reservation.ReservationVm
import com.riramzy.littlelemon.ui.screens.reservation.ReservationsScreen
import com.riramzy.littlelemon.ui.screens.search.SearchScreen
import com.riramzy.littlelemon.ui.screens.search.SearchVm
import com.riramzy.littlelemon.ui.screens.signup.SignUpScreen
import com.riramzy.littlelemon.ui.viewmodel.UserVm
import com.riramzy.littlelemon.utils.Screen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LittleLemonApp() {
    val navController = rememberNavController()

    val userVm: UserVm = hiltViewModel()
    val reservationVm: ReservationVm = hiltViewModel()
    val cartVm: CartVm = hiltViewModel()
    val homeVm: HomeVm = hiltViewModel()
    val ordersVm: OrdersVm = hiltViewModel()
    val searchVm: SearchVm = hiltViewModel()
    
    val startDestination = when {
        !userVm.isOnboardingDone() -> Screen.Onboarding.route
        userVm.isLoggedIn() -> Screen.Home.route
        else -> Screen.Login.route
    }

    NavHost(navController = navController, startDestination = startDestination) {
        //--- Onboarding ---
        composable("onboarding1") {
            OnboardingWelcomeScreen(
                onNext = { navController.navigate("onboarding2") },
                onSkip = {
                    userVm.setOnboardingDone(true)
                    navController.navigate("login")
                }
            )
        }
        composable("onboarding2") {
            OnboardingBrowseScreen(
                onNext = { navController.navigate("onboarding3") },
                onSkip = {
                    userVm.setOnboardingDone(true)
                    navController.navigate("login")
                }
            )
        }
        composable("onboarding3") {
            OnboardingQuickScreen(
                onNext = { navController.navigate("onboarding4") },
                onSkip = {
                    userVm.setOnboardingDone(true)
                    navController.navigate("login")
                }
            )
        }
        composable("onboarding4") {
            OnboardingFindScreen(
                onNext = {
                    userVm.setOnboardingDone(true)
                    navController.navigate("login")
                         },
                onSkip = {
                    userVm.setOnboardingDone(true)
                    navController.navigate("home")
                }
            )
        }

        //--- Auth ---
        composable(Screen.Login.route) {
            LoginScreen(
                navController = navController,
                userVm = userVm,
                //loginVm = loginVm
            )
        }

        composable(Screen.Register.route) {
            SignUpScreen(
                navController = navController,
                userVm = userVm
            )
        }

        //--- Home ---
        composable(Screen.Home.route) {
            HomeScreen(
                navController = navController,
                searchVm = searchVm
            )
        }

        //--- Details ---
        composable(
            Screen.Details.route,
            arguments = listOf(navArgument("itemId") { type = NavType.IntType })
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getInt("itemId") ?: 1
            ItemDetailsScreen(
                itemId = itemId,
                navController = navController
            )
        }

        //--- Profile ---
        composable(Screen.Profile.route) {
            ProfileScreen(
                navController = navController,
                userVm = userVm
            )
        }

        //--- Profile Details ---
        composable(Screen.ProfileDetails.route) {
            ProfileDetailsScreen(
                navController = navController,
                userVm = userVm
            )
        }

        //--- Reservation ---
        composable(Screen.ReservationTableDetails.route) {
            ReservationTableDetailsScreen(
                reservationVm = reservationVm,
                navController = navController
            )
        }

        //--- Confirmation ---
        //--- Reservation Confirmation ---
        composable(Screen.ReservationConfirmation.route) {
            ConfirmationScreen(
                reservationVm = reservationVm,
                navController = navController,
                cartVm = cartVm,
                ordersVm = ordersVm,
                isCart = false,
                isReservation = true
            )
        }

        //--- Cart Confirmation ---
        composable(Screen.CartConfirmation.route) {
            ConfirmationScreen(
                reservationVm = reservationVm,
                navController = navController,
                cartVm = cartVm,
                ordersVm = ordersVm,
                isCart = true,
                isReservation = false
            )
        }

        //--- Cart ---
        composable(Screen.Cart.route) {
            CartScreen(
                cartVm = cartVm,
                navController = navController
            )
        }

        //--- Checkout ---
        composable(Screen.Checkout.route) {
            CheckoutScreen(
                cartVm = cartVm,
                homeVm = homeVm,
                navController = navController
            )
        }

        //--- Payment ---
        composable(Screen.ReservationPayment.route) {
            PaymentScreen(
                navController = navController,
                onNextClickedReservation = { navController.navigate(Screen.ReservationConfirmation.route) },
                isForReservation = true,
                isForCart = false,
            )
        }

        composable(Screen.CartPayment.route) {
            PaymentScreen(
                navController = navController,
                onNextClickedCart = { navController.navigate(Screen.CartConfirmation.route) },
                isForCart = true,
                isForReservation = false,
            )
        }

        //--- Search ---
        composable(Screen.Search.route) {
            SearchScreen(
                navController = navController,
                category = null,
                searchVm = searchVm
            )
        }

        composable(
            route = Screen.CategorySearch.route,
            arguments = listOf(navArgument("category") { type = NavType.StringType })
        ) {
            val category = it.arguments?.getString("category") ?: ""
            SearchScreen(
                navController = navController,
                category = category,
                searchVm = searchVm
            )
        }

        //Orders Screen
        composable(Screen.Orders.route) {
            OrdersScreen(
                navController = navController,
                ordersVm = ordersVm,
            )
        }

        //Reservations Screen
        composable(Screen.Reservations.route) {
            ReservationsScreen(
                navController = navController,
                reservationVm = reservationVm
            )
        }
    }
}
