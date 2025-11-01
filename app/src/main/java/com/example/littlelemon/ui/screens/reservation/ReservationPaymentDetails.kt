package com.example.littlelemon.ui.screens.reservation

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.FabPosition
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
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
import androidx.navigation.NavHostController
import com.example.littlelemon.R
import com.example.littlelemon.ui.components.InputField
import com.example.littlelemon.ui.components.LemonNavigationBar
import com.example.littlelemon.ui.components.LemonPaymentSelector
import com.example.littlelemon.ui.components.SubInputField
import com.example.littlelemon.ui.components.TopAppBar
import com.example.littlelemon.ui.components.YellowLemonButton
import com.example.littlelemon.ui.theme.LittleLemonTheme
import com.example.littlelemon.utils.Screen

@Composable
fun ReservationPaymentScreen(
    onNextClicked: () -> Unit = {},
    navController: NavHostController,
) {
    val navBackStackEntry by navController.currentBackStackEntryFlow.collectAsState(null)
    val currentRoute = navBackStackEntry?.destination?.route ?: Screen.Home.route

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .padding(
                        vertical = 10.dp,
                        horizontal = 15.dp
                    ),
                isSearchRequired = false
            )
        },
        floatingActionButton = {
            LemonNavigationBar(
                isActionEnabled = true,
                onActionText = "Next",
                onActionClicked = onNextClicked,
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
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(25.dp)
        ) {
            item {
                PaymentHeadline(
                    modifier = Modifier.padding(
                        horizontal = 15.dp,
                    )
                )
            }
            item {
                UserInformation(
                    modifier = Modifier.padding(
                        horizontal = 15.dp,
                    )
                )
            }
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Payment Method",
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp)
                    )
                    LemonPaymentSelector(
                        title = "Mastercard",
                        subtitle = "So, we can charge you",
                        picture = R.drawable.mastercard_logo,
                        modifier = Modifier.padding(
                            bottom = 15.dp
                        )
                    )
                    LemonPaymentSelector(
                        title = "Visa",
                        subtitle = "Yes, we can charge as well",
                        picture = R.drawable.visa_logo,
                    )
                }
            }
            item {
                CardInformation(
                    modifier = Modifier.padding(
                        horizontal = 15.dp,
                    )
                )
            }
            /*
            item {
                LinearProgressIndicator(
                    progress = { 0.5f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 15.dp,
                            end = 15.dp,
                            top = 50.dp
                        ),
                    color = if (isSystemInDarkTheme()) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.primaryContainer
                    },
                    trackColor = MaterialTheme.colorScheme.tertiaryContainer,
                    strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
                )
            }

             */
        }
    }
}

@Composable
fun PaymentHeadline(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Table Reservation".uppercase(),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "Payment Details",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp)
        )
    }
}

@Composable
fun UserInformation(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Your Information",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
        )
        InputField(
            requiredText = "First Name",
            modifier= Modifier
                .padding(bottom = 15.dp)
        )
        InputField(
            requiredText = "Last Name",
            modifier = Modifier
                .padding(bottom = 15.dp)
        )
        InputField(
            requiredText = "Email",
            modifier = Modifier
                .padding(bottom = 15.dp)
        )
        InputField(
            requiredText = "Phone Number",
            modifier = Modifier
        )
    }
}

@Composable
fun CardInformation(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        InputField(
            requiredText = "Card Number",
            modifier = Modifier
                .padding(bottom = 15.dp)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SubInputField(
                requiredText = "Month",
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 6.dp)
            )
            SubInputField(
                requiredText = "Year",
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 6.dp)
            )
            SubInputField(
                requiredText = "CVV",
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview
@Composable
fun ReservationPaymentScreenPreview() {
    LittleLemonTheme {
        ReservationPaymentScreen(
            onNextClicked = {},
            navController = NavHostController(LocalContext.current)
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ReservationPaymentScreenDarkPreview() {
    LittleLemonTheme {
        ReservationPaymentScreen(
            onNextClicked = {},
            navController = NavHostController(LocalContext.current)
        )
    }
}