package com.riramzy.littlelemon.ui.screens.auth.login

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.riramzy.littlelemon.R
import com.riramzy.littlelemon.ui.components.GreenLemonButton
import com.riramzy.littlelemon.ui.components.LemonInputField
import com.riramzy.littlelemon.ui.screens.auth.UserState
import com.riramzy.littlelemon.ui.screens.auth.UserVm
import com.riramzy.littlelemon.ui.theme.LittleLemonTheme
import com.riramzy.littlelemon.utils.Screen

@Composable
fun LoginScreen(
    navController: NavController,
    userVm: UserVm = hiltViewModel(),
) {
    val context = LocalContext.current
    val state by userVm.userState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        userVm.resetState()
    }

    LaunchedEffect(state) {
        when (val currentState = state) {
            is UserState.Success -> {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
            }

            is UserState.Error -> {
                Toast.makeText(context, currentState.message, Toast.LENGTH_SHORT).show()
                userVm.resetState()
            }

            else -> {}
        }
    }

    LoginScreenContent(
        navController = navController,
        login = userVm::login,
        state = state,
    )
}

@Composable
fun LoginScreenContent(
    navController: NavController,
    login: (String, String) -> Unit,
    state: UserState,
) {
    val context = LocalContext.current

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    val isLoading = state is UserState.Loading

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
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LemonInputField(
                    requiredText = "Email:",
                    value = email,
                    onValueChange = { email = it },
                    isReadOnly = isLoading,
                    isMultiline = false,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier
                        .padding(top = 40.dp)
                        .fillMaxWidth(),
                )

                LemonInputField(
                    requiredText = "Password:",
                    value = password,
                    onValueChange = { password = it },
                    isPasswordField = true,
                    isReadOnly = isLoading,
                    isMultiline = false,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
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
                    text = if (isLoading) "Logging in..." else "Login",
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .fillMaxWidth(),
                    enabled = !isLoading,
                    onClick = {
                        if (!isLoading) {
                            if (email.isBlank() || password.isBlank()) {
                                Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                            } else {
                                login(email, password)
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
        LoginScreenContent(
            navController = NavController(LocalContext.current),
            login = { _, _ -> },
            state = UserState.Idle,
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LoginScreenDarkPreview() {
    LittleLemonTheme {
        LoginScreenContent(
            navController = NavController(LocalContext.current),
            login = { _, _ -> },
            state = UserState.Idle,
        )
    }
}