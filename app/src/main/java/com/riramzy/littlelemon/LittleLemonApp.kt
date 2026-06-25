package com.riramzy.littlelemon

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.riramzy.littlelemon.ui.screens.auth.UserVm
import com.riramzy.littlelemon.ui.screens.auth.login.LoginScreen
import com.riramzy.littlelemon.ui.screens.auth.signup.SignUpScreen
import com.riramzy.littlelemon.ui.screens.cart.CartScreen
import com.riramzy.littlelemon.ui.screens.checkout.CheckoutScreen
import com.riramzy.littlelemon.ui.screens.confirmation.ConfirmationScreen
import com.riramzy.littlelemon.ui.screens.details.ItemDetailsScreen
import com.riramzy.littlelemon.ui.screens.home.HomeScreen
import com.riramzy.littlelemon.ui.screens.onboarding.OnboardingBrowseScreen
import com.riramzy.littlelemon.ui.screens.onboarding.OnboardingFindScreen
import com.riramzy.littlelemon.ui.screens.onboarding.OnboardingQuickScreen
import com.riramzy.littlelemon.ui.screens.onboarding.OnboardingWelcomeScreen
import com.riramzy.littlelemon.ui.screens.orders.OrdersScreen
import com.riramzy.littlelemon.ui.screens.payment.PaymentScreen
import com.riramzy.littlelemon.ui.screens.profile.ProfileDetailsScreen
import com.riramzy.littlelemon.ui.screens.profile.ProfileScreen
import com.riramzy.littlelemon.ui.screens.reservation.ReservationTableDetailsScreen
import com.riramzy.littlelemon.ui.screens.reservation.ReservationVm
import com.riramzy.littlelemon.ui.screens.reservation.ReservationsScreen
import com.riramzy.littlelemon.ui.screens.search.SearchScreen
import com.riramzy.littlelemon.utils.Screen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LittleLemonApp() {
    val navController = rememberNavController()
    val userVm: UserVm = hiltViewModel()
    val reservationVm: ReservationVm = hiltViewModel()

    val isReady by userVm.isReady.collectAsStateWithLifecycle()
    val isLoggedIn by userVm.isLoggedIn.collectAsStateWithLifecycle()
    val isOnboardingDone by userVm.isOnboardingDone.collectAsStateWithLifecycle()

    if (!isReady) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = Color(0xFFF4C210)
            )
        }
    } else {
        val startDestination = when {
            !isOnboardingDone -> Screen.Onboarding.route
            isLoggedIn -> Screen.Home.route
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
            )
        }

        composable(Screen.Register.route) {
            SignUpScreen(
                navController = navController,
            )
        }

        //--- Home ---
        composable(Screen.Home.route) {
            HomeScreen(
                navController = navController
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
            )
        }

        //--- Profile Details ---
        composable(Screen.ProfileDetails.route) {
            ProfileDetailsScreen(
                navController = navController,
            )
        }

        //--- Reservation ---
        composable(Screen.ReservationTableDetails.route) {
            ReservationTableDetailsScreen(
                navController = navController,
                reservationVm = reservationVm
            )
        }

        //--- Confirmation ---
        //--- Reservation Confirmation ---
        composable(Screen.ReservationConfirmation.route) {
            ConfirmationScreen(
                navController = navController,
                isCart = false,
                isReservation = true,
                reservationVm = reservationVm
            )
        }

        //--- Cart Confirmation ---
        composable(Screen.CartConfirmation.route) {
            ConfirmationScreen(
                navController = navController,
                isCart = true,
                isReservation = false,
                reservationVm = reservationVm
            )
        }

        //--- Cart ---
        composable(Screen.Cart.route) {
            CartScreen(
                navController = navController
            )
        }

        //--- Checkout ---
        composable(Screen.Checkout.route) {
            CheckoutScreen(
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
                reservationVm = reservationVm
            )
        }

        composable(Screen.CartPayment.route) {
            PaymentScreen(
                navController = navController,
                onNextClickedCart = { navController.navigate(Screen.CartConfirmation.route) },
                isForCart = true,
                isForReservation = false,
                reservationVm = reservationVm
            )
        }

        //--- Search ---
            composable(
                route = Screen.Search.route,
                arguments = listOf(navArgument("query") { type = NavType.StringType })
            ) {
            SearchScreen(
                navController = navController,
            )
        }

        composable(
            route = Screen.CategorySearch.route,
            arguments = listOf(navArgument("category") { type = NavType.StringType })
        ) {
            SearchScreen(
                navController = navController,
            )
        }

        //Orders Screen
        composable(Screen.Orders.route) {
            OrdersScreen(
                navController = navController,
            )
        }

        //Reservations Screen
        composable(Screen.Reservations.route) {
            ReservationsScreen(
                navController = navController,
            )
        }
    }
}
}
