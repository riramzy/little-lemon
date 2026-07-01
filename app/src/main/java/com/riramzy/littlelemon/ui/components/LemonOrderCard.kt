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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.riramzy.littlelemon.R
import com.riramzy.littlelemon.data.local.orders.LocalOrderItem
import com.riramzy.littlelemon.ui.theme.LittleLemonTheme
import com.riramzy.littlelemon.utils.dishesImagesMap

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
            .wrapContentHeight()
            .fillMaxWidth(),
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
                .padding(15.dp)
                .wrapContentHeight(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Order #$orderId".uppercase(),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Black,
                )

                Card(
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
                    Text(
                        text = "Confirmed".uppercase(),
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(6.dp)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .heightIn(min = 150.dp)
                    .padding(bottom = 10.dp)
            ) {
                ordersItem.forEach { orderItem ->
                    OrderItemDetails(
                        modifier = Modifier.padding(
                            vertical = 3.dp,
                        ),
                        orderItem = orderItem
                    )
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isSystemInDarkTheme()) {
                        Color.Black

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
                            text = "$5.00",
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
                            text = "$$totalPrice",
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
    Box(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .background(
                shape = RoundedCornerShape(25.dp),
                color = if (isSystemInDarkTheme()) {
                    Color.Black
                } else {
                    Color.White
                }
            ),
        contentAlignment = Alignment.Center

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),
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
                    contentDescription = null
                )

                Column(
                    modifier = Modifier
                        .padding(start = 10.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "${orderItem.quantity}x ${orderItem.title}",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Black
                    )

                    if (orderItem.selectedAddOns.isNotEmpty()) {
                        Text(
                            text = orderItem.selectedAddOns,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                        )
                    }
                }
            }

            Text(
                text = "$${"%.2f".format(orderItem.quantity * orderItem.price)}",
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