package com.example.littlelemon.ui.screens.reservation

import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.littlelemon.ui.components.LemonDateSelector
import com.example.littlelemon.ui.components.LemonDurationSelector
import com.example.littlelemon.ui.components.LemonNumberOfDinersSelector
import com.example.littlelemon.ui.components.LemonTimeSelector
import com.example.littlelemon.ui.components.TopAppBar
import com.example.littlelemon.ui.components.YellowLemonButton
import com.example.littlelemon.ui.theme.LittleLemonTheme
import java.time.LocalTime
import java.time.MonthDay
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReservationTableDetailsScreen(
    onNextClicked: () -> Unit = {}
) {
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
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LinearProgressIndicator(
                    progress = { 0.5f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 15.dp,
                            end = 15.dp,
                            top = 15.dp
                        ),
                    color = if (isSystemInDarkTheme()) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.primaryContainer
                           },
                    trackColor = MaterialTheme.colorScheme.tertiaryContainer,
                    strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
                )
                YellowLemonButton(
                    text = "Next",
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
                    },
                    onClick = onNextClicked
                )
            }
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
            verticalArrangement = Arrangement.spacedBy(25.dp)
        ) {
            item {
                DetailsHeadline(
                    modifier = Modifier.padding(
                        horizontal = 15.dp,
                    )
                )
            }
            item {
                DatePicker(
                    modifier = Modifier.padding(
                        horizontal = 15.dp
                    )
                )
            }
            item {
                TimePicker(
                    modifier = Modifier.padding(
                        horizontal = 15.dp
                    )
                )
            }
            item {
                DurationPicker(
                    modifier = Modifier.padding(
                        horizontal = 15.dp
                    )
                )
            }
            item {
                NumberOfDinerPicker(
                    modifier = Modifier.padding(
                        horizontal = 15.dp
                    )
                )
            }
        }
    }
}

@Composable
fun DetailsHeadline(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Table Reservation".uppercase(),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "Table Details",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatePicker(
    modifier: Modifier = Modifier
) {
    val currentMonth = YearMonth.now()
    val currentDay = MonthDay.now().dayOfMonth
    val daysInMonth = currentMonth.lengthOfMonth()
    val monthDays = (currentDay..daysInMonth).map { day ->
        MonthDay.of(currentMonth.month, day)
    }

    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Choose date",
                style = MaterialTheme.typography.labelLarge,
            )
            Text(
                text = currentMonth.month.getDisplayName(
                    TextStyle.FULL_STANDALONE,
                    Locale.getDefault()
                ),
                style = MaterialTheme.typography.labelLarge,
            )
        }

        LazyRow {
            items(monthDays) { day ->
                LemonDateSelector(
                    day = day,
                    modifier = Modifier.padding(end = 15.dp)
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimePicker(
    modifier: Modifier = Modifier
) {
    fun generateTimeSlots(openingTime: LocalTime, closingTime: LocalTime, intervalMinutes: Long): List<LocalTime> {
        val timeSlots = mutableListOf<LocalTime>()
        var currentTime = openingTime
        while (currentTime.isBefore(closingTime)) {
            timeSlots.add(currentTime)
            currentTime = currentTime.plusMinutes(intervalMinutes)
        }
        return timeSlots
    }

    val openingTime = LocalTime.of(12, 0)
    val closingTime = LocalTime.of(22, 0)
    val timeSlots = generateTimeSlots(openingTime, closingTime, 30)

    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Choose time",
                style = MaterialTheme.typography.labelLarge,
            )
        }

        LazyRow {
            items(timeSlots) { time ->
                LemonTimeSelector(
                    time = time,
                    modifier = Modifier.padding(end = 15.dp)
                )
            }
        }
    }
}

@Composable
fun DurationPicker(
    modifier: Modifier = Modifier
) {
    val durations = listOf(
        "1  hour",
        "2 hours",
        "3 hours",
        "4 hours"
    )

    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Choose duration",
                style = MaterialTheme.typography.labelLarge,
            )
        }

        LazyRow {
            items(durations) { duration ->
                LemonDurationSelector(
                    duration = duration,
                    modifier = Modifier.padding(end = 15.dp)
                )
            }
        }
    }
}

@Composable
fun NumberOfDinerPicker(
    modifier: Modifier = Modifier
) {
    val numberOfDiners = listOf(
        "1",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        "8",
        "9",
        "10"
    )

    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Choose number of diners",
                style = MaterialTheme.typography.labelLarge,
            )
        }

        LazyRow {
            items(numberOfDiners) { diners ->
                LemonNumberOfDinersSelector(
                    numberOfDiners = diners,
                    modifier = Modifier.padding(end = 15.dp)
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun ReservationTableDetailsScreenPreview() {
    LittleLemonTheme {
        ReservationTableDetailsScreen()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ReservationTableDetailsScreenDarkPreview() {
    LittleLemonTheme {
        ReservationTableDetailsScreen()
    }
}