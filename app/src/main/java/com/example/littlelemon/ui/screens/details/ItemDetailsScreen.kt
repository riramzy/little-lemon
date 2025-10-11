package com.example.littlelemon.ui.screens.details

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.outlined.RemoveCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.littlelemon.R
import com.example.littlelemon.ui.components.TopAppBar
import com.example.littlelemon.ui.components.YellowLemonButton
import com.example.littlelemon.ui.theme.LittleLemonTheme

@Composable
fun ItemDetailsScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .padding(
                        vertical = 10.dp,
                        horizontal = 15.dp
                    ),
                isSearchRequired = false
            )
        },
        bottomBar = {
            YellowLemonButton(
                text = "Add to cart",
                modifier = Modifier
                    .padding(
                        horizontal = 15.dp,
                        vertical = 15.dp
                    )
                    .fillMaxWidth(),
                color = if (isSystemInDarkTheme()) {
                    MaterialTheme.colorScheme.onPrimary
                } else {
                    MaterialTheme.colorScheme.primaryContainer
                },
                textColor = if (isSystemInDarkTheme()) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onPrimaryContainer
                }
            )
        },
        containerColor = if (isSystemInDarkTheme()) {
            MaterialTheme.colorScheme.background
        } else {
            Color.White
        },
        modifier = Modifier
            .statusBarsPadding()
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            item {
                ItemDetails(
                    modifier = Modifier
                        .padding(
                            horizontal = 15.dp,
                        )
                )
            }
            item {
                DeliveryDetails(
                    modifier = Modifier
                        .padding(
                            horizontal = 15.dp,
                            vertical = 15.dp
                        )
                )
            }
            item {
                AddOnsSelection(
                    modifier = Modifier
                        .padding(
                            horizontal = 15.dp,
                        )
                )
            }
            item {
                ItemNumberPicker()
            }
        }
    }
}

@Composable
fun ItemDetails(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(
            containerColor = if (isSystemInDarkTheme()) {
                MaterialTheme.colorScheme.onPrimary
            } else {
                MaterialTheme.colorScheme.primaryContainer
            },
            contentColor = if (isSystemInDarkTheme()) {
                MaterialTheme.colorScheme.onSecondaryContainer
            } else {
                MaterialTheme.colorScheme.onPrimaryContainer
            }
        ),
        shape = RoundedCornerShape(25.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.greek_salad),
            contentDescription = "Greek Salad",
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(15.dp)
                .clip(
                    RoundedCornerShape(25.dp)
                ),
            contentScale = ContentScale.Crop,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 15.dp,
                    start = 15.dp,
                    end = 15.dp,
                    bottom = 10.dp
                )
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Greek Salad",
                style = MaterialTheme.typography.titleLarge,
                fontSize = 25.sp,
                color = if (isSystemInDarkTheme()) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onPrimaryContainer
                }
            )

            Text(
                text = "$12.99",
                style = MaterialTheme.typography.titleLarge,
                fontSize = 25.sp,
                color = if (isSystemInDarkTheme()) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onPrimaryContainer
                }
            )
        }
        Text(
            text = "A traditional Greek salad consists of sliced cucumbers, tomatoes, green bell pepper, red onion, olives, and feta cheese.",
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier
                .padding(
                    start = 15.dp,
                    end = 15.dp,
                    bottom = 15.dp
                ),
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            color = if (isSystemInDarkTheme()) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.onPrimaryContainer
            }
        )
    }
}

@Composable
fun DeliveryDetails(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                painter = painterResource(id = R.drawable.delivery_icon),
                contentDescription = "Delivery",
                modifier = Modifier.size(50.dp),
            )
            Text(
                text = "Delivery time:",
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 10.dp)
            )
            Text(
                text = "30 minutes",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 10.dp)
            )
        }
    }
}

@Composable
fun AddOnsSelection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Text(
            text = "Add Ons",
            style = MaterialTheme.typography.labelLarge,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Feta",
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 10.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "$1.00",
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(start = 10.dp)
                )
                RadioButton(
                    selected = false,
                    onClick = { /*TODO*/ }
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Parmesan",
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 10.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "$1.00",
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(start = 10.dp)
                )
                RadioButton(
                    selected = false,
                    onClick = { /*TODO*/ }
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Dressing",
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 10.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "$1.00",
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(start = 10.dp)
                )
                RadioButton(
                    selected = false,
                    onClick = { /*TODO*/ }
                )
            }
        }
    }
}

@Composable
fun ItemNumberPicker(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .width(180.dp)
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = { /*TODO*/ }
        ) {
            Icon(
                imageVector = Icons.Default.AddCircle,
                contentDescription = "Add",
                modifier = Modifier.size(25.dp),
            )
        }

        Text(
            text = "1",
            style = MaterialTheme.typography.titleLarge,
            fontSize = 25.sp
        )

        IconButton(
            onClick = { /*TODO*/ }
        ) {
            Icon(
                imageVector = Icons.Outlined.RemoveCircle,
                contentDescription = "Minus",
                modifier = Modifier.size(25.dp),
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun ItemDetailsScreenPreview() {
    LittleLemonTheme {
        ItemDetailsScreen()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ItemDetailsScreenDarkPreview() {
    LittleLemonTheme {
        ItemDetailsScreen()
    }
}