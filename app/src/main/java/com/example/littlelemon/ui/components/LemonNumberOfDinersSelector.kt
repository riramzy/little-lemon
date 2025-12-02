package com.example.littlelemon.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.littlelemon.ui.theme.LittleLemonTheme

@Composable
fun LemonNumberOfDinersSelector(
    modifier: Modifier = Modifier,
    numberOfDiners: String = "1",
    isSelected: Boolean = false,
    onClick: () -> Unit = {},
) {
    Card(
        modifier = modifier
            .size(70.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSystemInDarkTheme()) {
                if (isSelected) {
                    MaterialTheme.colorScheme.secondary
                } else {
                    MaterialTheme.colorScheme.background
                }
            } else {
                if (isSelected) {
                    MaterialTheme.colorScheme.secondaryContainer
                } else {
                    Color.White
                }
            },
            contentColor = if (isSystemInDarkTheme()) {
                if (isSelected) {
                    MaterialTheme.colorScheme.onPrimary
                } else {
                    MaterialTheme.colorScheme.secondary
                }
            } else {
                if (isSelected) {
                    Color.White
                } else {
                    MaterialTheme.colorScheme.secondaryContainer
                }
            }
        ),
        border = if (isSystemInDarkTheme()) {
            BorderStroke(
                width = 2.dp,
                color = MaterialTheme.colorScheme.secondary
            )
        } else {
            BorderStroke(
                width = 2.dp,
                color = MaterialTheme.colorScheme.secondaryContainer
            )
        },
        onClick = {
            onClick()
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = numberOfDiners,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

@Preview
@Composable
fun LemonNumberOfDinersSelectorPreview() {
    LittleLemonTheme {
        LemonNumberOfDinersSelector()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LemonNumberOfDinersSelectorDarkPreview() {
    LittleLemonTheme {
        LemonNumberOfDinersSelector()
    }
}