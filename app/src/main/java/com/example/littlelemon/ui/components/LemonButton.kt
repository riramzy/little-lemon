package com.example.littlelemon.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.littlelemon.R
import com.example.littlelemon.ui.theme.LittleLemonTheme
import com.example.littlelemon.ui.theme.Typography

@Composable
fun YellowLemonButton(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primaryContainer,
    textColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    borderColor: Color = Color.Transparent,
    imageInButton: Int? = null,
    onClick: () -> Unit = {}
) {
    LemonButton(
        text = text,
        color = color,
        textColor = textColor,
        onClick = onClick,
        borderColor = borderColor,
        imageInButton = imageInButton,
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
    imageInButton: Int? = null,
    onClick: () -> Unit = {}
) {
    LemonButton(
        text = text,
        color = color,
        textColor = textColor,
        onClick = onClick,
        borderColor = borderColor,
        imageInButton = imageInButton,
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
    imageInButton: Int? = null,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale = if (isPressed) 0.95f else 1f
    val containerColor = if (isPressed) lerp(color, Color.White, 0.2f) else color

    Button(
        onClick = {
            onClick()
        },
        enabled = true,
        modifier = modifier
            .width(355.dp)
            .wrapContentHeight()
            .scale(scale),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = textColor
        ),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(
            2.dp,
            borderColor
        ),
        interactionSource = interactionSource
    ) {
        if (imageInButton != null) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Image(
                    painter = painterResource(imageInButton),
                    contentDescription = text,
                    modifier = Modifier
                        .size(24.dp)
                )
                Text(
                    text = text,
                    style = Typography.titleSmall,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            Text(
                text = text,
                style = Typography.titleSmall,
                modifier = Modifier.weight(0.3f),
                textAlign = TextAlign.Center
            )
        }
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
            YellowLemonButton(
                "Continue with Google",
                color = Color.Blue,
                textColor = Color.White,
                onClick = {},
                imageInButton = R.drawable.google
            )
        }
    }
}
