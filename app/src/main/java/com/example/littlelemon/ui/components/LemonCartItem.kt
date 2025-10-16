package com.example.littlelemon.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.littlelemon.R
import com.example.littlelemon.data.local.cart.LocalCartItem
import com.example.littlelemon.data.local.menu.LocalMenuItem
import com.example.littlelemon.ui.theme.LittleLemonTheme
import com.example.littlelemon.utils.dishesImagesMap

@Composable
fun LemonCartItem(
    modifier: Modifier = Modifier,
    item: LocalCartItem,
    onRemoveClicked: () -> Unit = {},
    onAddClicked: () -> Unit = {},
    onMinusClicked: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .wrapContentSize()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(20.dp),
                ambientColor = Color.Transparent,
                spotColor = Color.Gray,
                clip = true
            )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isSystemInDarkTheme()) {
                    MaterialTheme.colorScheme.onTertiary
                } else {
                    Color(0xFFF9F9F9)
                },
                contentColor = if (isSystemInDarkTheme()) {
                    MaterialTheme.colorScheme.tertiaryContainer
                } else {
                    MaterialTheme.colorScheme.primary
                }
            ),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row {
                    Image(
                        painter = painterResource(id = dishesImagesMap.getValue(item.id)),
                        modifier = Modifier
                            .padding(start = 15.dp)
                            .clip(CircleShape)
                            .size(65.dp),
                        contentScale = ContentScale.Crop,
                        contentDescription = null
                    )

                    Column(
                        modifier = Modifier
                            .padding(start = 20.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.labelLarge
                        )
                        Text(
                            text = "$${item.price}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Row(
                            modifier = Modifier.padding(top = 8.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = {
                                    onMinusClicked()
                                },
                                modifier = Modifier.size(15.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.RemoveCircleOutline,
                                    contentDescription = null
                                )
                            }
                            Text(
                                text = item.quantity.toString(),
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(horizontal = 10.dp)
                            )
                            IconButton(
                                onClick = {
                                    onAddClicked()
                                },
                                modifier = Modifier.size(15.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AddCircleOutline,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }

                IconButton(
                    onClick = {
                        onRemoveClicked()
                    },
                    modifier = Modifier
                        .padding(end = 15.dp)
                        .size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.DeleteForever,
                        contentDescription = null
                    )
                }
            }
        }
    }

}

@Preview
@Composable
fun LemonCartItemPreview() {
    LittleLemonTheme {
        LemonCartItem(
            modifier = Modifier.padding(20.dp),
            item = LocalCartItem(
                id = 1,
                title = "Greek Salad",
                price = 10.0,
                image = ""
            )
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LemonCartItemDarkPreview() {
    LittleLemonTheme {
        LemonCartItem(
            modifier = Modifier.padding(20.dp),
            item = LocalCartItem(
                id = 1,
                title = "Greek Salad",
                price = 10.0,
                image = ""
            )
        )
    }
}
