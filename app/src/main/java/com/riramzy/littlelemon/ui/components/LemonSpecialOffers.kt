package com.riramzy.littlelemon.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.riramzy.littlelemon.R
import com.riramzy.littlelemon.ui.theme.LittleLemonTheme

@Composable
fun LemonSpecialOffers(
    modifier: Modifier = Modifier,
    icon: Int = R.drawable.date,
    iconColor: Color = MaterialTheme.colorScheme.primary,
    title: String = "20% Off",
    description: String = "First-time customers get 20% off their entire order"
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Card(
            shape = MaterialTheme.shapes.large,
            border = BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onBackground,
            ),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background,
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .clip(shape = CircleShape)
                        .size(50.dp)
                        .background(
                            if (isSystemInDarkTheme()) {
                                iconColor.copy(0.2f)
                            } else {
                                iconColor.copy(0.1f)
                            }
                        ),
                ) {
                    Icon(
                        painter = painterResource(icon),
                        contentDescription = "Offer Icon",
                        modifier = Modifier
                            .size(25.dp),
                        tint = if (isSystemInDarkTheme()) {
                            iconColor
                        } else {
                            iconColor.copy(1000f)
                        }
                    )
                }

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 10.dp)
                )

                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}

@Preview
@Composable
fun LemonSpecialOffersPreview() {
    LittleLemonTheme {
        LemonSpecialOffers(
            icon = R.drawable.percentage,
            iconColor = Color.Cyan,
            title = "20% Off",
            description = "First-time customers get 20% off their entire order"
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LemonSpecialOffersDarkPreview() {
    LittleLemonTheme {
        LemonSpecialOffers(
            icon = R.drawable.percentage,
            iconColor = Color.Cyan,
            title = "20% Off",
            description = "First-time customers get 20% off their entire order"
        )
    }
}