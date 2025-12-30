package com.example.littlelemon.ui.screens.profile

import android.content.res.Configuration
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.littlelemon.R
import com.example.littlelemon.data.preferences.UserPreferences
import com.example.littlelemon.data.repos.UserRepo
import com.example.littlelemon.ui.components.LemonNavigationBar
import com.example.littlelemon.ui.components.YellowLemonButton
import com.example.littlelemon.ui.theme.LittleLemonTheme
import com.example.littlelemon.ui.viewmodel.UserVm
import com.example.littlelemon.utils.Screen
import org.jetbrains.annotations.Async

@Composable
fun ProfileScreen(
    navController: NavController,
    vm: UserVm
) {
    val context = LocalContext.current

    val username = vm.getUsername()
    val firstName = vm.getFirstName()
    val email = vm.getEmail()

    var showLogoutDialog by remember { mutableStateOf(false) }
    var showDeleteAccountDialog by remember { mutableStateOf(false) }

    var selectedImageUri by remember {
        mutableStateOf(vm.getProfilePicture()?.toUri())
    }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = {
                showLogoutDialog = false
            },
            title = {
                Text(
                    text = "Logout"
                )
            },
            text = {
                Text(
                    text = "Are you sure you want to logout?",
                )
            },
            confirmButton = {
                YellowLemonButton(
                    text = "Logout",
                    onClick = {
                        vm.logout()
                        Toast.makeText(
                            context,
                            "Logged out successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        navController.navigate(Screen.Login.route)
                        showLogoutDialog = false
                    },
                    color = if (isSystemInDarkTheme()) {
                        MaterialTheme.colorScheme.errorContainer
                    } else {
                        MaterialTheme.colorScheme.error
                    },
                    textColor = if (isSystemInDarkTheme()) {
                        MaterialTheme.colorScheme.onErrorContainer
                        } else {
                        MaterialTheme.colorScheme.onError
                    },
                    modifier = Modifier.width(100.dp)
                )
            },
            dismissButton = {
                YellowLemonButton(
                    text = "Cancel",
                    onClick = {
                        showLogoutDialog = false
                    },
                    color = if (isSystemInDarkTheme()) {
                        MaterialTheme.colorScheme.background
                    } else {
                        MaterialTheme.colorScheme.background
                    },
                    textColor = if (isSystemInDarkTheme()) {
                        MaterialTheme.colorScheme.onBackground
                        } else {
                        MaterialTheme.colorScheme.onBackground
                    },
                    modifier = Modifier.width(100.dp)
                )
            }
        )
    }

    if (showDeleteAccountDialog) {
        AlertDialog(
            onDismissRequest = {
                showDeleteAccountDialog = false
            },
            title = {
                Text(
                    text = "Delete account"
                )
            },
            text = {
                Text(
                    text = "Are you sure you want to delete your account??",
                )
            },
            confirmButton = {
                YellowLemonButton(
                    text = "Delete",
                    onClick = {
                        vm.deleteAccount()
                        Toast.makeText(
                            context,
                            "Account deleted successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        navController.navigate(Screen.Login.route)
                        showDeleteAccountDialog = false
                    },
                    color = if (isSystemInDarkTheme()) {
                        MaterialTheme.colorScheme.errorContainer
                    } else {
                        MaterialTheme.colorScheme.error
                    },
                    textColor = if (isSystemInDarkTheme()) {
                        MaterialTheme.colorScheme.onErrorContainer
                    } else {
                        MaterialTheme.colorScheme.onError
                    },
                    modifier = Modifier.width(100.dp)
                )
            },
            dismissButton = {
                YellowLemonButton(
                    text = "Cancel",
                    onClick = {
                        showDeleteAccountDialog = false
                    },
                    color = if (isSystemInDarkTheme()) {
                        MaterialTheme.colorScheme.background
                    } else {
                        MaterialTheme.colorScheme.background
                    },
                    textColor = if (isSystemInDarkTheme()) {
                        MaterialTheme.colorScheme.onBackground
                    } else {
                        MaterialTheme.colorScheme.onBackground
                    },
                    modifier = Modifier.width(100.dp)
                )
            }
        )
    }

    Scaffold(
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
        containerColor = if (isSystemInDarkTheme()) {
            MaterialTheme.colorScheme.background
        } else {
            Color.White
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(
                bottom = innerPadding.calculateBottomPadding() + 90.dp
            ),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            item {
                Column(
                    modifier = Modifier
                        .padding(
                            start = 15.dp,
                            end = 15.dp,
                            top = 40.dp,
                            bottom = 40.dp
                        )
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 15.dp)
                            .size(100.dp)
                    ) {
                        AsyncImage(
                            model = selectedImageUri,
                            contentDescription = "Profile Picture",
                            placeholder = painterResource(R.drawable.profile_picture),
                            error = painterResource(R.drawable.profile_picture),
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop,
                        )

                        /*
                        IconButton(
                            onClick = {
                                photoPickerLauncher.launch(
                                    PickVisualMediaRequest(
                                        ActivityResultContracts.PickVisualMedia.ImageOnly
                                    )
                                )
                            },
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .clip(CircleShape)
                                .size(24.dp)
                                .background(
                                    if (isSystemInDarkTheme()) {
                                        MaterialTheme.colorScheme.primary
                                    } else {
                                        MaterialTheme.colorScheme.primaryContainer
                                    }
                                )

                        ) {
                            Icon(
                                Icons.Filled.Edit,
                                modifier = Modifier
                                    .size(24.dp)
                                    .padding(4.dp),
                                contentDescription = "Edit",
                                tint = if (isSystemInDarkTheme()) {
                                    Color.Black
                                } else {
                                    Color.White
                                },
                            )
                        }

                         */
                    }

                    Text(
                        text = firstName?.replaceFirstChar {
                            it.uppercase()
                        } ?: "Ramzy",
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 26.sp,
                        modifier = Modifier.padding(
                            bottom = 10.dp,
                            top = 10.dp
                        )
                    )
                    Text(
                        text = username ?: "riramzy",
                        style = MaterialTheme.typography.titleSmall,
                        color = if (isSystemInDarkTheme()) {
                            Color.White.copy(0.5f)
                        } else {
                            Color.Black.copy(0.5f)
                        }

                    )
                    Text(
                        text = email ?: "james.iredell@examplepetstore.com",
                        style = MaterialTheme.typography.titleSmall,
                        color = if (isSystemInDarkTheme()) {
                            Color.White.copy(0.5f)
                        } else {
                            Color.Black.copy(0.5f)
                        }
                    )
                }
            }

            item {
                Box(
                    modifier = Modifier
                        .padding(
                            bottom = 10.dp,
                            start = 10.dp,
                            end = 10.dp
                        )
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    SectionHead(
                        title = "Profile",
                        icon = Icons.Default.Person,
                        onClick = {
                            navController.navigate(Screen.ProfileDetails.route)
                        }
                    )
                }
            }
            item {
                Box(
                    modifier = Modifier
                        .padding(
                            bottom = 10.dp,
                            start = 10.dp,
                            end = 10.dp
                        )
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    SectionHead(
                        title = "Shipping Address",
                        icon = Icons.Default.LocationOn,
                    )
                }
            }
            item {
                Box(
                    modifier = Modifier
                        .padding(
                            bottom = 10.dp,
                            start = 10.dp,
                            end = 10.dp
                        )
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    SectionHead(
                        title = "Orders",
                        icon = Icons.Default.History,
                        onClick = {
                            navController.navigate(Screen.Orders.route)
                        }
                    )
                }
            }
            item {
                Box(
                    modifier = Modifier
                        .padding(
                            bottom = 10.dp,
                            start = 10.dp,
                            end = 10.dp
                        )
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    SectionHead(
                        title = "Reservations",
                        icon = Icons.Default.Restaurant,
                        modifier = Modifier,
                        onClick = {
                            navController.navigate(Screen.Reservations.route)
                        }
                    )
                }
            }
            item {
                Box(
                    modifier = Modifier
                        .padding(
                            bottom = 10.dp,
                            start = 10.dp,
                            end = 10.dp
                        )
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    SectionHead(
                        title = "Privacy Policy",
                        icon = Icons.Default.Lock,
                    )
                }
            }
            item {
                Box(
                    modifier = Modifier
                        .padding(
                            bottom = 10.dp,
                            start = 10.dp,
                            end = 10.dp
                        )
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    SectionHead(
                        title = "Settings",
                        icon = Icons.Default.Settings,
                    )
                }
            }
            item {
                Box(
                    modifier = Modifier
                        .padding(
                            bottom = 10.dp,
                            start = 10.dp,
                            end = 10.dp
                        )
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    SectionHead(
                        title = "Logout",
                        icon = Icons.AutoMirrored.Filled.Login,
                        onClick = {
                            showLogoutDialog = true
                        }
                    )
                }
            }
            item {
                Box(
                    modifier = Modifier
                        .padding(
                            bottom = 10.dp,
                            start = 10.dp,
                            end = 10.dp
                        )
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    SectionHead(
                        title = "Delete Account",
                        icon = Icons.Default.DeleteForever,
                        onClick = {
                            showDeleteAccountDialog = true
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SectionHead(
    modifier: Modifier = Modifier,
    title: String,
    icon: ImageVector,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .width(300.dp)
            .height(50.dp),
        colors = CardDefaults.cardColors(
            if (isSystemInDarkTheme()) {
                Color.Black
            } else {
                MaterialTheme.colorScheme.tertiaryContainer
            }
        ),
        shape = CircleShape,
        onClick = {
            onClick()
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                color = if (isSystemInDarkTheme()) {
                    Color.White
                } else {
                    Color.Black
                }
            )
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 10.dp)
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(
                        if (isSystemInDarkTheme()) {
                            MaterialTheme.colorScheme.primary.copy(0.2f)
                        } else {
                            MaterialTheme.colorScheme.primaryContainer.copy(0.2f)
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(24.dp),
                    tint = if (isSystemInDarkTheme()) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.primaryContainer
                    }
                )
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