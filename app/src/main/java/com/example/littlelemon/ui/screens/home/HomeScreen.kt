package com.example.littlelemon.ui.screens.home

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.littlelemon.R
import com.example.littlelemon.di.AppContainer
import com.example.littlelemon.ui.components.HeroCard
import com.example.littlelemon.ui.components.LemonNavigationBar
import com.example.littlelemon.ui.components.LemonSection
import com.example.littlelemon.ui.components.TopAppBar
import com.example.littlelemon.ui.theme.LittleLemonTheme
import com.example.littlelemon.utils.Screen

@Composable
fun HomeScreen(
    appContainer: AppContainer,
    navController: NavController,
) {
    val viewModel: HomeVm = viewModel(factory = HomeVmFactory(appContainer))
    val menuItems = viewModel.menuItems.collectAsState().value
    
    val navBackStackEntry by navController.currentBackStackEntryFlow.collectAsState(null)
    val currentRoute = navBackStackEntry?.destination?.route ?: Screen.Home.route

    val searchQuery by appContainer.searchVm.searchQuery.collectAsState()

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
                    appContainer.searchVm.onSearchQueryChange(it)
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
                bottom = innerPadding.calculateBottomPadding() + 90.dp
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
            item {
                LemonSection(
                    menuItems = menuItems,
                    title = "SHOP BY CATEGORY",
                    subTitle = "Find what you want quickly",
                    isCategory = true,
                    onItemClicked = {

                    }
                )
            }
            item {
                LemonSection(
                    menuItems = menuItems,
                    title = "FAMOUS DISHES",
                    subTitle = "What people order the most?",
                    modifier = Modifier.padding(top = 15.dp),
                    isCategory = false,
                    onItemClicked = { item ->
                        navController.navigate(Screen.Details.createRoute(item.id))
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    LittleLemonTheme {
        HomeScreen(
            appContainer = AppContainer(
                context = LocalContext.current
            ),
            navController = NavController(LocalContext.current)
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenDarkPreview() {
    LittleLemonTheme {
        HomeScreen(
            appContainer = AppContainer(
                context = LocalContext.current
            ),
            navController = NavController(LocalContext.current)
        )
    }
}

