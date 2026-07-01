package com.riramzy.littlelemon.ui.screens.orders

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
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.riramzy.littlelemon.R
import com.riramzy.littlelemon.data.local.orders.LocalOrder
import com.riramzy.littlelemon.data.local.orders.LocalOrderItem
import com.riramzy.littlelemon.data.local.orders.LocalOrderWithItems
import com.riramzy.littlelemon.ui.components.LemonNavigationBar
import com.riramzy.littlelemon.ui.components.LemonOrderCard
import com.riramzy.littlelemon.ui.components.TopAppBar
import com.riramzy.littlelemon.ui.components.YellowLemonButton
import com.riramzy.littlelemon.ui.theme.LittleLemonTheme
import com.riramzy.littlelemon.utils.Screen

@Composable
fun OrdersScreen(
    navController: NavController,
    ordersVm: OrdersVm = hiltViewModel(),
) {
    val ordersWithItems = ordersVm.ordersWithItems.collectAsStateWithLifecycle().value

    OrdersScreenContent(
        navController = navController,
        ordersWithItems = ordersWithItems,
        clearOrders = ordersVm::clearOrders
    )

}

@Composable
fun OrdersScreenContent(
    navController: NavController,
    ordersWithItems: List<LocalOrderWithItems>,
    clearOrders: () -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryFlow.collectAsStateWithLifecycle(
        null
    )
    val currentRoute = navBackStackEntry?.destination?.route ?: Screen.Home.route

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    modifier = Modifier
                        .padding(vertical = 10.dp, horizontal = 15.dp),
                    isSearchRequired = false,
                )
                if (ordersWithItems.isNotEmpty()) {
                    Text(
                        text = "Orders",
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(start = 15.dp, end = 15.dp, bottom = 15.dp)
                    )
                }
            }
        },
        floatingActionButton = {
            LemonNavigationBar(
                isActionEnabled = ordersWithItems.isNotEmpty(),
                onActionText = "Clear",
                onActionClicked = clearOrders,
                onHomeClicked = { navController.navigate(Screen.Home.route) },
                onReservationClicked = { navController.navigate(Screen.ReservationTableDetails.route) },
                onCartClicked = { navController.navigate(Screen.Cart.route) },
                onProfileClicked = { navController.navigate(Screen.Profile.route) },
                selectedRoute = currentRoute
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        modifier = Modifier.statusBarsPadding()
    ) { innerPadding ->
        if (ordersWithItems.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Image(
                    painter = painterResource(id = R.drawable.waiting_driver),
                    contentDescription = "Empty Cart",
                )

                Text(
                    text = "No orders placed yet",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 26.sp,
                    modifier = Modifier
                        .padding(vertical = 15.dp)
                )

                YellowLemonButton(
                    text = "Add an order now!",
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
                    },
                    modifier = Modifier.padding(horizontal = 15.dp)
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentPadding = PaddingValues(bottom = 90.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(ordersWithItems) { orderWithItems ->
                    LemonOrderCard(
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 15.dp),
                        ordersItem = orderWithItems.items,
                        paymentType = orderWithItems.order.paymentMethod,
                        totalPrice = "%.2f".format(orderWithItems.order.totalPrice),
                        orderId = orderWithItems.order.id
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun OrdersScreenPreview() {
    LittleLemonTheme {
        OrdersScreenContent(
            navController = NavController(LocalContext.current),
            ordersWithItems = listOf(
                LocalOrderWithItems(
                    order = LocalOrder(
                        id = 1,
                        totalPrice = 10.0,
                        paymentMethod = "Cash"
                    ),
                    items = listOf(
                        LocalOrderItem(
                            orderId = 1,
                            dishId = 1,
                            title = "Greek Salad",
                            price = 10.0,
                            image = "https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/images/greekSalad.jpg",
                            quantity = 1
                        ),
                        LocalOrderItem(
                            orderId = 1,
                            dishId = 1,
                            title = "Greek Salad",
                            price = 10.0,
                            image = "https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/images/greekSalad.jpg",
                            quantity = 1
                        ),
                        LocalOrderItem(
                            orderId = 1,
                            dishId = 1,
                            title = "Greek Salad",
                            price = 10.0,
                            image = "https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/images/greekSalad.jpg",
                            quantity = 1
                        ),
                    )
                )
            ),
            clearOrders = {}
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun OrdersScreenDarkPreview() {
    LittleLemonTheme {
        OrdersScreenContent(
            navController = NavController(LocalContext.current),
            ordersWithItems = listOf(
                LocalOrderWithItems(
                    order = LocalOrder(
                        id = 1,
                        totalPrice = 10.0,
                        paymentMethod = "Cash"
                    ),
                    items = listOf(
                        LocalOrderItem(
                            orderId = 1,
                            dishId = 1,
                            title = "Greek Salad",
                            price = 10.0,
                            image = "https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/images/greekSalad.jpg",
                            quantity = 1
                        ),
                        LocalOrderItem(
                            orderId = 1,
                            dishId = 1,
                            title = "Greek Salad",
                            price = 10.0,
                            image = "https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/images/greekSalad.jpg",
                            quantity = 1
                        ),
                        LocalOrderItem(
                            orderId = 1,
                            dishId = 1,
                            title = "Greek Salad",
                            price = 10.0,
                            image = "https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/images/greekSalad.jpg",
                            quantity = 1
                        ),
                    )
                )
            ),
            clearOrders = {}
        )
    }
}
