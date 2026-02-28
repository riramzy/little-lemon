package com.riramzy.littlelemon.ui.screens.checkout

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.riramzy.littlelemon.data.local.cart.LocalCartItem
import com.riramzy.littlelemon.data.local.menu.LocalMenuItem
import com.riramzy.littlelemon.ui.components.ItemOverview
import com.riramzy.littlelemon.ui.components.LemonCutlerySelector
import com.riramzy.littlelemon.ui.components.LemonNavigationBar
import com.riramzy.littlelemon.ui.components.TopAppBar
import com.riramzy.littlelemon.ui.screens.cart.CartVm
import com.riramzy.littlelemon.ui.screens.home.HomeVm
import com.riramzy.littlelemon.ui.theme.LittleLemonTheme
import com.riramzy.littlelemon.utils.Screen

@Composable
fun CheckoutScreen(
    cartVm: CartVm = hiltViewModel(),
    homeVm: HomeVm = hiltViewModel(),
    navController: NavController
) {
    val cartItems = cartVm.cartItems.collectAsState().value
    val menuItems = homeVm.menuItems.collectAsState().value

    CheckoutScreenContent(
        navController = navController,
        cartItems = cartItems,
        menuItems = menuItems
    )
}

@Composable
fun CheckoutScreenContent(
    navController: NavController,
    cartItems: List<LocalCartItem>,
    menuItems: List<LocalMenuItem>
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
                onActionClicked = {
                    navController.navigate(Screen.CartPayment.route)
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
                    LemonCutlerySelector(
                        modifier = Modifier
                            .padding(
                                horizontal = 15.dp,
                                vertical = 15.dp
                            )
                    )
                    OrderSummary(
                        modifier = Modifier
                            .padding(
                                horizontal = 15.dp,
                                vertical = 15.dp
                            )
                            .wrapContentHeight(),
                        cartItems = cartItems
                    )
                    SuggestionsSection(
                        modifier = Modifier
                            .padding(
                                horizontal = 15.dp,
                                vertical = 15.dp
                            ),
                        menuItems = menuItems
                    )
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

@Composable
fun OrderSummary(
    modifier: Modifier = Modifier,
    cartItems: List<LocalCartItem> = emptyList()
) {
    /*
    val cartItems = listOf(
        LocalCartItem(
            id = 1,
            title = "Greek Salad",
            price = 12.99,
            image = "",
        ),
        LocalCartItem(
            id = 2,
            title = "Bruschetta",
            price = 12.99,
            image = "",
        )
    )

     */

    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Order Summary",
            style = MaterialTheme.typography.labelLarge,
            fontSize = 24.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "Items",
            style = MaterialTheme.typography.labelLarge,
            fontSize = 20.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            cartItems.forEach { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${item.quantity} x ${item.title}",
                        style = MaterialTheme.typography.labelSmall,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Start,
                    )
                    Text(
                        text = "$${item.price}",
                        style = MaterialTheme.typography.labelSmall,
                        fontSize = 20.sp,
                        textAlign = TextAlign.End,
                    )
                }
            }
        }
    }
}

@Composable
fun SuggestionsSection(
    modifier: Modifier = Modifier,
    menuItems: List<LocalMenuItem> = emptyList()
) {
    Column(
        modifier = modifier
            .wrapContentWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "ADD MORE TO YOUR ORDER!",
            style = MaterialTheme.typography.titleLarge,
            fontSize = 20.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )
        LazyRow(
            modifier = Modifier.padding(top = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            items(menuItems) { item ->
                ItemOverview(
                    modifier = Modifier
                        .width(295.dp)
                        .height(110.dp)
                        .padding(end = 10.dp),
                    itemName = item.title,
                    itemPrice = item.price,
                    itemDescription = item.description,
                    itemId = item.id
                )
            }
        }
    }
}

@Composable
fun TotalPrice(
    modifier: Modifier = Modifier,
    subtotal: Double = 0.00,
    service: Double = 5.00,
    delivery: Double = 5.00,
    isCart: Boolean = false
){
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSystemInDarkTheme()) {
                MaterialTheme.colorScheme.onTertiary
            } else {
                MaterialTheme.colorScheme.primaryContainer

            },
            contentColor = if (isSystemInDarkTheme()) {
                MaterialTheme.colorScheme.primary
            } else {
                Color.White
            }
        ),
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Subtotal",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Start,
                )
                Text(
                    text = "$$subtotal",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.End,
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Delivery",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Start,
                )
                Text(
                    text = "$$delivery",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.End,
                )
            }

            if (!isCart) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 15.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Service",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Start,
                    )
                    Text(
                        text = "$$service",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.End,
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Total".uppercase(),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Black,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Start,
                )
                Text(
                    text = if (isCart) {
                        "$${subtotal + delivery}"
                    } else {
                        "$${subtotal + delivery + service}"
                    },
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Black,
                    fontSize = 20.sp,
                    textAlign = TextAlign.End,
                )
            }
        }
    }
}

@Preview
@Composable
fun CheckoutScreenPreview() {
    LittleLemonTheme {
        CheckoutScreenContent(
            navController = rememberNavController(),
            cartItems = emptyList(),
            menuItems = emptyList()
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CheckoutScreenDarkPreview() {
    LittleLemonTheme {
        CheckoutScreenContent(
            navController = rememberNavController(),
            cartItems = emptyList(),
            menuItems = emptyList()
        )
    }
}