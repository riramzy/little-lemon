package com.example.littlelemon.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.littlelemon.R
import com.example.littlelemon.ui.theme.LittleLemonTheme

@Composable
fun HeroCard(
    modifier: Modifier = Modifier,
    headlineText: String,
    descriptionText: String,
    buttonText: String,
    heroImage: Int,
    onReserveClicked: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .width(376.dp)
            .height(250.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSystemInDarkTheme()) {
                MaterialTheme.colorScheme.onSecondary
            } else {
                MaterialTheme.colorScheme.secondaryContainer
            },
            contentColor = if (isSystemInDarkTheme()) {
                MaterialTheme.colorScheme.onSecondaryContainer
            } else {
                MaterialTheme.colorScheme.onSecondaryContainer
            }
        )
    ) {
        Row(
            modifier = Modifier
                .width(376.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .weight(1.5f),
                verticalArrangement = Arrangement.SpaceAround,
            ) {
                Card(
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .weight(1f)
                        .wrapContentHeight(),
                    shape = RoundedCornerShape(100.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSystemInDarkTheme()) {
                            Color.Green.copy(
                                alpha = 0.5f
                            )
                        } else {
                            Color.Green
                        },
                        contentColor = Color.White
                    ),
                ) {
                    Text(
                        text = headlineText,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier
                            .padding(6.dp),
                        textAlign = TextAlign.Center
                    )
                }

                Text(
                    text = descriptionText,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .weight(2f),
                    overflow = TextOverflow.Ellipsis
                )

                YellowLemonButton(
                    text = buttonText,
                    color = if (isSystemInDarkTheme()) {
                        MaterialTheme.colorScheme.onPrimary
                    } else {
                        MaterialTheme.colorScheme.primaryContainer
                    },
                    textColor = if (isSystemInDarkTheme()) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onPrimaryContainer
                    },
                    onClick = onReserveClicked,
                    modifier = Modifier.weight(1f)
                )
            }
            Image(
                painter = painterResource(heroImage),
                contentDescription = "Hero Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .weight(1f)
            )
        }
    }
}

@Preview
@Composable
fun HeroCardPreview() {
    LittleLemonTheme {
        HeroCard(
            headlineText = "Fresh Today!",
            descriptionText = stringResource(R.string.hero_section_description),
            buttonText = "Order Take Away",
            heroImage = R.drawable.hero_image
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HeroCardDarkPreview() {
    LittleLemonTheme {
        HeroCard(
            headlineText = "Fresh Today!",
            descriptionText = stringResource(R.string.hero_section_description),
            buttonText = "Order Take Away",
            heroImage = R.drawable.hero_image
        )
    }
}