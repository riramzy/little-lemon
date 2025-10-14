package com.example.littlelemon.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.littlelemon.ui.theme.LittleLemonTheme
import com.example.littlelemon.utils.Screen
import com.example.littlelemon.utils.navIconsMap

@Composable
fun LemonNavigationBar(
    modifier: Modifier = Modifier,
    onHomeClicked: () -> Unit = {},
    onReservationClicked: () -> Unit = {},
    onCartClicked: () -> Unit = {},
    onProfileClicked: () -> Unit = {},
    selectedRoute: String
) {
    Card(
        modifier = modifier
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
                MaterialTheme.colorScheme.primary
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
                                .background(MaterialTheme.colorScheme.primary.copy(0.2f))
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
                                .background(MaterialTheme.colorScheme.primary.copy(0.2f))
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
            val isCartSelected = selectedRoute == Screen.Details.route
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
                                .background(MaterialTheme.colorScheme.primary.copy(0.2f))
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
                                .background(MaterialTheme.colorScheme.primary.copy(0.2f))
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