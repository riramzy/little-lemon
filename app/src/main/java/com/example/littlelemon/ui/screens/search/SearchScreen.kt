package com.example.littlelemon.ui.screens.search

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.littlelemon.di.AppContainer
import com.example.littlelemon.ui.components.ItemOverview
import com.example.littlelemon.ui.components.LemonGenrePill
import com.example.littlelemon.ui.components.LemonNavigationBar
import com.example.littlelemon.ui.components.TopAppBar
import com.example.littlelemon.ui.theme.LittleLemonTheme
import com.example.littlelemon.utils.Screen

@Composable
fun SearchScreen(
    navController: NavHostController,
    appContainer: AppContainer,
    category: String?
) {
    val categoriesFilters = listOf(
        "Starters",
        "Mains",
        "Desserts",
    )

    val vm = appContainer.searchVm
    val navBackStackEntry by navController.currentBackStackEntryFlow.collectAsState(null)
    val currentRoute = navBackStackEntry?.destination?.route ?: Screen.Home.route

    val searchQuery by vm.searchQuery.collectAsState()
    val searchItems by vm.searchedItems.collectAsState()
    val selectedCategory by vm.categoryFilter.collectAsState()

    LaunchedEffect(category) {
        if (!category.isNullOrBlank()) {
            vm.searchByCategory(category)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .padding(
                        vertical = 10.dp,
                        horizontal = 15.dp
                    ),
                searchQuery = searchQuery,
                onSearchQueryChange = vm::onSearchQueryChange
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
        Column(
            modifier = Modifier
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            LazyRow(
                modifier = Modifier
                    .padding(
                        start = 15.dp,
                        end = 15.dp,
                        top = 15.dp
                    )
                    .fillMaxWidth()
            ) {
                items(categoriesFilters) { filterCategory ->
                    val isSelected = filterCategory.equals(selectedCategory, ignoreCase = true)
                    LemonGenrePill(
                        genre = filterCategory,
                        isSelected = isSelected,
                        onGenreClicked = { vm.searchByCategory(filterCategory) },
                        modifier = Modifier.padding(end = 10.dp),
                    )
                }
            }
            LazyColumn(
                modifier = Modifier.padding(
                    start = 15.dp,
                    end = 15.dp,
                    top = 15.dp
                )
            ) {
                items(searchItems) { item ->
                    ItemOverview(
                        itemName = item.title,
                        itemDescription = item.description,
                        itemPrice = item.price,
                        itemId = item.id,
                        modifier = Modifier.padding(bottom = 15.dp),
                        onItemClick = {
                            navController.navigate(
                                Screen.Details.createRoute(item.id)
                            )
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun SearchScreenPreview() {
    LittleLemonTheme {
        SearchScreen(
            navController = rememberNavController(),
            appContainer = AppContainer(
                context = LocalContext.current
            ),
            category = "starters"
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SearchScreenDarkPreview() {
    LittleLemonTheme {
        SearchScreen(
            navController = rememberNavController(),
            appContainer = AppContainer(
                context = LocalContext.current
            ),
            category = null
        )
    }
}
