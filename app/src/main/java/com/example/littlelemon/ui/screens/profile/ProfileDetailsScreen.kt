package com.example.littlelemon.ui.screens.profile

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.littlelemon.R
import com.example.littlelemon.data.preferences.UserPreferences
import com.example.littlelemon.data.repos.UserRepo
import com.example.littlelemon.ui.components.LemonInputField
import com.example.littlelemon.ui.components.LemonNavigationBar
import com.example.littlelemon.ui.components.YellowLemonButton
import com.example.littlelemon.ui.theme.LittleLemonTheme
import com.example.littlelemon.ui.viewmodel.UserVm
import com.example.littlelemon.utils.Screen

@Composable
fun ProfileDetailsScreen(
    navController: NavController,
    vm: UserVm
) {
    val context = LocalContext.current

    var username by remember { mutableStateOf(vm.getUsername()) }
    var tempUsername: String? by remember { mutableStateOf(vm.getUsername()) }
    var firstName by remember { mutableStateOf(vm.getFirstName()) }
    var tempFirstName by remember { mutableStateOf(vm.getFirstName()) }
    var lastName by remember { mutableStateOf(vm.getLastName()) }
    var tempLastName by remember { mutableStateOf(vm.getLastName()) }
    val email = vm.getEmail()

    fun saveNewInfo() {
        vm.editUsername(tempUsername!!)
        vm.editFirstName(tempFirstName!!)
        vm.editLastName(tempLastName!!)
    }

    var showSaveDialog by remember { mutableStateOf(false) }

    var selectedImageUri by remember {
        mutableStateOf(vm.getProfilePicture()?.toUri())
    }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            selectedImageUri = uri
            vm.saveProfilePicture(uri.toString())
        }
    )

    if (showSaveDialog) {
        AlertDialog(
            onDismissRequest = {
                showSaveDialog = false
            },
            title = {
                Text(
                    text = "Save"
                )
            },
            text = {
                Text(
                    text = "Are you sure you want to save your new information?",
                )
            },
            confirmButton = {
                YellowLemonButton(
                    text = "Save",
                    onClick = {
                        saveNewInfo()
                        Toast.makeText(
                            context,
                            "Saved successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        saveNewInfo()
                        navController.navigate(Screen.Profile.route)
                        showSaveDialog = false
                    },
                    color = if (isSystemInDarkTheme()) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.primaryContainer
                    },
                    textColor = if (isSystemInDarkTheme()) {
                        MaterialTheme.colorScheme.onPrimary
                    } else {
                        MaterialTheme.colorScheme.onPrimaryContainer
                    },
                    modifier = Modifier.width(100.dp)
                )
            },
            dismissButton = {
                YellowLemonButton(
                    text = "Cancel",
                    onClick = {
                        showSaveDialog = false
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
                isActionEnabled = true,
                onActionText = "Save",
                onActionClicked = {
                    showSaveDialog = true
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
                .imePadding()
                .fillMaxSize()
                .padding(innerPadding),
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
                    horizontalAlignment = Alignment.Start,
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
                    }
                }
            }

            item {
                Text(
                    text = "Profile Information",
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 15.dp,
                            end = 15.dp,
                            bottom = 30.dp
                        )
                )
            }

            item {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    LemonInputField(
                        requiredText = "Username",
                        value = tempUsername ?: username.toString(),
                        onValueChange = { newUserName ->
                            tempUsername = newUserName
                        },
                        modifier = Modifier
                            .padding(
                                bottom = 20.dp,
                                start = 15.dp,
                                end = 15.dp
                            )
                    )

                    LemonInputField(
                        requiredText = "First Name",
                        value = tempFirstName ?: firstName.toString(),
                        onValueChange = { newFirstName ->
                            tempFirstName = newFirstName
                        },
                        modifier = Modifier
                            .padding(
                                bottom = 20.dp,
                                start = 15.dp,
                                end = 15.dp
                            )
                    )

                    LemonInputField(
                        requiredText = "Last Name",
                        value = tempLastName ?: lastName.toString(),
                        onValueChange = { newLastName ->
                            tempLastName = newLastName
                        },
                        modifier = Modifier
                            .padding(
                                bottom = 20.dp,
                                start = 15.dp,
                                end = 15.dp
                            )
                    )

                    LemonInputField(
                        requiredText = "Email",
                        value = email ?: "",
                        modifier = Modifier
                            .padding(
                                bottom = 20.dp,
                                start = 15.dp,
                                end = 15.dp
                            )
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ProfileDetailsScreenPreview() {
    LittleLemonTheme {
        ProfileDetailsScreen(
            navController = NavController(LocalContext.current),
            vm = UserVm(
                UserRepo(
                    UserPreferences(LocalContext.current)
                )
            )
        )
    }
}