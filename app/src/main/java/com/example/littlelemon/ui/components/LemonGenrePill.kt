package com.example.littlelemon.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.littlelemon.ui.theme.LittleLemonTheme

@Composable
fun LemonGenrePill(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    genre: String = "Dessert",
    onGenreClicked: () -> Unit = {}
    ) {
    Card(
        modifier = modifier
            .width(75.dp)
            .height(39.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSystemInDarkTheme()) {
                if (isSelected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onTertiary
                }
            } else {
                if (isSelected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.tertiaryContainer
                }
            },
            contentColor = if (isSystemInDarkTheme()) {
                if (isSelected) {
                    MaterialTheme.colorScheme.onTertiary
                } else {
                    MaterialTheme.colorScheme.primary
                }
            } else {
                if (isSelected) {
                    Color.White
                } else {
                    MaterialTheme.colorScheme.primary
                }
            }
        ),
        onClick = {
            onGenreClicked()
        }
    ) {
        Text(
            text = genre,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun LemonGenrePillPreview() {
    LittleLemonTheme {
        LemonGenrePill()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LemonGenrePillDarkPreview() {
    LittleLemonTheme {
        LemonGenrePill()
    }
}