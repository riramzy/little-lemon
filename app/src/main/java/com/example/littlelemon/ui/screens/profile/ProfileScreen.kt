package com.example.littlelemon.ui.screens.profile

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.littlelemon.R
import com.example.littlelemon.data.preferences.UserPreferences
import com.example.littlelemon.data.repos.UserRepo
import com.example.littlelemon.ui.components.GreenLemonButton
import com.example.littlelemon.ui.components.LemonNavigationBar
import com.example.littlelemon.ui.components.SubInputField
import com.example.littlelemon.ui.theme.LittleLemonTheme
import com.example.littlelemon.ui.viewmodel.UserVm
import com.example.littlelemon.utils.Screen

@Composable
fun ProfileScreen(
    navController: NavController,
    vm: UserVm
) {
    val context = LocalContext.current

    val username = vm.getUsername()
    val firstName = vm.getFirstName()
    val lastName = vm.getLastName()
    val email = vm.getEmail()

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        if (isSystemInDarkTheme()) {
                            MaterialTheme.colorScheme.onSecondary
                        } else {
                            MaterialTheme.colorScheme.secondaryContainer
                        }
                    ),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profile_picture),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .padding(top = 15.dp)
                        .size(120.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                )

                Text(
                    text = "Profile",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(vertical = 15.dp),
                    color = Color.White
                )
            }
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
                selectedRoute = Screen.Profile.route
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(
                    if (isSystemInDarkTheme()) {
                        MaterialTheme.colorScheme.onSecondary
                    } else {
                        MaterialTheme.colorScheme.secondaryContainer
                    }
                )
        ) {
            Card(
                modifier = Modifier
                    .fillMaxSize(),
                shape = RoundedCornerShape(
                    topStart = 20.dp,
                    topEnd = 20.dp,
                    bottomEnd = 0.dp,
                    bottomStart = 0.dp
                ),
                colors = CardDefaults.cardColors(
                    if (isSystemInDarkTheme()) {
                        Color.Black
                    } else {
                        Color.White
                    }
                )
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 15.dp),
                    contentPadding = PaddingValues(
                        bottom = innerPadding.calculateBottomPadding() + 90.dp
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    item {
                        Card(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(horizontal = 15.dp),
                            colors = CardDefaults.cardColors(
                                if (isSystemInDarkTheme()) {
                                    Color.Black
                                } else {
                                    Color.White
                                }
                            ),
                        ) {
                            Text(
                                text = "Username",
                                style = MaterialTheme.typography.labelLarge,
                                modifier = Modifier
                                    .padding(top = 15.dp)
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Start,
                                color = if (isSystemInDarkTheme()) {
                                    Color.White
                                } else {
                                    Color.Black
                                }
                            )
                            SubInputField(
                                requiredText = username ?: "",
                                modifier = Modifier
                                    .padding(top = 14.dp)
                                    .fillMaxWidth(),
                            )
                        }
                    }
                    item {
                        Card(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(horizontal = 15.dp),
                            colors = CardDefaults.cardColors(
                                if (isSystemInDarkTheme()) {
                                    Color.Black
                                } else {
                                    Color.White
                                }
                            ),
                        ) {
                            Text(
                                text = "First name",
                                style = MaterialTheme.typography.labelLarge,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Start,
                                color = if (isSystemInDarkTheme()) {
                                    Color.White
                                } else {
                                    Color.Black
                                }
                            )
                            SubInputField(
                                requiredText = firstName ?: "",
                                modifier = Modifier
                                    .padding(top = 14.dp)
                                    .fillMaxWidth(),
                            )
                        }
                    }
                    item {
                        Card(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(horizontal = 15.dp),
                            colors = CardDefaults.cardColors(
                                if (isSystemInDarkTheme()) {
                                    Color.Black
                                } else {
                                    Color.White
                                }
                            ),
                        ) {
                            Text(
                                text = "Last name",
                                style = MaterialTheme.typography.labelLarge,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Start,
                                color = if (isSystemInDarkTheme()) {
                                    Color.White
                                } else {
                                    Color.Black
                                }
                            )
                            SubInputField(
                                requiredText = lastName ?: "",
                                modifier = Modifier
                                    .padding(top = 14.dp)
                                    .fillMaxWidth(),
                            )
                        }
                    }
                    item {
                        Card(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(horizontal = 15.dp),
                            colors = CardDefaults.cardColors(
                                if (isSystemInDarkTheme()) {
                                    Color.Black
                                } else {
                                    Color.White
                                }
                            ),
                        ) {
                            Text(
                                text = "Email",
                                style = MaterialTheme.typography.labelLarge,
                                modifier = Modifier
                                    .padding(top = 14.dp)
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Start,
                                color = if (isSystemInDarkTheme()) {
                                    Color.White
                                } else {
                                    Color.Black
                                }
                            )
                            SubInputField(
                                requiredText = email ?: "",
                                modifier = Modifier
                                    .padding(top = 14.dp)
                                    .fillMaxWidth(),
                            )
                        }
                    }
                    item {
                        GreenLemonButton(
                            text = "Log Out",
                            modifier = Modifier
                                .padding(horizontal = 15.dp)
                                .fillMaxWidth(),
                            color = if (isSystemInDarkTheme()) {
                                MaterialTheme.colorScheme.errorContainer
                            } else {
                                MaterialTheme.colorScheme.error
                            },
                            onClick = {
                                vm.logout()
                                Toast.makeText(
                                    context,
                                    "Logged out successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                                navController.navigate(Screen.Login.route)
                            }
                        )

                        GreenLemonButton(
                            text = "Delete account",
                            modifier = Modifier
                                .padding(horizontal = 15.dp)
                                .fillMaxWidth(),
                            color = if (isSystemInDarkTheme()) {
                                MaterialTheme.colorScheme.background
                            } else {
                                MaterialTheme.colorScheme.background
                            },
                            borderColor = if (isSystemInDarkTheme()) {
                                MaterialTheme.colorScheme.errorContainer
                            } else {
                                MaterialTheme.colorScheme.error
                            },
                            textColor = if (isSystemInDarkTheme()) {
                                MaterialTheme.colorScheme.errorContainer
                            } else {
                                MaterialTheme.colorScheme.error
                            },
                            onClick = {
                                vm.deleteAccount()
                                Toast.makeText(
                                    context,
                                    "Account deleted successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                                navController.navigate(Screen.Login.route)
                            }
                        )
                    }
                }
            }
        }
    }
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun ProfileScreenPreview() {
    LittleLemonTheme {
        ProfileScreen(
            navController = NavController(LocalContext.current),
            vm = UserVm(
                UserRepo(
                    UserPreferences(LocalContext.current)
                )
            )
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ProfileScreenDarkPreview() {
    LittleLemonTheme {
        ProfileScreen(
            navController = NavController(LocalContext.current),
            vm = UserVm(
                UserRepo(
                    UserPreferences(LocalContext.current)
                )
            )
        )
    }
}