package com.example.littlelemon.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.littlelemon.R
import com.example.littlelemon.data.local.orders.LocalOrderItem
import com.example.littlelemon.ui.theme.LittleLemonTheme
import com.example.littlelemon.utils.dishesImagesMap

@Composable
fun LemonOrderCard(
    modifier: Modifier = Modifier,
    ordersItem: List<LocalOrderItem>,
    paymentType: String = "COD",
    totalPrice: String = "0.0",
    orderId: Int = 0
) {
    Card(
        modifier = modifier
            .height(350.dp),
        shape = RoundedCornerShape(25.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSystemInDarkTheme()) {
                MaterialTheme.colorScheme.primary.copy(0.2f)

            } else {
                MaterialTheme.colorScheme.primaryContainer.copy(0.2f)
            },
            contentColor = if (isSystemInDarkTheme()) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.primary
            }
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Headline and status pill
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Order #$orderId".uppercase(),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.W900,
                    modifier = Modifier.padding(start = 10.dp)
                )
                Card(
                    modifier = modifier
                        .height(25.dp)
                        .width(100.dp)
                        .fillMaxHeight(),
                    shape = RoundedCornerShape(100.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSystemInDarkTheme()) {
                            MaterialTheme.colorScheme.primary

                        } else {
                            MaterialTheme.colorScheme.primary
                        },
                        contentColor = if (isSystemInDarkTheme()) {
                            MaterialTheme.colorScheme.onPrimary
                        } else {
                            Color.White
                        }
                    )
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Confirmed".uppercase(),
                            style = MaterialTheme.typography.bodyMedium,
                            fontSize = 14.sp,
                        )
                    }
                }
            }
            //Order items and their details
            LazyColumn(
                modifier = Modifier
                    .height(150.dp)
                    //.width(334.dp)
            ) {
                items(ordersItem) { orderItem ->
                    OrderItemDetails(
                        modifier = Modifier.padding(
                            vertical = 3.dp,
                            horizontal = 15.dp
                        ),
                        orderItem = orderItem
                    )
                }
            }
            //Order payment summary
            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .height(90.dp),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isSystemInDarkTheme()) {
                        MaterialTheme.colorScheme.primary.copy(0.2f)

                    } else {
                        Color.White
                    },
                    contentColor = if (isSystemInDarkTheme()) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.primary
                    }
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(15.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Payment type",
                            style = MaterialTheme.typography.titleSmall
                        )
                        Text(
                            text = paymentType,
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Shipping",
                            style = MaterialTheme.typography.titleSmall
                        )
                        Text(
                            text = "5$",
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Total",
                            style = MaterialTheme.typography.titleSmall
                        )
                        Text(
                            text = "$totalPrice$",
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun OrderItemDetails(
    modifier: Modifier = Modifier,
    orderItem: LocalOrderItem,
) {
    Card(
        modifier = modifier
            .height(45.dp),
            //.width(334.dp),
        shape = RoundedCornerShape(25.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSystemInDarkTheme()) {
                MaterialTheme.colorScheme.primary.copy(0.2f)

            } else {
                Color.White
            },
            contentColor = if (isSystemInDarkTheme()) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.primary
            }
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(id = dishesImagesMap[orderItem.dishId] ?: R.drawable.greek_salad),
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(32.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                    contentDescription = "Item Image"
                )
                Text(
                    "${orderItem.quantity}x ${orderItem.title}",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier
                        .padding(start = 10.dp)
                )
            }
            Text(
                "${orderItem.quantity*orderItem.price}$" ,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Black,
                modifier = Modifier
                    .padding(end = 10.dp)
            )
        }
    }
}

@Preview
@Composable
fun LemonOrderCardPreview() {
    LittleLemonTheme {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            horizontalArrangement = Arrangement.Center
        ) {
            LemonOrderCard(
                modifier = Modifier.padding(10.dp),
                ordersItem = listOf(
                    LocalOrderItem(
                        id = 1,
                        orderId = 1,
                        title = "Greek Salad",
                        price = 10.0,
                        image = "https://github.com/Meta-Mobile-Developer-PC/Working-With-Data-API/blob/main/images/greekSalad.jpg?raw=true",
                        quantity = 1,
                        dishId = 1
                    )
                )
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LemonOrderCardDarkPreview() {
    LittleLemonTheme {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background),
            horizontalArrangement = Arrangement.Center
        ) {
            LemonOrderCard(
                modifier = Modifier.padding(10.dp),
                ordersItem = listOf(
                    LocalOrderItem(
                        id = 1,
                        orderId = 1,
                        title = "Greek Salad",
                        price = 10.0,
                        image = "https://github.com/Meta-Mobile-Developer-PC/Working-With-Data-API/blob/main/images/greekSalad.jpg?raw=true",
                        quantity = 1,
                        dishId = 1
                    )
                )
            )
        }
    }
}