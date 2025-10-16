package com.example.littlelemon.ui.screens.cart

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.littlelemon.R
import com.example.littlelemon.di.AppContainer
import com.example.littlelemon.ui.components.LemonCartItem
import com.example.littlelemon.ui.components.TopAppBar
import com.example.littlelemon.ui.components.YellowLemonButton
import com.example.littlelemon.ui.screens.checkout.TotalPrice
import com.example.littlelemon.ui.theme.LittleLemonTheme
import com.example.littlelemon.utils.Screen

@Composable
fun CartScreen(
    cartVm: CartVm,
    navController: NavController = rememberNavController()
) {
    val cartItems = cartVm.cartItems.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .padding(
                        vertical = 10.dp,
                        horizontal = 15.dp
                    ),
                isSearchRequired = false,
                onProfileClicked = {
                    navController.navigate(Screen.Profile.route)
                }
            )
        },
        bottomBar = {
            if (cartItems.isNotEmpty()) {
                YellowLemonButton(
                    text = "Checkout",
                    modifier = Modifier
                        .padding(
                            horizontal = 15.dp,
                            vertical = 15.dp
                        )
                        .fillMaxWidth(),
                    color = if (isSystemInDarkTheme()) {
                        MaterialTheme.colorScheme.onPrimary
                    } else {
                        MaterialTheme.colorScheme.primaryContainer
                    },
                    textColor = if (isSystemInDarkTheme()) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onPrimaryContainer
                    }
                )
            }
        },
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
                                    cartVm.removeFromCart(item.id)
                                },
                                onAddClicked = {
                                    cartVm.increaseQuantity(item.id)
                                },
                                onMinusClicked = {
                                    cartVm.decreaseQuantity(item.id)
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
        CartScreen(
            cartVm = viewModel(factory = CartVmFactory(AppContainer(LocalContext.current)))
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CartScreenDarkPreview() {
    LittleLemonTheme {
        CartScreen(
            cartVm = viewModel(factory = CartVmFactory(AppContainer(LocalContext.current)))
        )
    }
}
