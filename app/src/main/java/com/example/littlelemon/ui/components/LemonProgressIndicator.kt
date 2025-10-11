package com.example.littlelemon.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.littlelemon.ui.theme.LittleLemonTheme

@Composable
fun ProgressIndicator(
    progression: Int,
    color: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        when (progression) {
            1 -> {
                Pillar(color)
                Circle(color)
                Circle(color)
                Circle(color)
            }
            2 -> {
                Circle(color)
                Pillar(color)
                Circle(color)
                Circle(color)
            }
            3 -> {
                Circle(color)
                Circle(color)
                Pillar(color)
                Circle(color)
            }
            4 -> {
                Circle(color)
                Circle(color)
                Circle(color)
                Pillar(color)
            }
        }
    }
}

@Composable
fun Circle(color: Color) {
    Box(
        modifier = Modifier
            .padding(horizontal = 3.dp)
            .size(10.dp)
            .background(color.copy(0.5f), CircleShape)
    )
}

@Composable
fun Pillar(color: Color) {
    Box(
        modifier = Modifier
            .padding(horizontal = 3.dp)
            .height(10.dp)
            .width(60.dp)
            .background(color, CircleShape)
    )
}

@Preview
@Composable
fun ProgressIndicatorPreview() {
    LittleLemonTheme {
        ProgressIndicator(
            progression = 1,
            color = MaterialTheme.colorScheme.secondaryContainer
        )
    }
}
