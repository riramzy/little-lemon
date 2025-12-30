package com.example.littlelemon.ui.components

import android.content.res.Configuration
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.littlelemon.R
import com.example.littlelemon.ui.theme.LittleLemonTheme

@Composable
fun LemonReservationCard(
    modifier: Modifier = Modifier,
    nameOfReserver: String = "John Doe",
    date: String = "Dec 4 2025",
    time: String = "19:00",
    duration: String = "3 hours",
    numberOfDiners: String = "3",
    totalPrice: Double = 50.00,
    paymentMethod: String = "Mastercard",
    reservationId: Int = 123456
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
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
                .padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
                ) {
                Text(
                    text = "Reservation #$reservationId".uppercase(),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.W900,
                    modifier = Modifier.padding(vertical = 10.dp)
                )

                Card(
                    modifier = modifier
                        .height(25.dp)
                        .width(55.dp)
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
                            text = "$$totalPrice",
                            style = MaterialTheme.typography.bodyMedium,
                            fontSize = 14.sp,
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .padding(top = 15.dp)
                    .fillMaxWidth(),
            ) {
                ReservationItemDetails(
                    title = "Guest",
                    entry = nameOfReserver,
                    icon = R.drawable.profile_icon,
                    modifier = Modifier.padding(bottom = 5.dp)
                )
                ReservationItemDetails(
                    title = "Date",
                    entry = date,
                    icon = R.drawable.date,
                    modifier = Modifier.padding(bottom = 5.dp)
                )
                ReservationItemDetails(
                    title = "Time",
                    entry = time,
                    icon = R.drawable.time,
                    modifier = Modifier.padding(bottom = 5.dp)
                )
                ReservationItemDetails(
                    title = "Duration",
                    entry = duration,
                    icon = R.drawable.duration,
                    modifier = Modifier.padding(bottom = 5.dp)
                )
                ReservationItemDetails(
                    title = "Number of diners",
                    entry = numberOfDiners,
                    icon = R.drawable.people,
                )
            }
        }
    }
}

@Composable
fun ReservationItemDetails(
    modifier: Modifier = Modifier,
    title: String,
    entry: String,
    icon: Int
) {
    Card(
        modifier = modifier
            .wrapContentHeight(),
        shape = RoundedCornerShape(25.dp),
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
        Row(
            modifier = Modifier
                .padding(6.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .size(30.dp)
                    .background(
                        if (isSystemInDarkTheme()) {
                            MaterialTheme.colorScheme.primary.copy(0.2f)
                        } else {
                            MaterialTheme.colorScheme.primaryContainer.copy(0.2f)
                        }
                    ),
            ) {
                Icon(
                    painter = painterResource(icon),
                    contentDescription = "Time Icon",
                    modifier = Modifier
                        .size(25.dp)
                )
            }

            Column(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    color = if (isSystemInDarkTheme()) {
                        MaterialTheme.colorScheme.primary.copy(0.5f)
                    } else {
                        MaterialTheme.colorScheme.primary.copy(0.5f)
                    },
                    fontSize = 12.sp
                )

                Text(
                    text = entry,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun LemonReservationCardPreview() {
    LittleLemonTheme() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(10.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            LemonReservationCard()
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LemonReservationCardDarkPreview() {
    LittleLemonTheme() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(10.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            LemonReservationCard()
        }
    }
}