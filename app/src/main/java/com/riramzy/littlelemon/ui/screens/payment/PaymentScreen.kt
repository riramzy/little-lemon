package com.riramzy.littlelemon.ui.screens.payment

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.riramzy.littlelemon.R
import com.riramzy.littlelemon.ui.components.LemonInputField
import com.riramzy.littlelemon.ui.components.LemonNavigationBar
import com.riramzy.littlelemon.ui.components.LemonPaymentSelector
import com.riramzy.littlelemon.ui.components.LemonSubInputField
import com.riramzy.littlelemon.ui.components.TopAppBar
import com.riramzy.littlelemon.ui.theme.LittleLemonTheme
import com.riramzy.littlelemon.ui.viewmodel.UserVm
import com.riramzy.littlelemon.utils.Screen

@Composable
fun PaymentScreen(
    onNextClickedCart: () -> Unit = {},
    onNextClickedReservation: () -> Unit = {},
    navController: NavController,
    isForReservation: Boolean = false,
    isForCart: Boolean = true,
    paymentVm: PaymentVm = hiltViewModel(),
    userVm: UserVm = hiltViewModel()
) {
    val context = LocalContext.current

    val isLogged = userVm.isLoggedIn()

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

    PaymentScreenContent(
        onNextClicked = paymentVm::onNextClicked,
        onNextClickedCart = onNextClickedCart,
        onNextClickedReservation = onNextClickedReservation,
        onFirstNameChange = paymentVm::onFirstNameChange,
        onLastNameChange = paymentVm::onLastNameChange,
        onEmailChange = paymentVm::onEmailChange,
        onPhoneNumberChange = paymentVm::onPhoneNumberChange,
        onPaymentMethodChange = paymentVm::onPaymentMethodChange,
        onCardNumberChange = paymentVm::onCardNumberChange,
        onCardMonthChange = paymentVm::onCardMonthChange,
        onCardYearChange = paymentVm::onCardYearChange,
        onCardCvvChange = paymentVm::onCardCvvChange,
        navController = navController,
        isForReservation = isForReservation,
        isForCart = isForCart,
        isLogged = isLogged,
        firstName = firstName,
        lastName = lastName,
        email = email,
        phoneNumber = phoneNumber,
        paymentMethod = paymentMethod,
        cardNumber = cardNumber,
        cardMonth = cardMonth,
        cardYear = cardYear,
        cardCvv = cardCvv,
    )


}

@Composable
fun PaymentScreenContent(
    onNextClicked: (() -> Unit) -> Unit,
    onNextClickedCart: () -> Unit,
    onNextClickedReservation: () -> Unit,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    onPaymentMethodChange: (String) -> Unit,
    onCardNumberChange: (String) -> Unit,
    onCardMonthChange: (String) -> Unit,
    onCardYearChange: (String) -> Unit,
    onCardCvvChange: (String) -> Unit,
    navController: NavController,
    isForReservation: Boolean,
    isForCart: Boolean,
    isLogged: Boolean,
    firstName: String,
    lastName: String,
    email: String,
    phoneNumber: String,
    paymentMethod: String?,
    cardNumber: String,
    cardMonth: String,
    cardYear: String,
    cardCvv: String
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
                onActionText = if (isForReservation) "Confirm" else if (isForCart) "Submit" else "",
                onActionClicked = {
                    val onNext = if (isForReservation) onNextClickedReservation else onNextClickedCart
                    onNextClicked(onNext)
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
            .imePadding()
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
                        onFirstNameChange = onFirstNameChange,
                        lastName = lastName,
                        onLastNameChange = onLastNameChange,
                        email = email,
                        onEmailChange = onEmailChange,
                        phoneNumber = phoneNumber,
                        onPhoneNumberChange = onPhoneNumberChange
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
                            onClick = { onPaymentMethodChange("Mastercard") },
                            modifier = Modifier.padding(
                                bottom = 15.dp
                            )
                        )
                        LemonPaymentSelector(
                            title = "Visa",
                            subtitle = "Yes, we can charge as well",
                            picture = R.drawable.visa_logo,
                            isSelected = paymentMethod == "Visa",
                            onClick = { onPaymentMethodChange("Visa") },
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
                                onClick = { onPaymentMethodChange("Cash On Delivery") },
                            )
                        }
                    }
                }
                if (paymentMethod != "Cash On Delivery") {
                    item {
                        CardInformation(
                            modifier = Modifier.padding(horizontal = 15.dp),
                            cardNumber = cardNumber,
                            onCardNumberChange = onCardNumberChange,
                            month = cardMonth,
                            onMonthChange = onCardMonthChange,
                            year = cardYear,
                            onYearChange = onCardYearChange,
                            cvv = cardCvv,
                            onCvvChange = onCardCvvChange
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
        LemonInputField(
            requiredText = "First Name",
            value = firstName.replaceFirstChar { it.uppercase() },
            onValueChange = onFirstNameChange,
            isReadOnly = isLogged,
            modifier = Modifier
                .padding(bottom = 15.dp)
        )
        LemonInputField(
            requiredText = "Last Name",
            value = lastName.replaceFirstChar { it.uppercase() },
            onValueChange = onLastNameChange,
            isReadOnly = isLogged,
            modifier = Modifier
                .padding(bottom = 15.dp)
        )
        LemonInputField(
            requiredText = "Email",
            value = email,
            onValueChange = onEmailChange,
            isReadOnly = isLogged,
            modifier = Modifier
                .padding(bottom = 15.dp)
        )
        LemonInputField(
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
        LemonInputField(
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
            LemonSubInputField(
                requiredText = "Month",
                value = month,
                onValueChange = onMonthChange,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 6.dp)
            )
            LemonSubInputField(
                requiredText = "Year",
                value = year,
                onValueChange = onYearChange,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 6.dp)
            )
            LemonSubInputField(
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
        PaymentScreenContent(
            onNextClicked = {},
            onNextClickedCart = {},
            onNextClickedReservation = {},
            onFirstNameChange = {},
            onLastNameChange = {},
            onEmailChange = {},
            onPhoneNumberChange = {},
            onPaymentMethodChange = {},
            onCardNumberChange = {},
            onCardMonthChange = {},
            onCardYearChange = {},
            onCardCvvChange = {},
            navController = rememberNavController(),
            isForReservation = false,
            isForCart = false,
            isLogged = false,
            firstName = "",
            lastName = "",
            email = "",
            phoneNumber = "",
            paymentMethod = "",
            cardNumber = "",
            cardMonth = "",
            cardYear = "",
            cardCvv = ""
        )
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PaymentScreenDarkPreview() {
    LittleLemonTheme {
        PaymentScreenContent(
            onNextClicked = {},
            onNextClickedCart = {},
            onNextClickedReservation = {},
            onFirstNameChange = {},
            onLastNameChange = {},
            onEmailChange = {},
            onPhoneNumberChange = {},
            onPaymentMethodChange = {},
            onCardNumberChange = {},
            onCardMonthChange = {},
            onCardYearChange = {},
            onCardCvvChange = {},
            navController = rememberNavController(),
            isForReservation = false,
            isForCart = false,
            isLogged = false,
            firstName = "",
            lastName = "",
            email = "",
            phoneNumber = "",
            paymentMethod = "",
            cardNumber = "",
            cardMonth = "",
            cardYear = "",
            cardCvv = ""
        )
    }
}
