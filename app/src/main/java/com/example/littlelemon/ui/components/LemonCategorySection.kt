package com.example.littlelemon.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.littlelemon.R
import com.example.littlelemon.data.local.menu.LocalMenuItem
import com.example.littlelemon.ui.theme.LittleLemonTheme
import com.example.littlelemon.utils.categoryImagesMap
import com.example.littlelemon.utils.dishesImagesMap

@Composable
fun LemonCategorySection(
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

        Column(
            verticalArrangement = Arrangement.spacedBy(15.dp), // Use spacedBy for consistent spacing
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(start = 15.dp)
        ) {
            val itemsToShow = if (isCategory) menuItems.distinctBy { it.category } else menuItems

            itemsToShow.forEach { item ->
                ItemCard(
                    isCategory = true,
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
                    onItemClicked = { onItemClicked(item) }
                )
            }
        }
    }
}

@Preview
@Composable
fun LemonCategorySectionPreview() {
    LittleLemonTheme() {
        Surface() {
            LemonCategorySection(
                menuItems = listOf(
                    LocalMenuItem(
                        id = 1,
                        title = "Greek Salad",
                        description = "The famous greek salad of crispy lettuce, peppers, olives, our Chicago...",
                        price = 12.99,
                        category = "starters",
                        image = "",
                    )
                ),
                title = "Shop by Category",
                subTitle = "Find what you want quickly",
                isCategory = true,
                onItemClicked = {},
                modifier = Modifier.padding(20.dp)
            )
        }
    }
}