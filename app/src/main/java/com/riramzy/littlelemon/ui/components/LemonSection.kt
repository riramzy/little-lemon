package com.riramzy.littlelemon.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.riramzy.littlelemon.R
import com.riramzy.littlelemon.data.local.menu.LocalMenuItem
import com.riramzy.littlelemon.utils.categoryImagesMap
import com.riramzy.littlelemon.utils.dishesImagesMap
import kotlin.text.lowercase
import kotlin.text.replaceFirstChar

@Composable
fun LemonSection(
    menuItems: List<LocalMenuItem>,
    title: String,
    subTitle: String,
    isCategory: Boolean,
    modifier: Modifier = Modifier,
    onItemClicked: (LocalMenuItem) -> Unit = {}
) {
    Column(
        modifier = modifier
    ) {
        Text(
            title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(
                start = 15.dp
            )
        )

        Text(
            subTitle,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(
                start = 15.dp,
                bottom = 20.dp
            )
        )

        LazyRow(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(start = 15.dp)
        ) {
            items(if (isCategory) menuItems.distinctBy { it.category } else menuItems) { item ->
                ItemCard(
                    isCategory = false,
                    itemText = if (isCategory) {
                        item.category.replaceFirstChar { it.uppercase() }
                    } else {
                        item.title
                    },
                    itemImage = if (isCategory) {
                        categoryImagesMap[item.category.lowercase()] ?: R.drawable.little_lemon_logo
                    } else {
                        dishesImagesMap[item.id] ?: R.drawable.little_lemon_logo
                    },
                    modifier = Modifier.padding(
                        end = 15.dp
                    ),
                    onItemClicked = { onItemClicked(item) }
                )
            }
        }
    }
}