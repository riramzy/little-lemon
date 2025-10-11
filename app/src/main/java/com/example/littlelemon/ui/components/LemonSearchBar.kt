package com.example.littlelemon.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.littlelemon.R
import com.example.littlelemon.ui.theme.LittleLemonTheme
import com.example.littlelemon.ui.theme.Typography

@Composable
fun SearchBar() {
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
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                painter = painterResource(R.drawable.search_icon),
                contentDescription = "Search Icon",
                modifier = Modifier
                    .padding(start = 8.dp)
                    .width(32.dp)
                    .height(32.dp),
                tint = if (isSystemInDarkTheme()) {
                    MaterialTheme.colorScheme.tertiaryContainer
                } else {
                    MaterialTheme.colorScheme.secondaryContainer
                }
            )
            TextField(
                value = "",
                onValueChange = { /*TODO*/ },
                placeholder = {
                    Text(
                        text = "Search",
                        style = Typography.bodyMedium,
                        color = if (isSystemInDarkTheme()) {
                            MaterialTheme.colorScheme.tertiaryContainer.copy(0.5f)
                        } else {
                            MaterialTheme.colorScheme.secondaryContainer.copy(0.5f)
                        }
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                )
            )
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