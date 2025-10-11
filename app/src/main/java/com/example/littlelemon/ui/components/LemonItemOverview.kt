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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.littlelemon.R
import com.example.littlelemon.ui.theme.LittleLemonTheme

@Composable
fun ItemOverview(
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .height(110.dp)
            .width(376.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSystemInDarkTheme()) {
                MaterialTheme.colorScheme.onTertiary
            } else {
                MaterialTheme.colorScheme.tertiaryContainer
            },
            contentColor = if (isSystemInDarkTheme()) {
                Color.White
            } else {
                Color.Black
            }
        ),
        border = BorderStroke(
            0.5.dp,
            if (isSystemInDarkTheme()) {
                MaterialTheme.colorScheme.tertiaryContainer
            } else {
                MaterialTheme.colorScheme.secondaryContainer
            }
        ),
        shape = RoundedCornerShape(20.dp)
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
                    text = "Greek Salad",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(bottom = 6.dp)
                )

                Text(
                    "The famous greek salad of crispy lettuce, peppers, olives and our Chicago style feta cheese, garnished with crunchy garlic and rosemary croutons.",
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(
                        bottom = 8.dp,
                        end = 8.dp
                    )
                )

                Text(
                    text = "$12",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            Image(
                painter = painterResource(R.drawable.greek_salad),
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