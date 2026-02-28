package com.riramzy.littlelemon.ui.screens.reservation

import android.content.res.Configuration
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.riramzy.littlelemon.R
import com.riramzy.littlelemon.data.local.reservations.LocalReservation
import com.riramzy.littlelemon.ui.components.LemonNavigationBar
import com.riramzy.littlelemon.ui.components.LemonReservationCard
import com.riramzy.littlelemon.ui.components.TopAppBar
import com.riramzy.littlelemon.ui.components.YellowLemonButton
import com.riramzy.littlelemon.ui.theme.LittleLemonTheme
import com.riramzy.littlelemon.utils.Screen

@Composable
fun ReservationsScreen(
    navController: NavController,
    reservationVm: ReservationVm = hiltViewModel()
) {
    //Collect reservations with items
    val reservations = reservationVm.reservations.collectAsState().value

    ReservationScreenContent(
        navController = navController,
        reservations = reservations,
        onClearClicked = {
            reservationVm.clearReservations()
        }
    )

}

@Composable
fun ReservationScreenContent(
    navController: NavController,
    reservations: List<LocalReservation>,
    onClearClicked: () -> Unit = {}
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
                if (reservations.isNotEmpty()) {
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
            }
        },
        floatingActionButton = {
            LemonNavigationBar(
                isActionEnabled = reservations.isNotEmpty(),
                onActionText = "Clear",
                onActionClicked = {
                    onClearClicked()
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
        if (reservations.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Image(
                    painter = painterResource(id = R.drawable.waiting_reservation),
                    contentDescription = "Empty Cart",
                )

                Text(
                    text = "No reservations yet",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 26.sp,
                    modifier = Modifier
                        .padding(vertical = 15.dp)
                )

                YellowLemonButton(
                    text = "Add a reservation now!",
                    color = if (isSystemInDarkTheme()) {
                        MaterialTheme.colorScheme.onPrimary
                    } else {
                        MaterialTheme.colorScheme.primaryContainer
                    },
                    textColor = if (isSystemInDarkTheme()) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onPrimaryContainer
                    },
                    onClick = {
                        navController.navigate(Screen.ReservationTableDetails.route)
                    }
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .statusBarsPadding(),
                contentPadding = PaddingValues(
                    bottom = innerPadding.calculateBottomPadding() + 100.dp
                ),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(reservations.size) { reservation ->
                    LemonReservationCard(
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 15.dp),
                        nameOfReserver = reservations[reservation].nameOfReserver,
                        date = reservations[reservation].date,
                        time = reservations[reservation].time,
                        duration = reservations[reservation].duration,
                        numberOfDiners = reservations[reservation].numberOfDiners,
                        totalPrice = reservations[reservation].totalPrice,
                        paymentMethod = reservations[reservation].paymentMethod,
                        reservationId = reservations[reservation].id
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ReservationsScreenPreview() {
    LittleLemonTheme {
        ReservationScreenContent(
            navController = rememberNavController(),
            reservations = emptyList()
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ReservationScreenDarkPreview() {
    LittleLemonTheme {
        ReservationScreenContent(
            navController = rememberNavController(),
            reservations = emptyList()
        )
    }
}

