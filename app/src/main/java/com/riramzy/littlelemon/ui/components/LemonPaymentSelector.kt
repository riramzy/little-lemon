package com.riramzy.littlelemon.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.riramzy.littlelemon.R
import com.riramzy.littlelemon.ui.theme.LittleLemonTheme

@Composable
fun LemonPaymentSelector(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    picture: Int,
    isSelected: Boolean = false,
    onClick: () -> Unit = {}
) {
    //var isPressed: Boolean by remember { mutableStateOf(false) }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            modifier = Modifier
                .width(70.dp)
                .height(35.dp)
                .padding(end = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isSystemInDarkTheme()) {
                    MaterialTheme.colorScheme.onTertiary
                } else {
                    MaterialTheme.colorScheme.tertiaryContainer
                },
                contentColor = if (isSystemInDarkTheme()) {
                    Color.White
                } else {
                    Color.Black
                }
            ),
            border = if (isSystemInDarkTheme()) {
                BorderStroke(
                    1.dp,
                    MaterialTheme.colorScheme.secondary
                )
            } else {
                BorderStroke(
                    1.dp,
                    MaterialTheme.colorScheme.secondaryContainer
                )
            },
            shape = RoundedCornerShape(16.dp),
            onClick = {
                onClick()
            }
        ) {
            Image(
                painter = painterResource(picture),
                contentDescription = "Logo",
                contentScale = ContentScale.Fit,
                alignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Row(
            modifier = Modifier
                .weight(3f)
                .clickable {
                    onClick()
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = title,
                    color = if (isSystemInDarkTheme()) {
                        Color.White
                    } else {
                        Color.Black
                    }
                )
                Text(
                    text = subtitle,
                    color = if (isSystemInDarkTheme()) {
                        Color.White
                    } else {
                        Color.Black
                    }
                )
            }
            if (isSelected) {
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    tint = Color.Blue,
                    contentDescription = "Check",
                )
            }
        }
    }
}

@Preview
@Composable
fun LemonPaymentSelectorPreview() {
    LittleLemonTheme {
        Column {
            LemonPaymentSelector(
                title = "Mastercard",
                subtitle = "So, we can charge you",
                picture = R.drawable.mastercard_logo
            )
            LemonPaymentSelector(
                title = "Visa",
                subtitle = "Yes, we can charge as well",
                picture = R.drawable.visa_logo
            )
        }

    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LemonPaymentSelectorDarkPreview() {
    LittleLemonTheme {
        Column {
            LemonPaymentSelector(
                title = "Mastercard",
                subtitle = "So, we can charge you",
                picture = R.drawable.mastercard_logo
            )
            LemonPaymentSelector(
                title = "Visa",
                subtitle = "Yes, we can charge as well",
                picture = R.drawable.visa_logo
            )
        }

    }
}