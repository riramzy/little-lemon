package com.example.littlelemon.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.littlelemon.R
import com.example.littlelemon.ui.theme.LittleLemonTheme

@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    isSearchRequired: Boolean = true,
    searchQuery: String = "",
    onSearchQueryChange: (String) -> Unit = {},
    onSearchBarClicked: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = if (isSystemInDarkTheme()) {
                    painterResource(R.drawable.little_lemon_logo)
                } else {
                    painterResource(R.drawable.little_lemon_logo)
                },
                contentDescription = "Little Lemon Logo",
                modifier = Modifier
                    .width(145.dp)
                    .height(32.dp)
                    .padding(start = 10.dp),
                contentScale = ContentScale.Fit,
                alignment = Alignment.CenterStart,
            )
            Row {
                IconButton(
                    onClick = { /*TODO*/ }
                ) {
                    Image(
                        painter = painterResource(R.drawable.notifications_icon),
                        contentDescription = "Notifications Icon",
                        modifier = Modifier.width(32.dp)
                            .height(32.dp),
                        colorFilter = if (isSystemInDarkTheme()) {
                            ColorFilter.tint(MaterialTheme.colorScheme.tertiaryContainer)
                        } else {
                            ColorFilter.tint(MaterialTheme.colorScheme.secondaryContainer)
                        }
                    )
                }
            }
        }
        if (isSearchRequired) {
            SearchBar(
                searchQuery = searchQuery,
                onSearchQueryChange = onSearchQueryChange,
                modifier = Modifier
                    .padding(top = 20.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        onSearchBarClicked()
                    }
            )
        }
    }
}

@Preview
@Composable
fun TopAppBarPreview() {
    LittleLemonTheme {
        TopAppBar()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TopAppBarDarkPreview() {
    LittleLemonTheme {
        TopAppBar()
    }
}
