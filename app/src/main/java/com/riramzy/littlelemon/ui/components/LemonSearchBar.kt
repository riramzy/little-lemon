package com.riramzy.littlelemon.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.riramzy.littlelemon.R
import com.riramzy.littlelemon.ui.theme.LittleLemonTheme

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    searchQuery: String = "",
    onSearchQueryChange: (String) -> Unit = {},
) {
    val requiredText = "Search"
    val focusManager = LocalFocusManager.current

    Card(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 50.dp),
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
                MaterialTheme.colorScheme.tertiary
            } else {
                MaterialTheme.colorScheme.onTertiaryContainer
            }
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 50.dp),
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
                tint = MaterialTheme.colorScheme.primary
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(6f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                BasicTextField(
                    value = searchQuery,
                    onValueChange = {
                        onSearchQueryChange(it)
                    },
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        color = if (isSystemInDarkTheme()) {
                            MaterialTheme.colorScheme.tertiary
                        } else {
                            MaterialTheme.colorScheme.onTertiaryContainer
                        }
                    ),
                    cursorBrush = SolidColor(
                        if (isSystemInDarkTheme()) {
                            MaterialTheme.colorScheme.tertiary
                        } else {
                            MaterialTheme.colorScheme.onTertiaryContainer
                        }
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() }),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier
                                .height(45.dp)
                                .fillMaxWidth()
                                .padding(horizontal = 6.dp, vertical = 0.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (searchQuery.isEmpty()) {
                                Text(
                                    text = requiredText,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = if (isSystemInDarkTheme()) {
                                            MaterialTheme.colorScheme.tertiary
                                        } else {
                                            MaterialTheme.colorScheme.onTertiaryContainer
                                        }
                                    )
                                )
                            }
                            innerTextField()
                        }
                    },
                    modifier = Modifier
                        .weight(6f)
                )

                if (searchQuery.isNotEmpty()) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear Search Query",
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .width(24.dp)
                            .height(24.dp)
                            .weight(1f)
                            .clickable {
                                onSearchQueryChange("")
                            },
                        tint = MaterialTheme.colorScheme.secondaryContainer
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
        Box(
            modifier = Modifier
                .background(
                    color = Color.White
                )
        ) {
            SearchBar(modifier = Modifier.padding(15.dp))
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, backgroundColor = 0xFF000000)
@Composable
fun SearchBarDarkPreview() {
    LittleLemonTheme {
        Box(
            modifier = Modifier
                .background(
                    color = Color.Black
                )
        ) {
            SearchBar(modifier = Modifier.padding(15.dp))
        }
    }
}