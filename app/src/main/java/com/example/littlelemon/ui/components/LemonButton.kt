package com.example.littlelemon.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.littlelemon.ui.theme.LittleLemonTheme
import com.example.littlelemon.ui.theme.Typography

@Composable
fun YellowLemonButton(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primaryContainer,
    textColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    borderColor: Color = Color.Transparent,
    onClick: () -> Unit = {}
) {
    LemonButton(
        text = text,
        color = color,
        textColor = textColor,
        onClick = onClick,
        borderColor = borderColor,
        modifier = modifier
    )
}

@Composable
fun GreenLemonButton(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.secondaryContainer,
    textColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
    borderColor: Color = Color.Transparent,
    onClick: () -> Unit = {}
) {
    LemonButton(
        text = text,
        color = color,
        textColor = textColor,
        onClick = onClick,
        borderColor = borderColor,
        modifier = modifier
    )
}

@Composable
fun LemonButton(
    text: String,
    color: Color,
    textColor: Color,
    onClick: () -> Unit,
    borderColor: Color = Color.Transparent,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = {
            onClick()
        },
        enabled = true,
        modifier = modifier
            .width(355.dp)
            .wrapContentHeight(),
        colors = ButtonDefaults.buttonColors(
            color,
        ),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(
            2.dp,
            borderColor
        )
    ) {
        Text(
            text = text,
            style = Typography.titleSmall,
            color = textColor,
            modifier = Modifier.weight(0.3f),
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun LemonButtonPreview() {
    LittleLemonTheme {
        Column(
           verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            YellowLemonButton("Reserve a table", onClick = {})
            GreenLemonButton("Register" , onClick = {})
        }
    }
}