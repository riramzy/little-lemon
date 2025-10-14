package com.example.littlelemon.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.littlelemon.R
import com.example.littlelemon.ui.theme.LittleLemonTheme

@Composable
fun SearchBar() {
    var textValue by remember { mutableStateOf("") }
    var isClicked by remember { mutableStateOf(false) }
    val requiredText = "Search"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(100.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSystemInDarkTheme()) {
                MaterialTheme.colorScheme.onTertiary
            } else {
                MaterialTheme.colorScheme.tertiaryContainer
            },
        ),
        border = BorderStroke(
            0.5.dp,
            if (isSystemInDarkTheme()) {
                MaterialTheme.colorScheme.tertiaryContainer
            } else {
                MaterialTheme.colorScheme.secondaryContainer
            }
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Icon(
                painter = painterResource(R.drawable.search_icon),
                contentDescription = "Search Icon",
                modifier = Modifier
                    .padding(start = 8.dp)
                    .width(32.dp)
                    .height(32.dp)
                    .weight(1f),
                tint = if (isSystemInDarkTheme()) {
                    MaterialTheme.colorScheme.tertiaryContainer
                } else {
                    MaterialTheme.colorScheme.secondaryContainer
                }
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(6f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                BasicTextField(
                    value = textValue,
                    onValueChange = {
                        textValue = it
                    },
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                    ),
                    cursorBrush = SolidColor(
                        if (isSystemInDarkTheme()) Color.White else Color.Black
                    ),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier
                                .height(45.dp)
                                .fillMaxWidth()
                                .background(
                                    if (isSystemInDarkTheme()) MaterialTheme.colorScheme.onTertiary
                                    else MaterialTheme.colorScheme.tertiaryContainer,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 6.dp, vertical = 0.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (textValue.isEmpty()) {
                                Text(
                                    text = requiredText,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = if (isClicked) {
                                            if (isSystemInDarkTheme())
                                                Color.White
                                            else
                                                Color.Black
                                        } else {
                                            if (isSystemInDarkTheme())
                                                Color.White.copy(alpha = 0.5f)
                                            else
                                                Color.Black.copy(alpha = 0.5f)
                                        },
                                    )
                                )
                                isClicked = false
                            } else {
                                isClicked = true
                            }
                            innerTextField()
                        }
                    },
                    modifier = Modifier
                        .weight(6f)
                )
                if (textValue.isNotEmpty()) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Filter Icon",
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .width(24.dp)
                            .height(24.dp)
                            .weight(1f)
                            .clickable {
                                textValue = ""
                            },
                        tint = if (isSystemInDarkTheme()) {
                            MaterialTheme.colorScheme.tertiaryContainer
                        } else {
                            MaterialTheme.colorScheme.secondaryContainer
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun SearchBarPreview() {
    LittleLemonTheme {
        SearchBar()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SearchBarDarkPreview() {
    LittleLemonTheme {
        SearchBar()
    }
}