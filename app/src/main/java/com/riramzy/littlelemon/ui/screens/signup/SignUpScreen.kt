package com.riramzy.littlelemon.ui.screens.signup

import android.content.res.Configuration
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.riramzy.littlelemon.R
import com.riramzy.littlelemon.ui.components.GreenLemonButton
import com.riramzy.littlelemon.ui.components.LemonInputField
import com.riramzy.littlelemon.ui.theme.LittleLemonTheme
import com.riramzy.littlelemon.ui.viewmodel.UserVm
import com.riramzy.littlelemon.utils.Screen

@Composable
fun SignUpScreen(
    navController: NavController,
    userVm: UserVm = hiltViewModel()
) {
    val context = LocalContext.current

    var username by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .imePadding()
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
                text = "Register now!",
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
                LemonInputField(
                    requiredText = "Username:",
                    value = username,
                    onValueChange = { username = it },
                    modifier = Modifier
                        .padding(top = 40.dp)
                        .fillMaxWidth(),
                )

                LemonInputField(
                    requiredText = "First name:",
                    value = firstName,
                    onValueChange = { firstName = it },
                    modifier = Modifier
                        .padding(top = 14.dp)
                        .fillMaxWidth(),
                )

                LemonInputField(
                    requiredText = "Last name:",
                    value = lastName,
                    onValueChange = { lastName = it },
                    modifier = Modifier
                        .padding(top = 14.dp)
                        .fillMaxWidth(),
                )

                LemonInputField(
                    requiredText = "Password:",
                    value = password,
                    onValueChange = { password = it },
                    isPasswordField = true,
                    modifier = Modifier
                        .padding(top = 14.dp)
                        .fillMaxWidth(),
                )

                LemonInputField(
                    requiredText = "Email:",
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier
                        .padding(top = 14.dp)
                        .fillMaxWidth(),
                )

                GreenLemonButton(
                    text = "Register",
                    modifier = Modifier
                        .padding(top = 50.dp)
                        .fillMaxWidth(),
                    color = if (isSystemInDarkTheme()) {
                        MaterialTheme.colorScheme.onSecondary
                    } else {
                        MaterialTheme.colorScheme.secondaryContainer
                    },
                    onClick = {
                        val success = userVm.register(
                            username = username,
                            firstName = firstName,
                            lastName = lastName,
                            email = email,
                            password = password,
                        )

                        when {
                            username.isBlank() || firstName.isBlank() || lastName.isBlank() || email.isBlank() || password.isBlank() -> {
                                Toast.makeText(context, "Please fill all fields.", Toast.LENGTH_LONG).show()
                            }
                            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                                Toast.makeText(context, "Invalid email format.", Toast.LENGTH_LONG).show()
                            }
                            password.length < 8 -> {
                                Toast.makeText(context, "Password must be at least 8 characters.", Toast.LENGTH_LONG).show()
                            }
                            else -> {
                                if (success) {
                                    Toast.makeText(context, "Registration successful!", Toast.LENGTH_LONG).show()
                                    navController.navigate(Screen.Login.route)
                                } else {
                                    Toast.makeText(context, "Registration failed. Try again.", Toast.LENGTH_LONG).show()
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun SignUpScreenPreview() {
    LittleLemonTheme {
        SignUpScreen(
            navController = NavController(LocalContext.current),
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SignUpScreenDarkPreview() {
    LittleLemonTheme {
        SignUpScreen(
            navController = NavController(LocalContext.current),
        )
    }
}