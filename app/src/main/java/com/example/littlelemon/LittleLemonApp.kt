package com.example.littlelemon

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.littlelemon.di.AppContainer
import com.example.littlelemon.ui.screens.cart.CartScreen
import com.example.littlelemon.ui.screens.checkout.CheckoutScreen
import com.example.littlelemon.ui.screens.confirmation.ConfirmationScreen
import com.example.littlelemon.ui.screens.details.ItemDetailsScreen
import com.example.littlelemon.ui.screens.home.HomeScreen
import com.example.littlelemon.ui.screens.login.LoginScreen
import com.example.littlelemon.ui.screens.onboarding.OnboardingBrowseScreen
import com.example.littlelemon.ui.screens.onboarding.OnboardingFindScreen
import com.example.littlelemon.ui.screens.onboarding.OnboardingQuickScreen
import com.example.littlelemon.ui.screens.onboarding.OnboardingWelcomeScreen
import com.example.littlelemon.ui.screens.orders.OrdersScreen
import com.example.littlelemon.ui.screens.payment.PaymentScreen
import com.example.littlelemon.ui.screens.profile.ProfileScreen
import com.example.littlelemon.ui.screens.reservation.ReservationTableDetailsScreen
import com.example.littlelemon.ui.screens.reservation.ReservationsScreen
import com.example.littlelemon.ui.screens.search.SearchScreen
import com.example.littlelemon.ui.screens.signup.SignUpScreen
import com.example.littlelemon.utils.Screen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LittleLemonApp() {
    val context = LocalContext.current
    val appContainer by remember { mutableStateOf(AppContainer(context)) }
    val navController = rememberNavController()

    val userVm = appContainer.userVm
    val reservationVm = appContainer.reservationVm
    val cartVm = appContainer.cartVm
    val homeVm = appContainer.homeVm
    val ordersVm = appContainer.ordersVm
    
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
                vm = userVm,
                //loginVm = loginVm
            )
        }

        composable(Screen.Register.route) {
            SignUpScreen(
                navController = navController,
                vm = userVm
            )
        }

        //--- Home ---
        composable(Screen.Home.route) {
            HomeScreen(
                appContainer = appContainer,
                navController = navController,
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
                appContainer = appContainer,
                navController = navController
            )
        }

        //--- Profile ---
        composable(Screen.Profile.route) {
            ProfileScreen(
                navController = navController,
                vm = userVm
            )
        }

        //--- Reservation ---
        composable(Screen.ReservationTableDetails.route) {
            ReservationTableDetailsScreen(
                onNextClicked = { navController.navigate(Screen.ReservationPayment.route) },
                vm = reservationVm,
                navController = navController
            )
        }

        //--- Confirmation ---
        //--- Reservation Confirmation ---
        composable(Screen.ReservationConfirmation.route) {
            ConfirmationScreen(
                vm = reservationVm,
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
                vm = reservationVm,
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
                appContainer = appContainer
            )
        }

        composable(Screen.CartPayment.route) {
            PaymentScreen(
                navController = navController,
                onNextClickedCart = { navController.navigate(Screen.CartConfirmation.route) },
                isForCart = true,
                isForReservation = false,
                appContainer = appContainer
            )
        }

        //--- Search ---
        composable(Screen.Search.route) {
            SearchScreen(
                navController = navController,
                appContainer = appContainer,
                category = null
            )
        }

        composable(
            route = Screen.CategorySearch.route,
            arguments = listOf(navArgument("category") { type = NavType.StringType })
        ) {
            val category = it.arguments?.getString("category") ?: ""
            SearchScreen(
                navController = navController,
                appContainer = appContainer,
                category = category
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
                navController = navController
            )
        }
    }
}