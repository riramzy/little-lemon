package com.example.littlelemon.ui.screens.payment

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.littlelemon.R
import com.example.littlelemon.di.AppContainer
import com.example.littlelemon.ui.components.InputField
import com.example.littlelemon.ui.components.LemonNavigationBar
import com.example.littlelemon.ui.components.LemonPaymentSelector
import com.example.littlelemon.ui.components.SubInputField
import com.example.littlelemon.ui.components.TopAppBar
import com.example.littlelemon.ui.theme.LittleLemonTheme
import com.example.littlelemon.utils.Screen

@Composable
fun PaymentScreen(
    onNextClickedCart: () -> Unit = {},
    onNextClickedReservation: () -> Unit = {},
    navController: NavHostController,
    isForReservation: Boolean = false,
    isForCart: Boolean = true,
    appContainer: AppContainer
) {
    val context = LocalContext.current
    val paymentVm: PaymentVm = viewModel(
        factory = PaymentVmFactory(
            userVm = appContainer.userVm,
            reservationVm = appContainer.reservationVm
        )
    )

    val navBackStackEntry by navController.currentBackStackEntryFlow.collectAsState(null)
    val currentRoute = navBackStackEntry?.destination?.route ?: Screen.Home.route

    val isLogged = appContainer.userVm.isLoggedIn()

    // Collect state from the ViewModel
    val firstName by paymentVm.firstName.collectAsState()
    val lastName by paymentVm.lastName.collectAsState()
    val email by paymentVm.email.collectAsState()
    val phoneNumber by paymentVm.phoneNumber.collectAsState()
    val paymentMethod by paymentVm.paymentMethod.collectAsState()
    val cardNumber by paymentVm.cardNumber.collectAsState()
    val cardMonth by paymentVm.cardMonth.collectAsState()
    val cardYear by paymentVm.cardYear.collectAsState()
    val cardCvv by paymentVm.cardCvv.collectAsState()

    // Listen for toast messages
    LaunchedEffect(Unit) {
        paymentVm.toastMessage.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

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
                onActionText = if (isForReservation) "Confirm" else if (isForCart) "Submit" else "",
                onActionClicked = {
                    val onNext = if (isForReservation) onNextClickedReservation else onNextClickedCart
                    paymentVm.onNextClicked(onNext)
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
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PaymentHeadline(
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .padding(bottom = 25.dp),
                isForReservation = isForReservation,
                isForCart = isForCart
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                contentPadding = PaddingValues(
                    bottom = innerPadding.calculateBottomPadding() + 100.dp
                ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(25.dp)
            ) {
                item {
                    UserInformation(
                        modifier = Modifier.padding(
                            horizontal = 15.dp,
                        ),
                        isLogged = isLogged,
                        firstName = firstName,
                        onFirstNameChange = paymentVm::onFirstNameChange,
                        lastName = lastName,
                        onLastNameChange = paymentVm::onLastNameChange,
                        email = email,
                        onEmailChange = paymentVm::onEmailChange,
                        phoneNumber = phoneNumber,
                        onPhoneNumberChange = paymentVm::onPhoneNumberChange
                    )
                }
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
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
                            isSelected = paymentMethod == "Mastercard",
                            onClick = { paymentVm.onPaymentMethodChange("Mastercard") },
                            modifier = Modifier.padding(
                                bottom = 15.dp
                            )
                        )
                        LemonPaymentSelector(
                            title = "Visa",
                            subtitle = "Yes, we can charge as well",
                            picture = R.drawable.visa_logo,
                            isSelected = paymentMethod == "Visa",
                            onClick = { paymentVm.onPaymentMethodChange("Visa") },
                            modifier = Modifier.padding(
                                bottom = 15.dp
                            )
                        )
                        if (isForCart) {
                            LemonPaymentSelector(
                                title = "Cash On Delivery",
                                subtitle = "We will charge you, later",
                                picture = R.drawable.cash,
                                isSelected = paymentMethod == "Cash On Delivery",
                                onClick = { paymentVm.onPaymentMethodChange("Cash On Delivery") },
                            )
                        }
                    }
                }
                if (paymentMethod != "Cash On Delivery") {
                    item {
                        CardInformation(
                            modifier = Modifier.padding(horizontal = 15.dp),
                            cardNumber = cardNumber,
                            onCardNumberChange = paymentVm::onCardNumberChange,
                            month = cardMonth,
                            onMonthChange = paymentVm::onCardMonthChange,
                            year = cardYear,
                            onYearChange = paymentVm::onCardYearChange,
                            cvv = cardCvv,
                            onCvvChange = paymentVm::onCardCvvChange
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PaymentHeadline(
    modifier: Modifier = Modifier,
    isForReservation: Boolean = false,
    isForCart: Boolean = true
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = if (isForReservation) {
                "Table Reservation".uppercase()
            } else if (isForCart) {
                "Checkout".uppercase()
            } else {
                "Payment".uppercase()
            },
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
    modifier: Modifier = Modifier,
    isLogged: Boolean = false,
    firstName: String,
    onFirstNameChange: (String) -> Unit,
    lastName: String,
    onLastNameChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    phoneNumber: String,
    onPhoneNumberChange: (String) -> Unit
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
            value = firstName.replaceFirstChar { it.uppercase() },
            onValueChange = onFirstNameChange,
            isReadOnly = isLogged,
            modifier = Modifier
                .padding(bottom = 15.dp)
        )
        InputField(
            requiredText = "Last Name",
            value = lastName.replaceFirstChar { it.uppercase() },
            onValueChange = onLastNameChange,
            isReadOnly = isLogged,
            modifier = Modifier
                .padding(bottom = 15.dp)
        )
        InputField(
            requiredText = "Email",
            value = email,
            onValueChange = onEmailChange,
            isReadOnly = isLogged,
            modifier = Modifier
                .padding(bottom = 15.dp)
        )
        InputField(
            requiredText = "Phone Number",
            value = phoneNumber,
            onValueChange = onPhoneNumberChange,
            modifier = Modifier
        )
    }
}

@Composable
fun CardInformation(
    modifier: Modifier = Modifier,
    cardNumber: String,
    onCardNumberChange: (String) -> Unit,
    month: String,
    onMonthChange: (String) -> Unit,
    year: String,
    onYearChange: (String) -> Unit,
    cvv: String,
    onCvvChange: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        InputField(
            requiredText = "Card Number",
            value = cardNumber,
            onValueChange = onCardNumberChange,
            modifier = Modifier
                .padding(bottom = 15.dp)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SubInputField(
                requiredText = "Month",
                value = month,
                onValueChange = onMonthChange,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 6.dp)
            )
            SubInputField(
                requiredText = "Year",
                value = year,
                onValueChange = onYearChange,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 6.dp)
            )
            SubInputField(
                requiredText = "CVV",
                value = cvv,
                onValueChange = onCvvChange,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview
@Composable
fun PaymentScreenPreview() {
    LittleLemonTheme {
        PaymentScreen(
            onNextClickedCart = {},
            onNextClickedReservation = {},
            navController = rememberNavController(),
            appContainer = AppContainer(
                LocalContext.current
            )
        )
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PaymentScreenDarkPreview() {
    LittleLemonTheme {
        PaymentScreen(
            onNextClickedCart = {},
            onNextClickedReservation = {},
            navController = rememberNavController(),
            appContainer = AppContainer(
                LocalContext.current
            )
        )
    }
}
