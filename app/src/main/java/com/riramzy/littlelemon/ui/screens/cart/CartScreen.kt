package com.riramzy.littlelemon.ui.screens.cart

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.layout.ContentScale
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
import com.riramzy.littlelemon.data.local.cart.LocalCartItem
import com.riramzy.littlelemon.ui.components.LemonCartItem
import com.riramzy.littlelemon.ui.components.LemonNavigationBar
import com.riramzy.littlelemon.ui.components.TopAppBar
import com.riramzy.littlelemon.ui.components.YellowLemonButton
import com.riramzy.littlelemon.ui.screens.checkout.TotalPrice
import com.riramzy.littlelemon.ui.theme.LittleLemonTheme
import com.riramzy.littlelemon.utils.Screen

@Composable
fun CartScreen(
    cartVm: CartVm = hiltViewModel(),
    navController: NavController
) {
    val cartItems = cartVm.cartItems.collectAsState().value

    CartScreenContent(
        navController = navController,
        cartItems = cartItems,
        removeFromCart = cartVm::removeFromCart,
        increaseQuantity = cartVm::increaseQuantity,
        decreaseQuantity = cartVm::decreaseQuantity
    )

}

@Composable
fun CartScreenContent(
    navController: NavController,
    cartItems: List<LocalCartItem>,
    removeFromCart: (Int) -> Unit,
    increaseQuantity: (Int) -> Unit,
    decreaseQuantity: (Int) -> Unit,
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
                isSearchRequired = false,
            )
        },
        floatingActionButton = {
            if (cartItems.isNotEmpty()) {
                LemonNavigationBar(
                    isActionEnabled = true,
                    onActionText = "Checkout",
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
            }
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
        if (cartItems.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.empty_cart),
                    modifier = Modifier
                        .size(300.dp),
                    contentScale = ContentScale.Fit,
                    contentDescription = null
                )
                Text(
                    text = "Your cart is empty",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 26.sp
                )
                YellowLemonButton(
                    text = "Continue Shopping Now!",
                    modifier = Modifier
                        .padding(
                            horizontal = 15.dp,
                            vertical = 15.dp
                        ),
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
                        navController.navigate(Screen.Home.route)
                    }
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentPadding = PaddingValues(
                    bottom = innerPadding.calculateBottomPadding() + 100.dp
                ),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 15.dp,
                                    end = 15.dp,
                                    bottom = 30.dp
                                ),
                        ) {
                            Text(
                                text = "My Cart",
                                style = MaterialTheme.typography.titleLarge,
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Black
                            )
                            Text(
                                text = "${cartItems.size} items",
                                style = MaterialTheme.typography.titleSmall,
                            )
                        }
                        cartItems.forEach { item ->
                            LemonCartItem(
                                item = item,
                                onRemoveClicked = {
                                    removeFromCart(item.id)
                                },
                                onAddClicked = {
                                    increaseQuantity(item.id)
                                },
                                onMinusClicked = {
                                    decreaseQuantity(item.id)
                                },
                                modifier = Modifier.padding(
                                    start = 15.dp,
                                    end = 15.dp,
                                    bottom = 20.dp
                                )
                            )
                        }
                    }
                }
                item {
                    val subtotal = cartItems.sumOf { it.price * it.quantity }
                    TotalPrice(
                        modifier = Modifier
                            .padding(
                                horizontal = 15.dp,
                                vertical = 15.dp
                            ),
                        isCart = true,
                        subtotal = subtotal
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun CartScreenPreview() {
    LittleLemonTheme {
        CartScreenContent(
            navController = rememberNavController(),
            cartItems = emptyList(),
            removeFromCart = { },
            increaseQuantity = { },
            decreaseQuantity = { }
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CartScreenDarkPreview() {
    LittleLemonTheme {
        CartScreenContent(
            navController = rememberNavController(),
            cartItems = emptyList(),
            removeFromCart = { },
            increaseQuantity = { },
            decreaseQuantity = { }
        )
    }
}
