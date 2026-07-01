package com.riramzy.littlelemon.ui.screens.confirmation

import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.riramzy.littlelemon.data.local.cart.LocalCartItem
import com.riramzy.littlelemon.ui.components.LemonCutlerySelector
import com.riramzy.littlelemon.ui.components.LemonInputField
import com.riramzy.littlelemon.ui.components.LemonNavigationBar
import com.riramzy.littlelemon.ui.components.TopAppBar
import com.riramzy.littlelemon.ui.screens.auth.UserVm
import com.riramzy.littlelemon.ui.screens.cart.CartVm
import com.riramzy.littlelemon.ui.screens.orders.OrdersVm
import com.riramzy.littlelemon.ui.screens.reservation.ReservationVm
import com.riramzy.littlelemon.ui.theme.LittleLemonTheme
import com.riramzy.littlelemon.utils.Screen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ConfirmationScreen(
    reservationVm: ReservationVm = hiltViewModel(),
    cartVm: CartVm = hiltViewModel(),
    ordersVm: OrdersVm = hiltViewModel(),
    userVm: UserVm = hiltViewModel(),
    navController: NavController,
    isCart: Boolean = true,
    isReservation: Boolean = false,
) {
    val formState by reservationVm.reservationFormState.collectAsStateWithLifecycle()

    val cartItems by cartVm.cartItems.collectAsStateWithLifecycle()

    val userName by userVm.fullName.collectAsStateWithLifecycle()
    val orderId by reservationVm.orderId.collectAsStateWithLifecycle()
    val paymentType by reservationVm.selectedPaymentMethod.collectAsStateWithLifecycle()
    val phoneNumber by reservationVm.phoneNumber.collectAsStateWithLifecycle()

    val selectedTime = formState.selectedTime
    val selectedDuration = formState.selectedDuration
    val selectedNumberOfDiners = formState.selectedNumberOfDiners
    val selectedDate = formState.selectedDate

    ConfirmationScreenContent(
        navController = navController,
        cartItems = cartItems,
        userName = userName,
        orderId = orderId ?: "12",
        paymentType = paymentType.toString(),
        phoneNumber = phoneNumber ?: "01286909899",
        selectedTime = selectedTime.toString(),
        selectedNumberOfDiners = selectedNumberOfDiners.toString(),
        selectedDate = selectedDate.toString(),
        selectedDuration = selectedDuration.toString(),
        isCart = isCart,
        isReservation = isReservation,
        placeOrder = ordersVm::placeOrder,
        clearCart = cartVm::clearCart,
        confirmReservation = { reservationVm.confirmReservation(paymentType ?: "Mastercard") }
    )
}

@Composable
fun ConfirmationScreenContent(
    navController: NavController,
    cartItems: List<LocalCartItem>,
    userName: String,
    orderId: String,
    paymentType: String,
    phoneNumber: String,
    selectedTime: String,
    selectedNumberOfDiners: String,
    selectedDate: String,
    selectedDuration: String,
    isCart: Boolean,
    isReservation: Boolean,
    placeOrder: (List<LocalCartItem>, String) -> Unit,
    clearCart: () -> Unit,
    confirmReservation: () -> Unit = {}
) {
    val navBackStackEntry by navController.currentBackStackEntryFlow.collectAsStateWithLifecycle(
        null
    )
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
                onActionText = "Confirm",
                onActionClicked = {
                    if (isCart) {
                        placeOrder(cartItems, paymentType)
                        clearCart()
                        navController.navigate(Screen.Orders.route)
                    } else {
                        confirmReservation()
                        navController.navigate(Screen.Reservations.route)
                    }
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
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        modifier = Modifier
            .statusBarsPadding()
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(25.dp),
            contentPadding = PaddingValues(
                bottom = innerPadding.calculateBottomPadding() + 100.dp
            )
        ) {
            item {
                LemonCutlerySelector(
                    modifier = Modifier.padding(
                        horizontal = 15.dp,
                    )
                )
            }

            item {
                ReservationDetailsCard(
                    modifier = Modifier.padding(
                        horizontal = 15.dp,
                    ),
                    cartItems = cartItems,
                    isCart = isCart,
                    isReservation = isReservation,
                    userName = userName,
                    orderId = orderId,
                    paymentType = paymentType,
                    phoneNumber = phoneNumber,
                    selectedTime = selectedTime,
                    selectedDuration = selectedDuration,
                    selectedDate = selectedDate,
                    selectedNumberOfDiners = selectedNumberOfDiners
                )
            }

            item {
                AdditionalNotes(
                    modifier = Modifier.padding(
                        horizontal = 15.dp,
                    )
                )
            }
        }
    }
}

@Composable
fun ReservationDetailsCard(
    modifier: Modifier = Modifier,
    cartItems: List<LocalCartItem> = emptyList(),
    isCart: Boolean,
    isReservation: Boolean,
    userName: String = "Ramzi",
    orderId: String,
    paymentType: String,
    phoneNumber: String,
    selectedTime: String,
    selectedNumberOfDiners: String,
    selectedDate: String,
    selectedDuration: String,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHighest
        )
    ) {
        Column(
            modifier = Modifier.padding(15.dp)
        ) {
            Text(
                text = if (isCart) "Your Order is Placed!".uppercase() else if (isReservation) "Your Reservation is accepted!".uppercase() else "Confirmation".uppercase(),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 15.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                LemonInputField(
                    requiredText = "Order ID",
                    value = orderId,
                    isReadOnly = true
                )

                LemonInputField(
                    requiredText = "Name",
                    value = userName.split(" ").joinToString(" ") {
                        it.replaceFirstChar { c -> c.uppercase() }
                    },
                    isReadOnly = true
                )

                LemonInputField(
                    requiredText = "Phone number",
                    isReadOnly = true,
                    value = phoneNumber
                )

                if (isReservation) {
                    LemonInputField(
                        requiredText = "At",
                        isReadOnly = true,
                        value = selectedTime,
                    )

                    LemonInputField(
                        requiredText = "For",
                        isReadOnly = true,
                        value = selectedNumberOfDiners,
                    )

                    LemonInputField(
                        requiredText = "Date",
                        isReadOnly = true,
                        value = selectedDate,
                    )

                    LemonInputField(
                        requiredText = "Duration",
                        isReadOnly = true,
                        value = selectedDuration,
                    )
                }

                LemonInputField(
                    requiredText = "Payment type",
                    isReadOnly = true,
                    value = paymentType
                )

                if (isCart) {
                    LemonInputField(
                        requiredText = "Items",
                        isReadOnly = true,
                        value = cartItems.joinToString(separator = "\n") { cartItem ->
                            val addOnSuffix =
                                if (cartItem.selectedAddOns.isNotEmpty()) " (${cartItem.selectedAddOns})" else ""
                            "${cartItem.quantity}x ${cartItem.title}$addOnSuffix"
                        },
                        isMultiline = true
                    )
                }

                LemonInputField(
                    requiredText = "Total",
                    isReadOnly = true,
                    value = if (isReservation) "0.00" else "%.2f".format(cartItems.sumOf { it.price * it.quantity } + 5)
                )
            }
        }
    }
}

@Composable
fun AdditionalNotes(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Additional Notes",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp)
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer
            ),
            border = BorderStroke(
                width = 2.dp,
                color = MaterialTheme.colorScheme.onSurface
            )
        ) {
            TextField(
                value = "",
                onValueChange = {},
                modifier = Modifier
                    .fillMaxSize(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                keyboardActions = KeyboardActions(
                    onDone = {  }
                ),
            )
        }
    }
}

@Preview
@Composable
fun ConfirmationScreenPreview() {
    LittleLemonTheme {
        ConfirmationScreenContent(
            navController = NavController(LocalContext.current),
            cartItems = emptyList(),
            userName = "Ramzy",
            orderId = "123456",
            paymentType = "COD",
            phoneNumber = "01286909899",
            selectedDate = "4th Dec 2026",
            selectedNumberOfDiners = "3",
            selectedTime = "19:00",
            selectedDuration = "3 hours",
            isCart = true,
            isReservation = false,
            placeOrder = { _, _ -> },
            clearCart = {}
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ConfirmationScreenDarkPreview() {
    LittleLemonTheme {
        ConfirmationScreenContent(
            navController = NavController(LocalContext.current),
            cartItems = emptyList(),
            userName = "Ramzy",
            orderId = "123456",
            paymentType = "COD",
            phoneNumber = "01286909899",
            selectedDate = "4th Dec 2026",
            selectedNumberOfDiners = "3",
            selectedTime = "19:00",
            selectedDuration = "3 hours",
            isCart = true,
            isReservation = false,
            placeOrder = { _, _ -> },
            clearCart = {}
        )
    }
}