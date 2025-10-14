package com.example.littlelemon.ui.screens.reservation

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.littlelemon.data.repos.UserRepo
import com.example.littlelemon.ui.components.GreenLemonButton
import com.example.littlelemon.ui.components.InputField
import com.example.littlelemon.ui.components.LemonCutlerySelector
import com.example.littlelemon.ui.components.TopAppBar
import com.example.littlelemon.ui.components.YellowLemonButton
import com.example.littlelemon.ui.theme.LittleLemonTheme

@Composable
fun ReservationConfirmationScreen(
    modifier: Modifier = Modifier,
    vm: ReservationVm
) {
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .padding(
                        vertical = 10.dp,
                        horizontal = 15.dp
                    ),
                isSearchRequired = false
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LinearProgressIndicator(
                    progress = { 1f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 15.dp,
                            end = 15.dp,
                            top = 15.dp
                        ),
                    color = if (isSystemInDarkTheme()) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.primaryContainer
                    },
                    trackColor = MaterialTheme.colorScheme.tertiaryContainer,
                    strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(
                            15.dp
                        ),
                    horizontalArrangement = Arrangement.spacedBy(15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    GreenLemonButton(
                        text = "Edit",
                        modifier = Modifier
                            .weight(1f),
                        color = if (isSystemInDarkTheme()) {
                            MaterialTheme.colorScheme.onSecondary
                        } else {
                            MaterialTheme.colorScheme.secondaryContainer
                        },
                        textColor = if (isSystemInDarkTheme()) {
                            MaterialTheme.colorScheme.secondary
                        } else {
                            MaterialTheme.colorScheme.onSecondaryContainer
                        }
                    )
                    YellowLemonButton(
                        text = "Confirm",
                        modifier = Modifier.weight(1f),
                        color = if (isSystemInDarkTheme()) {
                            MaterialTheme.colorScheme.onPrimary
                        } else {
                            MaterialTheme.colorScheme.primaryContainer
                        },
                        textColor = if (isSystemInDarkTheme()) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onPrimaryContainer
                        }
                    )
                }
            }
        },
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
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(25.dp)
        ) {
            item {
                LemonCutlerySelector(
                    modifier = Modifier.padding(
                        horizontal = 15.dp,
                    )
                )
            }
            item {
                ReservationDetailsCard(
                    modifier = Modifier.padding(
                        horizontal = 15.dp,
                    ),
                    vm = vm
                )
            }
            item {
                AdditionalNotes(
                    modifier = Modifier.padding(
                        horizontal = 15.dp,
                    )
                )
            }
        }
    }
}

@Composable
fun ReservationDetailsCard(
    modifier: Modifier = Modifier,
    vm: ReservationVm
) {
    Card(
        modifier = modifier
            .height(450.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSystemInDarkTheme()) {
                MaterialTheme.colorScheme.onPrimary
            } else {
                MaterialTheme.colorScheme.primaryContainer
            }
        )
    ) {
        Column(
            modifier = Modifier.padding(15.dp)
        ) {
            Text(
                text = "Reservation Details".uppercase(),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 15.dp)
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    InputField(
                        requiredText = "Name"
                    )
                }
                item {
                    InputField(
                        requiredText = "Phone number"
                    )
                }
                item {
                    InputField(
                        requiredText = "At",
                        isReadOnly = true,
                        value = vm.selectedTime.value.toString(),
                    )
                }
                item {
                    InputField(
                        requiredText = "For"
                    )
                }
                item {
                    InputField(
                        requiredText = "Date",
                        isReadOnly = true,
                        value = vm.selectedDate.value.toString(),
                    )
                }
                item {
                    InputField(
                        requiredText = "Duration",
                    )
                }
                item {
                    InputField(
                        requiredText = "Courses"
                    )
                }
                item {
                    InputField(
                        requiredText = "Payment type"
                    )
                }
                item {
                    InputField(
                        requiredText = "Order ID"
                    )
                }
                item {
                    InputField(
                        requiredText = "Total"
                    )
                }
            }
        }
    }
}

@Composable
fun AdditionalNotes(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Additional Notes",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp)
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isSystemInDarkTheme()) {
                    MaterialTheme.colorScheme.onTertiary
                } else {
                    Color.White
                }
            ),
            border = if (isSystemInDarkTheme()) {
                BorderStroke(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.tertiaryContainer
                )
            } else {
                BorderStroke(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.secondaryContainer
                )
            }
        ) {
            TextField(
                value = "",
                onValueChange = {},
                modifier = Modifier
                    .fillMaxSize(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                keyboardActions = KeyboardActions(
                    onDone = {  }
                ),
            )
        }
    }
}

@Preview
@Composable
fun ReservationConfirmationScreenPreview() {
    LittleLemonTheme {
        ReservationConfirmationScreen(
            vm = ReservationVm()
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ReservationConfirmationScreenDarkPreview() {
    LittleLemonTheme {
        ReservationConfirmationScreen(
            vm = ReservationVm()
        )
    }
}