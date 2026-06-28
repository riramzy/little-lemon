package com.riramzy.littlelemon.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.riramzy.littlelemon.R
import com.riramzy.littlelemon.ui.theme.LittleLemonTheme

@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    isSearchRequired: Boolean = true,
    searchQuery: String = "",
    onSearchQueryChange: (String) -> Unit = {},
    onSearch: (() -> Unit)? = null
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.little_lemon_logo),
            contentDescription = "Little Lemon Logo",
            contentScale = ContentScale.Fit,
            alignment = Alignment.CenterStart,
            modifier = Modifier
                .padding(vertical = 4.dp)
                .height(45.dp),
        )

        if (isSearchRequired) {
            SearchBar(
                searchQuery = searchQuery,
                onSearchQueryChange = onSearchQueryChange,
                onSearch = onSearch,
                modifier = Modifier
                    .padding(top = 15.dp)
            )
        }
    }
}

@Preview
@Composable
fun TopAppBarPreview() {
    LittleLemonTheme {
        Surface(
            color = MaterialTheme.colorScheme.surfaceContainer
        ) {
            TopAppBar(modifier = Modifier.padding(10.dp))
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = false)
@Composable
fun TopAppBarDarkPreview() {
    LittleLemonTheme {
        Surface(
            color = MaterialTheme.colorScheme.surfaceContainer
        ) {
            TopAppBar(modifier = Modifier.padding(10.dp))
        }
    }
}
