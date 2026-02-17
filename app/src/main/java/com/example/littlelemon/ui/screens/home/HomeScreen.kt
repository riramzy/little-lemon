package com.example.littlelemon.ui.screens.home

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.littlelemon.R
import com.example.littlelemon.data.local.menu.LocalMenuItem
import com.example.littlelemon.ui.components.HeroCard
import com.example.littlelemon.ui.components.LemonAboutUs
import com.example.littlelemon.ui.components.LemonCategorySection
import com.example.littlelemon.ui.components.LemonNavigationBar
import com.example.littlelemon.ui.components.LemonSection
import com.example.littlelemon.ui.components.LemonSpecialOffers
import com.example.littlelemon.ui.components.TopAppBar
import com.example.littlelemon.ui.screens.search.SearchVm
import com.example.littlelemon.ui.theme.LittleLemonTheme
import com.example.littlelemon.utils.Screen

@Composable
fun HomeScreen(
    navController: NavController,
    homeVm: HomeVm = hiltViewModel(),
    searchVm: SearchVm = hiltViewModel()
) {
    val menuItems = homeVm.menuItems.collectAsState().value
    val searchQuery by searchVm.searchQuery.collectAsState()

    HomeScreenContent(
        navController = navController,
        menuItems = menuItems,
        searchQuery = searchQuery,
        onSearchQueryChange = searchVm::onSearchQueryChange
    )

}

@Composable
fun HomeScreenContent(
    navController: NavController,
    menuItems: List<LocalMenuItem>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
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
                searchQuery = searchQuery,
                onSearchQueryChange = {
                    onSearchQueryChange(it)
                },
                onSearchBarClicked = {
                    navController.navigate(Screen.Search.route)
                }
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
                        .padding(horizontal = 15.dp),
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
                                width = 5f,
                            ),
                        ),
                        color = if (isSystemInDarkTheme()) {
                            MaterialTheme.colorScheme.tertiaryContainer
                        } else {
                            MaterialTheme.colorScheme.secondaryContainer
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "Welcome to Little Lemon!",
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.primaryContainer,
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

            //Categories Section
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
                        end = 15.dp
                    )
                )
            }

            //Famous Dishes Section
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

            //Special Offers Section
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
                        description = "First-time customers get 20% off their entire order"
                    )

                    LemonSpecialOffers(
                        icon = R.drawable.quick_icon,
                        iconColor = Color.Green,
                        title = "Happy Hour",
                        description = "Special prices on drinks and appetizers 3-6pm daily"
                    )

                    LemonSpecialOffers(
                        icon = R.drawable.gift,
                        iconColor = Color.Magenta,
                        title = "Family Deal",
                        description = "Order for 4 or more and get a free dessert platter"
                    )
                }
            }

            //About Us
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

@Preview
@Composable
fun HomeScreenPreview() {
    LittleLemonTheme {
        HomeScreenContent(
            navController = rememberNavController(),
            menuItems = emptyList(),
            searchQuery = "",
            onSearchQueryChange = {}
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenDarkPreview() {
    LittleLemonTheme {
        HomeScreenContent(
            navController = rememberNavController(),
            menuItems = emptyList(),
            searchQuery = "",
            onSearchQueryChange = {}
        )
    }
}

