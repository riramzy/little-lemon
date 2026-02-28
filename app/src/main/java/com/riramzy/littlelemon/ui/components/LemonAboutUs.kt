package com.riramzy.littlelemon.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.riramzy.littlelemon.R
import com.riramzy.littlelemon.ui.theme.LittleLemonTheme

@Composable
fun LemonAboutUs(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        //Logo
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(bottom = 20.dp)
                .clip(CircleShape)
                .height(50.dp)
                .background(
                    if (isSystemInDarkTheme()) {
                        MaterialTheme.colorScheme.tertiaryContainer
                    } else {
                        MaterialTheme.colorScheme.tertiaryContainer
                    }
                ),
        ) {
            Image(
                painter = painterResource(R.drawable.little_lemon_logo_text),
                contentDescription = "Little Lemon Logo",
                modifier = Modifier
                    .padding(10.dp)
                    .width(100.dp)
                    .height(40.dp),
                contentScale = ContentScale.FillWidth
            )
        }

        //About Us
        Text(
            text = stringResource(R.string.hero_section_description),
            style = MaterialTheme.typography.bodyMedium,
            color = if (isSystemInDarkTheme()) {
                Color.White
            } else {
                Color.Black
            },
            modifier = Modifier.padding(bottom = 20.dp)
        )

        //Hours
        Column(
            modifier = Modifier.padding(bottom = 20.dp)
        ) {
            Text(
                text = "Hours",
                style = MaterialTheme.typography.titleLarge,
                color = if (isSystemInDarkTheme()) {
                    Color.White
                } else {
                    Color.Black
                },
                modifier = Modifier.padding(bottom = 10.dp)
            )

            Text(
                text = "Mon-Thu: 11am - 9pm\n" +
                        "Fri-Sat: 11am - 10pm\n" +
                        "Sunday: 12pm - 8pm",
                style = MaterialTheme.typography.bodyMedium,
                color = if (isSystemInDarkTheme()) {
                    Color.White
                } else {
                    Color.Black
                },
            )
        }

        //Follow Us
        Column {
            Text(
                text = "Follow Us",
                style = MaterialTheme.typography.titleLarge,
                color = if (isSystemInDarkTheme()) {
                    Color.White
                } else {
                    Color.Black
                },
                modifier = Modifier.padding(bottom = 10.dp)
            )

            Row {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(end = 15.dp)
                        .clip(shape = CircleShape)
                        .size(40.dp)
                        .background(
                            if (isSystemInDarkTheme()) {
                                MaterialTheme.colorScheme.tertiaryContainer
                            } else {
                                MaterialTheme.colorScheme.tertiaryContainer
                            }
                        ),
                ) {
                    Image(
                        painter = painterResource(R.drawable.facebook),
                        contentDescription = "Time Icon",
                        modifier = Modifier
                            .size(20.dp)
                    )
                }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(end = 15.dp)
                        .clip(shape = CircleShape)
                        .size(40.dp)
                        .background(
                            if (isSystemInDarkTheme()) {
                                MaterialTheme.colorScheme.tertiaryContainer
                            } else {
                                MaterialTheme.colorScheme.tertiaryContainer
                            }
                        ),
                ) {
                    Image(
                        painter = painterResource(R.drawable.instagram),
                        contentDescription = "Time Icon",
                        modifier = Modifier
                            .size(18.dp)
                    )
                }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(end = 25.dp)
                        .clip(shape = CircleShape)
                        .size(40.dp)
                        .background(
                            if (isSystemInDarkTheme()) {
                                MaterialTheme.colorScheme.tertiaryContainer
                            } else {
                                MaterialTheme.colorScheme.tertiaryContainer
                            }
                        ),
                ) {
                    Image(
                        painter = painterResource(R.drawable.x),
                        contentDescription = "Time Icon",
                        modifier = Modifier
                            .size(15.dp)
                    )
                }
            }
        }

    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LemonAboutUsPreview() {
    LittleLemonTheme {
        LemonAboutUs()
    }
}