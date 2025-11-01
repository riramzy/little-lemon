package com.example.littlelemon.ui.screens.login

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.littlelemon.R
import com.example.littlelemon.data.local.LocalDatabase
import com.example.littlelemon.data.preferences.UserPreferences
import com.example.littlelemon.data.repos.UserRepo
import com.example.littlelemon.ui.components.GreenLemonButton
import com.example.littlelemon.ui.components.InputField
import com.example.littlelemon.ui.theme.LittleLemonTheme
import com.example.littlelemon.ui.viewmodel.UserVm
import com.example.littlelemon.utils.Screen

@Composable
fun LoginScreen(
    navController: NavController,
    vm: UserVm,
) {
    val context = LocalContext.current

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                if (isSystemInDarkTheme()) {
                    MaterialTheme.colorScheme.onSecondary
                } else {
                    MaterialTheme.colorScheme.secondaryContainer
                }
            ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .width(200.dp)
                    .height(60.dp),
                colors = CardDefaults.cardColors(Color.White),
                shape = RoundedCornerShape(100.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.little_lemon_logo_text),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .fillMaxSize()
                )
            }

            Text(
                text = "Little Lemon",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(top = 20.dp),
                color = Color.White
            )

            Text(
                text = "Welcome back!",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.8f)
            )
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(3f),
            colors = CardDefaults.cardColors(
                if (isSystemInDarkTheme()) {
                    Color.Black
                } else {
                    Color.White
                }
            ),
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)

        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                InputField(
                    requiredText = "Username:",
                    value = username,
                    onValueChange = { username = it },
                    modifier = Modifier
                        .padding(top = 40.dp)
                        .fillMaxWidth(),
                )

                InputField(
                    requiredText = "Password:",
                    value = password,
                    onValueChange = { password = it },
                    isPasswordField = true,
                    modifier = Modifier
                        .padding(top = 14.dp)
                        .fillMaxWidth(),
                )

                Text(
                    text = "Forgot password?",
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isSystemInDarkTheme()) {
                        Color.White.copy(alpha = 0.4f)
                    } else {
                        Color.Black.copy(alpha = 0.4f)
                    },
                    modifier = Modifier
                        .padding(top = 14.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.End
                )

                GreenLemonButton(
                    text = "Login",
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .fillMaxWidth(),
                    color = if (isSystemInDarkTheme()) {
                        MaterialTheme.colorScheme.onSecondary
                    } else {
                        MaterialTheme.colorScheme.secondaryContainer
                    },
                    onClick = {
                        when {
                            username.isBlank() || password.isBlank() -> {
                                Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                            }

                            vm.login(username, password) -> {
                                Toast.makeText(context, "Welcome ${vm.getFirstName() + " " + vm.getLastName()}", Toast.LENGTH_SHORT).show()
                                navController.navigate(Screen.Home.route)
                            }

                            else -> {
                                Toast.makeText(context, "Invalid username or password", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                )

                Text(
                    text = "Don't have an account? Sign Up",
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isSystemInDarkTheme()) {
                        Color.White.copy(alpha = 0.4f)
                    } else {
                        Color.Black.copy(alpha = 0.4f)
                    },
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(Screen.Register.route)
                        },
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun LoginScreenPreview() {
    LittleLemonTheme {
        LoginScreen(
            navController = NavController(LocalContext.current),
            vm = UserVm(
                UserRepo(
                    UserPreferences(LocalContext.current)
                )
            ),
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LoginScreenDarkPreview() {
    LittleLemonTheme {
        LoginScreen(
            navController = NavController(LocalContext.current),
            vm = UserVm(
                UserRepo(
                    UserPreferences(LocalContext.current)
                )
            ),
        )
    }
}