package com.example.littlelemon.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.littlelemon.R
import com.example.littlelemon.ui.theme.LittleLemonTheme
import com.example.littlelemon.utils.dishesImagesMap

@Composable
fun ItemOverview(
    modifier: Modifier = Modifier,
    itemName: String = "Greek Salad",
    itemDescription: String = "The famous greek salad of crispy lettuce, peppers, olives and our Chicago...",
    itemPrice: Double = 12.99,
    itemId: Int = 1,
    onItemClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .height(110.dp)
            .width(376.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSystemInDarkTheme()) {
                MaterialTheme.colorScheme.background
            } else {
                MaterialTheme.colorScheme.background
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
        shape = RoundedCornerShape(20.dp),
        onClick = {
            onItemClick()
        }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
                .padding(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(start = 4.dp)
                    .weight(3f)
            ) {
                Text(
                    text = itemName,
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(bottom = 6.dp)
                )

                Text(
                    text = itemDescription,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(
                        bottom = 8.dp,
                        end = 8.dp
                    )
                )

                Text(
                    text = itemPrice.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            Image(
                painter = painterResource(id = dishesImagesMap[itemId] ?: R.drawable.greek_salad),
                contentDescription = "Greek Salad",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f)
                    .clip(
                        RoundedCornerShape(100.dp)
                    ),
            )
        }

    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun ItemOverviewPreview() {
    LittleLemonTheme {
        ItemOverview()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ItemOverviewDarkPreview() {
    LittleLemonTheme {
        ItemOverview()
    }
}