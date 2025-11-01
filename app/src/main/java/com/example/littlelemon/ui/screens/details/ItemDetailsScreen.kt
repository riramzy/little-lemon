package com.example.littlelemon.ui.screens.details

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.outlined.RemoveCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.littlelemon.R
import com.example.littlelemon.data.local.menu.LocalMenuItem
import com.example.littlelemon.data.model.toCartItems
import com.example.littlelemon.di.AppContainer
import com.example.littlelemon.ui.components.LemonNavigationBar
import com.example.littlelemon.ui.components.LemonSnackbarHost
import com.example.littlelemon.ui.components.TopAppBar
import com.example.littlelemon.ui.screens.cart.CartVm
import com.example.littlelemon.ui.screens.cart.CartVmFactory
import com.example.littlelemon.ui.theme.LittleLemonTheme
import com.example.littlelemon.utils.Screen
import com.example.littlelemon.utils.dishesImagesMap
import kotlinx.coroutines.launch

@Composable
fun ItemDetailsScreen(
    itemId: Int,
    appContainer: AppContainer,
    navController: NavController
) {
    val detailsVm: DetailsVm = viewModel(factory = DetailsVmFactory(appContainer))
    val cartVm: CartVm = viewModel(factory = CartVmFactory(appContainer))

    val item by detailsVm.menuItem.collectAsState()
    val cartItems by cartVm.cartItems.collectAsState() // Or your state flow
    val existingCartItem = cartItems.firstOrNull { it.id == itemId }
    var itemQty by remember { mutableStateOf(1) }


    val navBackStackEntry by navController.currentBackStackEntryFlow.collectAsState(null)
    val currentRoute = navBackStackEntry?.destination?.route ?: Screen.Home.route

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(itemId) {
        detailsVm.loadItem(itemId)
    }

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
            LemonNavigationBar(
                isActionEnabled = true,
                onActionText = "Add",
                onActionClicked = {
                    val product = item!!

                    if (existingCartItem == null) {
                        // first time adding this item
                        cartVm.addToCart(
                            product.toCartItems().copy(quantity = itemQty)
                        )
                    } else {
                        // item already in cart → increase quantity
                        cartVm.updateQuantity(
                            product.id,
                            existingCartItem.quantity + itemQty
                        )
                    }

                    scope.launch {
                        val result = snackbarHostState.showSnackbar(
                            message = "${product.title} added ($itemQty)",
                            actionLabel = "Undo",
                            duration = SnackbarDuration.Short
                        )

                        if (result == SnackbarResult.ActionPerformed) {
                            if (existingCartItem == null) {
                                // Remove newly added item
                                cartVm.removeFromCart(product.id)
                            } else {
                                // Undo = revert added quantity
                                cartVm.updateQuantity(
                                    product.id,
                                    existingCartItem.quantity
                                )
                            }
                        }
                    }
                    itemQty
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
        snackbarHost = {
            LemonSnackbarHost(
                hostState = snackbarHostState,
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
                bottom = innerPadding.calculateBottomPadding() + 90.dp
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            item {
                ItemDetails(
                    modifier = Modifier
                        .padding(
                            horizontal = 15.dp,
                        ),
                    item = item
                )
            }
            item {
                DeliveryDetails(
                    modifier = Modifier
                        .padding(
                            horizontal = 15.dp,
                            vertical = 15.dp
                        )
                )
            }
            item {
                AddOnsSelection(
                    modifier = Modifier
                        .padding(
                            horizontal = 15.dp,
                        )
                )
            }
            item {
                ItemNumberPicker(
                    quantity = itemQty,
                    onIncrease = { itemQty++ },
                    onDecrease = { if (itemQty > 1) itemQty-- }
                )
            }
        }
    }
}

@Composable
fun ItemDetails(
    modifier: Modifier = Modifier,
    item: LocalMenuItem?
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(
            containerColor = if (isSystemInDarkTheme()) {
                MaterialTheme.colorScheme.onPrimary
            } else {
                MaterialTheme.colorScheme.primaryContainer
            },
            contentColor = if (isSystemInDarkTheme()) {
                MaterialTheme.colorScheme.onSecondaryContainer
            } else {
                MaterialTheme.colorScheme.onPrimaryContainer
            }
        ),
        shape = RoundedCornerShape(25.dp)
    ) {
        Image(
            painter = painterResource(id = dishesImagesMap[item?.id] ?: R.drawable.greek_salad),
            contentDescription = "Greek Salad",
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(15.dp)
                .clip(
                    RoundedCornerShape(25.dp)
                ),
            contentScale = ContentScale.Crop,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 15.dp,
                    start = 15.dp,
                    end = 15.dp,
                    bottom = 10.dp
                )
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = item?.title ?: "Greek Salad",
                style = MaterialTheme.typography.titleLarge,
                fontSize = 25.sp,
                color = if (isSystemInDarkTheme()) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onPrimaryContainer
                }
            )

            Text(
                text = item?.price.toString(),
                style = MaterialTheme.typography.titleLarge,
                fontSize = 25.sp,
                color = if (isSystemInDarkTheme()) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onPrimaryContainer
                }
            )
        }
        Text(
            text = item?.description ?: "The famous greek salad of crispy lettuce, peppers, olives and our",
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier
                .padding(
                    start = 15.dp,
                    end = 15.dp,
                    bottom = 15.dp
                ),
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            color = if (isSystemInDarkTheme()) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.onPrimaryContainer
            }
        )
    }
}

@Composable
fun DeliveryDetails(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                painter = painterResource(id = R.drawable.delivery_icon),
                contentDescription = "Delivery",
                modifier = Modifier.size(50.dp),
            )
            Text(
                text = "Delivery time:",
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 10.dp)
            )
            Text(
                text = "30 minutes",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 10.dp)
            )
        }
    }
}

@Composable
fun AddOnsSelection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Text(
            text = "Add Ons",
            style = MaterialTheme.typography.labelLarge,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Feta",
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 10.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "$1.00",
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(start = 10.dp)
                )
                RadioButton(
                    selected = false,
                    onClick = { /*TODO*/ }
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
                text = "Parmesan",
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 10.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "$1.00",
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(start = 10.dp)
                )
                RadioButton(
                    selected = false,
                    onClick = { /*TODO*/ }
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
                text = "Dressing",
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 10.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "$1.00",
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(start = 10.dp)
                )
                RadioButton(
                    selected = false,
                    onClick = { /*TODO*/ }
                )
            }
        }
    }
}

@Composable
fun ItemNumberPicker(
    modifier: Modifier = Modifier,
    quantity: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
) {
    Row(
        modifier = modifier
            .width(180.dp)
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = {
                onIncrease()
            }
        ) {
            Icon(
                imageVector = Icons.Default.AddCircle,
                contentDescription = "Add",
                modifier = Modifier.size(25.dp),
                tint = if (isSystemInDarkTheme()) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onPrimaryContainer
                }
            )
        }

        Text(
            text = quantity.toString(),
            style = MaterialTheme.typography.titleLarge,
            fontSize = 25.sp
        )

        IconButton(
            onClick = {
                onDecrease()
            }
        ) {
            Icon(
                imageVector = Icons.Outlined.RemoveCircle,
                contentDescription = "Minus",
                modifier = Modifier.size(25.dp),
                tint = if (isSystemInDarkTheme()) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.primary
                }
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun ItemDetailsScreenPreview() {
    LittleLemonTheme {
        ItemDetailsScreen(
            itemId = 1,
            appContainer = AppContainer(LocalContext.current),
            navController = NavController(LocalContext.current)
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ItemDetailsScreenDarkPreview() {
    LittleLemonTheme {
        ItemDetailsScreen(
            itemId = 1,
            appContainer = AppContainer(LocalContext.current),
            navController = NavController(LocalContext.current)
        )
    }
}