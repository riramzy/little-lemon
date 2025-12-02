package com.example.littlelemon.ui.screens.reservation

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.littlelemon.ui.components.LemonNavigationBar
import com.example.littlelemon.ui.components.LemonReservationCard
import com.example.littlelemon.ui.components.TopAppBar
import com.example.littlelemon.ui.theme.LittleLemonTheme
import com.example.littlelemon.utils.Screen

@Composable
fun ReservationsScreen(
    navController: NavController
) {
    val navBackStackEntry by navController.currentBackStackEntryFlow.collectAsState(null)
    val currentRoute = navBackStackEntry?.destination?.route ?: Screen.Home.route

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    modifier = Modifier
                        .padding(
                            vertical = 10.dp,
                            horizontal = 15.dp
                        ),
                    isSearchRequired = false,
                )
                Text(
                    text = "Reservations",
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier
                        .padding(
                            start = 15.dp,
                            end = 15.dp,
                            bottom = 15.dp
                        )
                )
            }
        },
        floatingActionButton = {
            LemonNavigationBar(
                onActionClicked = {
                    navController.navigate(Screen.Checkout.route)
                },
                onHomeClicked = {
                    navController.navigate(Screen.Home.route)
                },
                onReservationClicked = {
                    navController.navigate(Screen.ReservationTableDetails.route)
                },
                onCartClicked = {
                    navController.navigate(Screen.Cart.route)
                },
                onProfileClicked = {
                    navController.navigate(Screen.Profile.route)
                },
                selectedRoute = currentRoute
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        containerColor = if (isSystemInDarkTheme()) {
            MaterialTheme.colorScheme.background
        } else {
            Color.White
        },
        modifier = Modifier
            .statusBarsPadding()
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .statusBarsPadding(),
            contentPadding = PaddingValues(
                bottom = innerPadding.calculateBottomPadding() + 100.dp
            ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(10) {
                LemonReservationCard(
                    modifier = Modifier.padding(
                        vertical = 8.dp
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun ReservationsScreenPreview() {
    LittleLemonTheme {
        ReservationsScreen(
            navController = NavController(LocalContext.current)
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ReservationScreenDarkPreview() {
    LittleLemonTheme {
        ReservationsScreen(
            navController = NavController(LocalContext.current)
        )
    }
}

