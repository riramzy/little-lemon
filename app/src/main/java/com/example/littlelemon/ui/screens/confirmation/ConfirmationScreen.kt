package com.example.littlelemon.ui.screens.confirmation

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.littlelemon.data.local.cart.LocalCartItem
import com.example.littlelemon.di.AppContainer
import com.example.littlelemon.ui.components.LemonInputField
import com.example.littlelemon.ui.components.LemonCutlerySelector
import com.example.littlelemon.ui.components.LemonNavigationBar
import com.example.littlelemon.ui.components.TopAppBar
import com.example.littlelemon.ui.screens.cart.CartVm
import com.example.littlelemon.ui.screens.cart.CartVmFactory
import com.example.littlelemon.ui.screens.orders.OrdersVm
import com.example.littlelemon.ui.screens.orders.OrdersVmFactory
import com.example.littlelemon.ui.screens.reservation.ReservationVm
import com.example.littlelemon.ui.theme.LittleLemonTheme
import com.example.littlelemon.ui.viewmodel.UserVm
import com.example.littlelemon.utils.Screen

@Composable
fun ConfirmationScreen(
    modifier: Modifier = Modifier,
    vm: ReservationVm,
    cartVm: CartVm,
    ordersVm: OrdersVm,
    navController: NavController,
    isCart: Boolean = true,
    isReservation: Boolean = false,
) {
    val navBackStackEntry by navController.currentBackStackEntryFlow.collectAsState(null)
    val currentRoute = navBackStackEntry?.destination?.route ?: Screen.Home.route

    val cartItems = cartVm.cartItems.collectAsState().value
    val userRepo = AppContainer(LocalContext.current).userRepo
    val userName = userRepo.getFullName()

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
                        ordersVm.placeOrder(cartItems)
                        cartVm.clearCart()
                        navController.navigate(Screen.Orders.route)
                    } else {
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
        containerColor = if (isSystemInDarkTheme()) {
            MaterialTheme.colorScheme.background
        } else {
            Color.White
        },
        modifier = Modifier
            .statusBarsPadding()
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(25.dp)
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
                    vm = vm,
                    cartItems = cartItems,
                    isCart = isCart,
                    isReservation = isReservation,
                    userName = userName.toString(),
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
    vm: ReservationVm,
    cartItems: List<LocalCartItem> = emptyList(),
    isCart: Boolean,
    isReservation: Boolean,
    userName: String = "Ramzi",
) {
    Card(
        modifier = modifier
            .height(450.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSystemInDarkTheme()) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.primaryContainer
            }
        )
    ) {
        Column(
            modifier = Modifier.padding(15.dp)
        ) {
            Text(
                text = if (isCart) "Your Order is Placed!".uppercase() else if (isReservation) "Your Reservation is accepted!".uppercase() else TODO(),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 15.dp)
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    LemonInputField(
                        requiredText = "Order ID",
                        value = vm.orderId.value.toString(),
                        isReadOnly = true
                    )
                }

                item {
                    LemonInputField(
                        requiredText = "Name",
                        value = userName.split(" ").joinToString(" ") {
                            it.replaceFirstChar { c -> c.uppercase() }
                        },
                        isReadOnly = true
                    )
                }

                item {
                    LemonInputField(
                        requiredText = "Phone number",
                        isReadOnly = true,
                        value = vm.phoneNumber.value.toString()
                    )
                }

                if (isReservation) {
                    item {
                        LemonInputField(
                            requiredText = "At",
                            isReadOnly = true,
                            value = vm.selectedTime.value.toString(),
                        )
                    }

                    item {
                        LemonInputField(
                            requiredText = "For",
                            isReadOnly = true,
                            value = vm.selectedNumberOfDiners.value.toString(),
                        )
                    }

                    item {
                        LemonInputField(
                            requiredText = "Date",
                            isReadOnly = true,
                            value = vm.selectedDate.value.toString(),
                        )
                    }

                    item {
                        LemonInputField(
                            requiredText = "Duration",
                            isReadOnly = true,
                            value = vm.selectedDuration.value.toString(),
                        )
                    }
                }

                item {
                    LemonInputField(
                        requiredText = "Payment type",
                        isReadOnly = true,
                        value = vm.selectedPaymentMethod.value.toString()
                    )
                }

                if (isCart) {
                    item {
                        LemonInputField(
                            requiredText = "Items",
                            isReadOnly = true,
                            value = cartItems.joinToString(separator = "\n") { cartItem ->
                                "${cartItem.quantity}x ${cartItem.title}"
                            },
                            isMultiline = true
                        )
                    }
                }

                item {
                    LemonInputField(
                        requiredText = "Total",
                        isReadOnly = true,
                        value = "%.2f".format(cartItems.sumOf { it.price * it.quantity } + 5)                    )
                }
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
                containerColor = if (isSystemInDarkTheme()) {
                    MaterialTheme.colorScheme.onTertiary
                } else {
                    Color.White
                }
            ),
            border = if (isSystemInDarkTheme()) {
                BorderStroke(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.tertiaryContainer
                )
            } else {
                BorderStroke(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.secondaryContainer
                )
            }
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
        ConfirmationScreen(
            vm = ReservationVm(
                reservationsRepo = AppContainer(LocalContext.current).reservationsRepo,
                userVm = UserVm(AppContainer(LocalContext.current).userRepo)
            ),
            navController = NavController(LocalContext.current),
            cartVm = viewModel(factory = CartVmFactory(AppContainer(LocalContext.current))),
            ordersVm = viewModel(factory = OrdersVmFactory(AppContainer(LocalContext.current))),
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ConfirmationScreenDarkPreview() {
    LittleLemonTheme {
        ConfirmationScreen(
            vm = ReservationVm(
                reservationsRepo = AppContainer(LocalContext.current).reservationsRepo,
                userVm = UserVm(AppContainer(LocalContext.current).userRepo)
            ),
            navController = NavController(LocalContext.current),
            cartVm = viewModel(factory = CartVmFactory(AppContainer(LocalContext.current))),
            ordersVm = viewModel(factory = OrdersVmFactory(AppContainer(LocalContext.current))),
        )
    }
}