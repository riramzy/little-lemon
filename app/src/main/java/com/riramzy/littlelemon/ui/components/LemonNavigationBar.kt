package com.riramzy.littlelemon.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.riramzy.littlelemon.ui.theme.LittleLemonTheme
import com.riramzy.littlelemon.utils.Screen
import com.riramzy.littlelemon.utils.navIconsMap

@Composable
fun LemonNavigationBar(
    modifier: Modifier = Modifier,
    onHomeClicked: () -> Unit = {},
    onReservationClicked: () -> Unit = {},
    onCartClicked: () -> Unit = {},
    onProfileClicked: () -> Unit = {},
    selectedRoute: String,
    isActionEnabled: Boolean = false,
    onActionClicked: () -> Unit = {},
    onActionText: String = "Add"
) {
    Row(
        modifier = modifier
            .padding(6.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            modifier = Modifier
                .wrapContentSize(),
            shape = RoundedCornerShape(100.dp),
            colors = cardColors(
                containerColor = if (isSystemInDarkTheme()) {
                    MaterialTheme.colorScheme.onTertiary
                } else {
                    MaterialTheme.colorScheme.tertiaryContainer
                },
                contentColor = if (isSystemInDarkTheme()) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.primaryContainer
                }
            )
        ) {
            Row {
                val isHomeSelected = selectedRoute == Screen.Home.route
                IconButton(
                    onClick = {
                        onHomeClicked()
                    },
                    modifier = Modifier
                        .padding(8.dp)
                        .then(
                            if (isHomeSelected) {
                                Modifier
                                    .clip(CircleShape)
                                    .background(
                                        if (isSystemInDarkTheme()) {
                                            MaterialTheme.colorScheme.primary.copy(0.2f)
                                        } else {
                                            MaterialTheme.colorScheme.primaryContainer.copy(0.2f)
                                        }
                                    )
                            } else {
                                Modifier
                            }
                        ),
                ) {
                    Icon(
                        painter = painterResource(navIconsMap["home"]!!),
                        modifier = Modifier
                            .size(32.dp),
                        contentDescription = null
                    )
                }
                val isReservationSelected = selectedRoute == Screen.ReservationTableDetails.route
                IconButton(
                    onClick = {
                        onReservationClicked()
                    },
                    modifier = Modifier
                        .padding(8.dp)
                        .then(
                            if (isReservationSelected) {
                                Modifier
                                    .clip(CircleShape)
                                    .background(
                                        if (isSystemInDarkTheme()) {
                                            MaterialTheme.colorScheme.primary.copy(0.2f)
                                        } else {
                                            MaterialTheme.colorScheme.primaryContainer.copy(0.2f)
                                        }
                                    )
                            } else {
                                Modifier
                            }
                        ),
                ) {
                    Icon(
                        painter = painterResource(navIconsMap["reservation"]!!),
                        modifier = Modifier
                            .size(32.dp),
                        contentDescription = null
                    )
                }
                val isCartSelected = selectedRoute == Screen.Cart.route
                IconButton(
                    onClick = {
                        onCartClicked()
                    },
                    modifier = Modifier
                        .padding(8.dp)
                        .then(
                            if (isCartSelected) {
                                Modifier
                                    .clip(CircleShape)
                                    .background(
                                        if (isSystemInDarkTheme()) {
                                            MaterialTheme.colorScheme.primary.copy(0.2f)
                                        } else {
                                            MaterialTheme.colorScheme.primaryContainer.copy(0.2f)
                                        }
                                    )
                            } else {
                                Modifier
                            }
                        ),
                ) {
                    Icon(
                        painter = painterResource(navIconsMap["cart"]!!),
                        modifier = Modifier
                            .size(32.dp),
                        contentDescription = null
                    )
                }
                val isProfileSelected = selectedRoute == Screen.Profile.route
                IconButton(
                    onClick = {
                        onProfileClicked()
                    },
                    modifier = Modifier
                        .padding(8.dp)
                        .then(
                            if (isProfileSelected) {
                                Modifier
                                    .clip(CircleShape)
                                    .background(
                                        if (isSystemInDarkTheme()) {
                                            MaterialTheme.colorScheme.primary.copy(0.2f)
                                        } else {
                                            MaterialTheme.colorScheme.primaryContainer.copy(0.2f)
                                        }
                                    )
                            } else {
                                Modifier
                            }
                        ),
                ) {
                    Icon(
                        painter = painterResource(navIconsMap["profile"]!!),
                        modifier = Modifier
                            .size(32.dp),
                        contentDescription = null
                    )
                }
            }
        }
        if (isActionEnabled) {
            Button(
                modifier = Modifier
                    .height(64.dp)
                    .padding(start = 6.dp),
                    //.wrapContentWidth(),
                onClick = onActionClicked,
                shape = RoundedCornerShape(100.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSystemInDarkTheme()) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.primaryContainer
                    },
                    contentColor = if (isSystemInDarkTheme()) {
                        MaterialTheme.colorScheme.onTertiary
                    } else {
                        //MaterialTheme.colorScheme.tertiaryContainer
                        Color.White
                    }
                )
            ) {
                Text(
                    onActionText,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview
@Composable
fun LemonNavigationBarPreview() {
    LittleLemonTheme {
        LemonNavigationBar(
            selectedRoute = Screen.Home.route
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LemonNavigationBarDarkPreview() {
    LittleLemonTheme {
        LemonNavigationBar(
            selectedRoute = Screen.Home.route
        )
    }
}
