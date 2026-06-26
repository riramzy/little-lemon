package com.riramzy.littlelemon.ui.screens.home

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.riramzy.littlelemon.R
import com.riramzy.littlelemon.data.local.menu.LocalMenuItem
import com.riramzy.littlelemon.ui.components.HeroCard
import com.riramzy.littlelemon.ui.components.LemonAboutUs
import com.riramzy.littlelemon.ui.components.LemonCategorySection
import com.riramzy.littlelemon.ui.components.LemonNavigationBar
import com.riramzy.littlelemon.ui.components.LemonSection
import com.riramzy.littlelemon.ui.components.LemonSpecialOffers
import com.riramzy.littlelemon.ui.components.TopAppBar
import com.riramzy.littlelemon.ui.theme.LittleLemonTheme
import com.riramzy.littlelemon.utils.Screen
import com.riramzy.littlelemon.utils.dishesImagesMap
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun HomeScreen(
    navController: NavController,
    homeVm: HomeVm = hiltViewModel()
) {
    val menuItems = homeVm.menuItems.collectAsStateWithLifecycle().value

    HomeScreenContent(
        navController = navController,
        menuItems = menuItems,
    )

}

@Composable
fun HomeScreenContent(
    navController: NavController,
    menuItems: List<LocalMenuItem>,
) {
    val navBackStackEntry by navController.currentBackStackEntryFlow.collectAsState(null)
    val currentRoute = navBackStackEntry?.destination?.route ?: Screen.Home.route
    var searchQuery by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .padding(
                        vertical = 10.dp,
                        horizontal = 15.dp
                    ),
                searchQuery = searchQuery,
                onSearchQueryChange = { searchQuery = it },
                onSearch = {
                    if (searchQuery.isNotBlank()) {
                        val encodedQuery =
                            URLEncoder.encode(searchQuery, StandardCharsets.UTF_8.toString())
                        navController.navigate(Screen.Search.createRoute(encodedQuery))
                    }
                },
                isSearchRequired = true,
            )
        },
        floatingActionButton = {
            LemonNavigationBar(
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
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier
            .statusBarsPadding()
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(
                bottom = innerPadding.calculateBottomPadding() + 100.dp
            )
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp)
                        .clearAndSetSemantics { contentDescription = "Welcome to Little Lemon!" },
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = "Welcome to Little Lemon!",
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                            fontFamily = MaterialTheme.typography.headlineLarge.fontFamily,
                            fontWeight = MaterialTheme.typography.headlineLarge.fontWeight,
                            drawStyle = Stroke(
                                width = 8f,
                            ),
                        ),
                        color = if (isSystemInDarkTheme()) {
                            MaterialTheme.colorScheme.onPrimaryContainer
                        } else {
                            MaterialTheme.colorScheme.secondaryContainer
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "Welcome to Little Lemon!",
                        style = MaterialTheme.typography.headlineLarge,
                        color = if (isSystemInDarkTheme()) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.primaryContainer
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            item {
                Text(
                    text = "Original Dishes from the Heart of Chicago",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp)
                )
            }

            item {
                HeroCard(
                    modifier = Modifier
                        .padding(horizontal = 15.dp, vertical = 15.dp),
                    headlineText = "Fresh Today!",
                    descriptionText = stringResource(R.string.hero_section_description),
                    buttonText = "Reserve a table",
                    heroImage = R.drawable.hero_image,
                    onReserveClicked = { navController.navigate(Screen.ReservationTableDetails.route) }
                )
            }

            item {
                LemonCategorySection(
                    menuItems = menuItems,
                    title = "Shop by Category",
                    subTitle = "Find what you want quickly",
                    isCategory = true,
                    onItemClicked = { item ->
                        navController.navigate(Screen.CategorySearch.createRoute(item.category))
                    },
                    modifier = Modifier.padding(
                        bottom = 20.dp,
                        end = 15.dp,
                        start = 15.dp
                    )
                )
            }

            item {
                LemonSection(
                    menuItems = menuItems,
                    title = "Famous Dishes",
                    subTitle = "What people order the most?",
                    modifier = Modifier.padding(bottom = 20.dp),
                    isCategory = false,
                    onItemClicked = { item ->
                        navController.navigate(Screen.Details.createRoute(item.id))
                    },
                )
            }

            item {
                Column(
                    modifier = Modifier.padding(top = 15.dp),
                ) {
                    Text(
                        text = "Special Offers",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.padding(
                            start = 15.dp,
                            bottom = 5.dp
                        )
                    )

                    Text(
                        text = "Don't miss out on our current promotions",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(
                            start = 15.dp,
                            bottom = 20.dp
                        )
                    )

                    LemonSpecialOffers(
                        icon = R.drawable.percentage,
                        iconColor = Color.Cyan,
                        title = "20% Off",
                        description = "First-time customers get 20% off their entire order",
                        modifier = Modifier.padding(horizontal = 15.dp, vertical = 10.dp)
                    )

                    LemonSpecialOffers(
                        icon = R.drawable.quick_icon,
                        iconColor = Color.Green,
                        title = "Happy Hour",
                        description = "Special prices on drinks and appetizers 3-6pm daily",
                        modifier = Modifier.padding(horizontal = 15.dp, vertical = 10.dp)
                    )

                    LemonSpecialOffers(
                        icon = R.drawable.gift,
                        iconColor = Color.Magenta,
                        title = "Family Deal",
                        description = "Order for 4 or more and get a free dessert platter",
                        modifier = Modifier.padding(horizontal = 15.dp, vertical = 10.dp)
                    )
                }
            }

            item {
                LemonAboutUs(
                    modifier = Modifier.padding(
                        horizontal = 20.dp,
                        vertical = 20.dp
                    )
                )
            }
        }
    }
}

@Preview(device = "id:pixel_9")
@Composable
fun HomeScreenPreview() {
    LittleLemonTheme {
        HomeScreenContent(
            navController = rememberNavController(),
            menuItems = listOf(
                LocalMenuItem(
                    id = 1,
                    title = "Greek Salad",
                    description = "The famous greek salad of crispy lettuce, peppers, olives, our Chicago...",
                    price = 12.99,
                    category = "starters",
                    image = dishesImagesMap[1].toString()
                )
            ),
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, device = "id:pixel_9")
@Composable
fun HomeScreenDarkPreview() {
    LittleLemonTheme {
        HomeScreenContent(
            navController = rememberNavController(),
            menuItems = listOf(
                LocalMenuItem(
                    id = 1,
                    title = "Greek Salad",
                    description = "The famous greek salad of crispy lettuce, peppers, olives, our Chicago...",
                    price = 12.99,
                    category = "starters",
                    image = dishesImagesMap[1].toString()
                )
            ),
        )
    }
}

