package com.example.littlelemon.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.littlelemon.ui.theme.LittleLemonTheme

@Composable
fun ItemCard(
    modifier: Modifier = Modifier,
    itemText: String,
    itemImage: Any,
    onItemClicked: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .width(126.dp)
            .height(130.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSystemInDarkTheme()) {
                MaterialTheme.colorScheme.background
            } else {
                Color.White
            },
            contentColor = if (isSystemInDarkTheme()) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.primary
            }
        ),
        border = BorderStroke(
            0.5.dp,
            if (isSystemInDarkTheme()) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.primary
            }
        ),
        onClick = onItemClicked
    ) {
        Column {
            AsyncImage(
                model = itemImage,
                contentDescription = "Item Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                contentScale = ContentScale.Crop
            )
            /*
            Image(
                painter = painterResource(id = itemImage),
                contentDescription = "Greek Salad",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                contentScale = ContentScale.Crop
            )

             */
            Text(
                text = itemText,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        vertical = 10.dp,
                        horizontal = 8.dp,
                    )
            )
        }
    }
}

@Preview
@Composable
fun ItemCardPreview() {
    LittleLemonTheme {
        ItemCard(
            itemText = "Greek Salad",
            itemImage = ""
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ItemCardDarkPreview() {
    LittleLemonTheme {
        ItemCard(
            itemText = "Greek Salad",
            itemImage = "R.drawable.greek_salad"
        )
    }
}