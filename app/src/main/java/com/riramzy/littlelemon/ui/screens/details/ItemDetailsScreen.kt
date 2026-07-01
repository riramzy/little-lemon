package com.riramzy.littlelemon.ui.screens.details

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.riramzy.littlelemon.R
import com.riramzy.littlelemon.data.local.cart.LocalCartItem
import com.riramzy.littlelemon.data.local.menu.LocalMenuItem
import com.riramzy.littlelemon.ui.components.LemonNavigationBar
import com.riramzy.littlelemon.ui.components.LemonSnackbarHost
import com.riramzy.littlelemon.ui.components.TopAppBar
import com.riramzy.littlelemon.ui.screens.cart.CartVm
import com.riramzy.littlelemon.ui.theme.LittleLemonTheme
import com.riramzy.littlelemon.utils.Screen
import com.riramzy.littlelemon.utils.dishesImagesMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ItemDetailsScreen(
    itemId: Int,
    navController: NavController,
    detailsVm: DetailsVm = hiltViewModel(),
    cartVm: CartVm = hiltViewModel()
) {
    val item by detailsVm.menuItem.collectAsStateWithLifecycle()
    val cartItems by cartVm.cartItems.collectAsStateWithLifecycle()
    val existingCartItem = cartItems.firstOrNull { it.id == itemId }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(itemId) {
        detailsVm.loadItem(itemId)
    }

    ItemDetailsScreenContent(
        navController = navController,
        item = item,
        existingCartItem = existingCartItem,
        snackbarHostState = snackbarHostState,
        scope = scope,
        addToCart = cartVm::addToCart,
        removeFromCart = cartVm::removeFromCart,
    )
}

@Composable
fun ItemDetailsScreenContent(
    navController: NavController,
    item: LocalMenuItem?,
    existingCartItem: LocalCartItem?,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope,
    addToCart: (LocalCartItem) -> Unit,
    removeFromCart: (Int) -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryFlow.collectAsStateWithLifecycle(
        null
    )
    val currentRoute = navBackStackEntry?.destination?.route ?: Screen.Home.route

    var itemQty by remember { mutableIntStateOf(1) }
    val selectedAddOns = remember { mutableStateListOf<Pair<String, Double>>() }

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
                isActionEnabled = (item != null),
                onActionText = "Add",
                onActionClicked = {
                    item?.let { product ->
                        val addOnsPrice = selectedAddOns.sumOf { it.second }
                        val finalPrice = product.price + addOnsPrice
                        val addOnsString =
                            selectedAddOns.joinToString(separator = ", ") { it.first }
                        val finalQty =
                            if (existingCartItem == null) itemQty else existingCartItem.quantity + itemQty

                        addToCart(
                            LocalCartItem(
                                id = product.id,
                                title = product.title,
                                price = finalPrice,
                                image = product.image,
                                quantity = finalQty,
                                selectedAddOns = addOnsString
                            )
                        )
                        scope.launch {
                            val result = snackbarHostState.showSnackbar(
                                message = "${product.title} added ($itemQty)",
                                actionLabel = "Undo",
                                duration = SnackbarDuration.Short
                            )

                            if (result == SnackbarResult.ActionPerformed) {
                                if (existingCartItem == null) {
                                    removeFromCart(product.id)
                                } else {
                                    addToCart(existingCartItem)
                                }
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
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
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
                        ),
                    addOns = listOf(
                        Pair("Feta", 1.00),
                        Pair("Parmesan", 1.00),
                        Pair("Dressing", 1.00)
                    ),
                    selectedAddOns = selectedAddOns,
                    onToggleAddOn = { addOn ->
                        val index = selectedAddOns.indexOfFirst { it.first == addOn.first }
                        if (index != -1) {
                            selectedAddOns.removeAt(index)
                        } else {
                            selectedAddOns.add(addOn)
                        }
                    }
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
            containerColor = MaterialTheme.colorScheme.primary,
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
                text = item?.title ?: "",
                style = MaterialTheme.typography.titleLarge,
                fontSize = 25.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )

            Text(
                text = item?.let { "$${String.format("%.2f", it.price)}" } ?: "",
                style = MaterialTheme.typography.titleLarge,
                fontSize = 25.sp,
                color = MaterialTheme.colorScheme.onPrimary
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
            color = MaterialTheme.colorScheme.onPrimary
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
            Box(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.delivery_icon),
                    contentDescription = "Delivery",
                    modifier = Modifier
                        .padding(6.dp)
                        .size(35.dp),
                    tint = MaterialTheme.colorScheme.surfaceContainer
                )
            }

            Text(
                text = "Delivery time:",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(start = 10.dp)
            )

            Text(
                text = "30 minutes",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(start = 10.dp)
            )
        }
    }
}

@Composable
fun AddOnsSelection(
    modifier: Modifier = Modifier,
    addOns: List<Pair<String, Double>> = emptyList(),
    selectedAddOns: List<Pair<String, Double>> = emptyList(),
    onToggleAddOn: (Pair<String, Double>) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Text(
            text = "Add Ons",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )

        addOns.forEach { addOn ->
            val isSelected = selectedAddOns.any { it.first == addOn.first }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onToggleAddOn(addOn) }
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = addOn.first,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(start = 10.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "$${"%.2f".format(addOn.second)}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(start = 10.dp)
                    )

                    RadioButton(
                        selected = isSelected,
                        onClick = { onToggleAddOn(addOn) },
                        colors = RadioButtonDefaults.colors(MaterialTheme.colorScheme.onSurface),
                    )
                }
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
                tint = MaterialTheme.colorScheme.primary,
            )
        }

        Text(
            text = quantity.toString(),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
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
                tint = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun ItemDetailsScreenPreview() {
    LittleLemonTheme {
        ItemDetailsScreenContent(
            navController = NavController(LocalContext.current),
            item = LocalMenuItem(
                id = 1,
                title = "Greek Salad",
                description = "The famous greek salad of crispy lettuce, peppers, olives and our",
                price = 10.0,
                image = "R.drawable.lemon_dessert",
                category = "Mains"
            ),
            existingCartItem = LocalCartItem(
                id = 2,
                title = "Greek Salad",
                price = 10.0,
                image = "R.drawable.greek_salad",
                quantity = 1
            ),
            snackbarHostState = remember { SnackbarHostState() },
            scope = rememberCoroutineScope(),
            addToCart = { },
            removeFromCart = { }
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ItemDetailsScreenDarkPreview() {
    LittleLemonTheme {
        ItemDetailsScreenContent(
            navController = NavController(LocalContext.current),
            item = LocalMenuItem(
                id = 1,
                title = "Greek Salad",
                description = "The famous greek salad of crispy lettuce, peppers, olives and our",
                price = 10.0,
                image = "R.drawable.lemon_dessert",
                category = "Mains"
            ),
            existingCartItem = LocalCartItem(
                id = 2,
                title = "Greek Salad",
                price = 10.0,
                image = "R.drawable.greek_salad",
                quantity = 1
            ),
            snackbarHostState = remember { SnackbarHostState() },
            scope = rememberCoroutineScope(),
            addToCart = { },
            removeFromCart = { }
        )
    }
}