package com.example.littlelemon.ui.screens.checkout

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.littlelemon.ui.components.ItemOverview
import com.example.littlelemon.ui.components.LemonCutlerySelector
import com.example.littlelemon.ui.components.TopAppBar
import com.example.littlelemon.ui.components.YellowLemonButton
import com.example.littlelemon.ui.screens.details.DeliveryDetails
import com.example.littlelemon.ui.theme.LittleLemonTheme

@Composable
fun CheckoutScreen() {
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
                text = "Checkout",
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
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                DeliveryDetails(
                    modifier = Modifier
                        .padding(
                            horizontal = 15.dp,
                        )
                )
            }
            item {
                LemonCutlerySelector(
                    modifier = Modifier
                        .padding(
                            horizontal = 15.dp,
                            vertical = 15.dp
                        )
                )
            }
            item {
                OrderSummary(
                    modifier = Modifier
                        .padding(
                            horizontal = 15.dp,
                            vertical = 15.dp
                        )
                        .wrapContentHeight()
                )
            }
            item {
                SuggestionsSection(
                    modifier = Modifier
                        .padding(
                            horizontal = 15.dp,
                            vertical = 15.dp
                        )
                )
            }
            item {
                TotalPrice(
                    modifier = Modifier
                        .padding(
                            horizontal = 15.dp,
                            vertical = 15.dp
                        )
                )
            }
        }
    }
}

@Composable
fun OrderSummary(
    modifier: Modifier = Modifier
) {
    val items = listOf(
        "1 x Greek Salad" to "$12.99",
        "1 x Bruschetta" to "$5.99",
        "1 x Pasta" to "$8.99",
        "1 x Lemon Dessert" to "$5.00",
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Order Summary",
            style = MaterialTheme.typography.labelLarge,
            fontSize = 24.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "Items",
            style = MaterialTheme.typography.labelLarge,
            fontSize = 20.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            items.forEach { (name, price) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.labelSmall,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Start,
                    )
                    Text(
                        text = price,
                        style = MaterialTheme.typography.labelSmall,
                        fontSize = 20.sp,
                        textAlign = TextAlign.End,
                    )
                }
            }
        }
    }
}

@Composable
fun SuggestionsSection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .wrapContentWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "ADD MORE TO YOUR ORDER!",
            style = MaterialTheme.typography.titleLarge,
            fontSize = 20.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )
        LazyRow(
            modifier = Modifier.padding(top = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            items(5) {
                ItemOverview(
                    modifier = Modifier
                        .width(295.dp)
                        .height(110.dp)
                )
            }
        }
    }
}

@Composable
fun TotalPrice(
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Subtotal",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Start,
            )
            Text(
                text = "$29.99",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.End,
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Delivery",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Start,
            )
            Text(
                text = "$2.00",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.End,
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Service",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Start,
            )
            Text(
                text = "$31.99",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.End,
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Total",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp,
                textAlign = TextAlign.Start,
            )
            Text(
                text = "$31.99",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp,
                textAlign = TextAlign.End,
            )
        }
    }
}

@Preview
@Composable
fun CheckoutScreenPreview() {
    LittleLemonTheme {
        CheckoutScreen()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CheckoutScreenDarkPreview() {
    LittleLemonTheme {
        CheckoutScreen()
    }
}