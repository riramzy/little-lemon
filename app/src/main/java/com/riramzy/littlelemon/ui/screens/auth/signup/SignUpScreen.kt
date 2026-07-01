package com.riramzy.littlelemon.ui.screens.auth.signup

import android.content.res.Configuration
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
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
fun SignUpScreen(
    navController: NavController,
    userVm: UserVm = hiltViewModel()
) {
    val state by userVm.userState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        userVm.resetState()
    }

    LaunchedEffect(state) {
        when (val currentState = state) {
            is UserState.Success -> {
                Toast.makeText(context, "Registration successful!", Toast.LENGTH_LONG).show()
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Register.route) { inclusive = true }
                }
            }

            is UserState.Error -> {
                Toast.makeText(context, currentState.message, Toast.LENGTH_LONG).show()
            }

            else -> {}
        }
    }

    SignUpScreenContent(
        state = state,
        register = userVm::register
    )
}

@Composable
fun SignUpScreenContent(
    state: UserState,
    register: (String, String, String, String, String) -> Unit,
) {
    var username by rememberSaveable { mutableStateOf("") }
    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    val isLoading = state is UserState.Loading

    Column(
        modifier = Modifier
            .imePadding()
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.primary
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
                colors = CardDefaults.cardColors(
                    if (isSystemInDarkTheme()) {
                        MaterialTheme.colorScheme.onPrimaryContainer.copy(0.3f)
                    } else {
                        MaterialTheme.colorScheme.surface.copy(0.5f)
                    }
                ),
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
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 20.dp),
                color = MaterialTheme.colorScheme.onPrimary
            )

            Text(
                text = "Register now!",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
            )
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(3f),
            colors = CardDefaults.cardColors(
                MaterialTheme.colorScheme.surfaceContainer
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
                    requiredText = "Username:",
                    value = username,
                    onValueChange = { username = it },
                    isReadOnly = isLoading,
                    isMultiline = false,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier
                        .padding(top = 40.dp)
                        .fillMaxWidth(),
                )

                LemonInputField(
                    requiredText = "First name:",
                    value = firstName,
                    onValueChange = { firstName = it },
                    isReadOnly = isLoading,
                    isMultiline = false,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier
                        .padding(top = 14.dp)
                        .fillMaxWidth(),
                )

                LemonInputField(
                    requiredText = "Last name:",
                    value = lastName,
                    onValueChange = { lastName = it },
                    isReadOnly = isLoading,
                    isMultiline = false,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier
                        .padding(top = 14.dp)
                        .fillMaxWidth(),
                )

                LemonInputField(
                    requiredText = "Email:",
                    value = email,
                    onValueChange = { email = it },
                    isReadOnly = isLoading,
                    isMultiline = false,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier
                        .padding(top = 14.dp)
                        .fillMaxWidth(),
                )

                LemonInputField(
                    requiredText = "Password:",
                    value = password,
                    onValueChange = { password = it },
                    isReadOnly = isLoading,
                    isPasswordField = true,
                    isMultiline = false,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier
                        .padding(top = 14.dp)
                        .fillMaxWidth(),
                )

                GreenLemonButton(
                    text = if (isLoading) "Registering..." else "Register",
                    modifier = Modifier
                        .padding(top = 50.dp)
                        .fillMaxWidth(),
                    enabled = !isLoading,
                    color = MaterialTheme.colorScheme.primary,
                    textColor = MaterialTheme.colorScheme.onPrimary,
                    onClick = {
                        register(username, firstName, lastName, email, password)
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
        SignUpScreenContent(
            state = UserState.Idle,
            register = { _, _, _, _, _ -> }
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SignUpScreenDarkPreview() {
    LittleLemonTheme {
        SignUpScreenContent(
            state = UserState.Idle,
            register = { _, _, _, _, _ -> }
        )
    }
}