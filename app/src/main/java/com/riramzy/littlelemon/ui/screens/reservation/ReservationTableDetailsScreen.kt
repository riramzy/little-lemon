package com.riramzy.littlelemon.ui.screens.reservation

import android.content.res.Configuration
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.riramzy.littlelemon.ui.components.LemonDateSelector
import com.riramzy.littlelemon.ui.components.LemonDurationSelector
import com.riramzy.littlelemon.ui.components.LemonNavigationBar
import com.riramzy.littlelemon.ui.components.LemonNumberOfDinersSelector
import com.riramzy.littlelemon.ui.components.LemonTimeSelector
import com.riramzy.littlelemon.ui.components.TopAppBar
import com.riramzy.littlelemon.ui.theme.LittleLemonTheme
import com.riramzy.littlelemon.utils.Screen
import java.time.LocalDate
import java.time.LocalTime
import java.time.MonthDay
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReservationTableDetailsScreen(
    reservationVm: ReservationVm = hiltViewModel(),
    navController: NavHostController,
) {
    val formState by reservationVm.reservationFormState.collectAsStateWithLifecycle()

    ReservationTableDetailsScreenContent(
        navController = navController,
        formState = formState,
        updateDate = reservationVm::updateDate,
        updateTime = reservationVm::updateTime,
        updateDuration = reservationVm::updateDuration,
        updateNumberOfDiners = reservationVm::updateNumberOfDiners
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReservationTableDetailsScreenContent(
    navController: NavHostController,
    formState: ReservationFormState,
    updateDate: (LocalDate) -> Unit = {},
    updateTime: (LocalTime) -> Unit = {},
    updateDuration: (String) -> Unit = {},
    updateNumberOfDiners: (Int) -> Unit = {}
) {
    val navBackStackEntry by navController.currentBackStackEntryFlow.collectAsState(null)
    val currentRoute = navBackStackEntry?.destination?.route ?: Screen.Home.route

    val context = LocalContext.current

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
        floatingActionButton = {
            LemonNavigationBar(
                isActionEnabled = true,
                onActionText = "Next",
                onActionClicked = {
                    if (
                        formState.selectedDate == null ||
                        formState.selectedTime == null ||
                        formState.selectedDuration == null ||
                        formState.selectedNumberOfDiners == null
                    ) {
                        Toast.makeText(
                            context,
                            "Please select all fields",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        navController.navigate(Screen.ReservationPayment.route)
                    }
                },
                onHomeClicked = {
                    navController.navigate(Screen.Home.route)
                },
                onReservationClicked = {
                    navController.navigate(Screen.ReservationTableDetails.route)
                },
                onCartClicked = {
                    navController.navigate(Screen.Cart.route)
                },
                onProfileClicked = {
                    navController.navigate(Screen.Profile.route)
                },
                selectedRoute = currentRoute
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
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
                    ),
                    date = formState.selectedDate ?: LocalDate.now(),
                    updateDate = updateDate
                )
            }
            item {
                TimePicker(
                    modifier = Modifier.padding(
                        horizontal = 15.dp
                    ),
                    time = formState.selectedTime ?: LocalTime.now(),
                    updateTimes = updateTime
                )
            }
            item {
                DurationPicker(
                    modifier = Modifier.padding(
                        horizontal = 15.dp
                    ),
                    duration = formState.selectedDuration,
                    updateDuration = updateDuration
                )
            }
            item {
                NumberOfDinerPicker(
                    modifier = Modifier.padding(
                        horizontal = 15.dp
                    ),
                    diners = formState.selectedNumberOfDiners ?: 1,
                    updateNumberOfDiners = updateNumberOfDiners
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
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "Table Details",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatePicker(
    modifier: Modifier = Modifier,
    date: LocalDate,
    updateDate: (LocalDate) -> Unit
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
                color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text = currentMonth.month.getDisplayName(
                    TextStyle.FULL_STANDALONE,
                    Locale.getDefault()
                ),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }

        LazyRow {
            items(monthDays) { day ->
                val isSelected = date.dayOfMonth == day.dayOfMonth
                LemonDateSelector(
                    day = day,
                    isSelected = isSelected,
                    onClick = {
                        updateDate(LocalDate.of(LocalDate.now().year, day.month, day.dayOfMonth))
                    },
                    modifier = Modifier.padding(end = 15.dp)
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimePicker(
    modifier: Modifier = Modifier,
    time: LocalTime,
    updateTimes: (LocalTime) -> Unit
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
                color = MaterialTheme.colorScheme.onSurface,
            )
        }

        LazyRow {
            items(timeSlots) { timeIndex ->
                val isSelected = time == timeIndex
                LemonTimeSelector(
                    time = timeIndex,
                    isSelected = isSelected,
                    onClick = {
                        updateTimes(timeIndex)
                    },
                    modifier = Modifier.padding(end = 15.dp)
                )
            }
        }
    }
}

@Composable
fun DurationPicker(
    modifier: Modifier = Modifier,
    duration: String? = null,
    updateDuration: (String) -> Unit
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
                color = MaterialTheme.colorScheme.onSurface,
            )
        }

        LazyRow {
            items(durations) { durationIndex ->
                val isSelected = duration == durationIndex
                LemonDurationSelector(
                    duration = durationIndex,
                    onClick = {
                        updateDuration(durationIndex)
                    },
                    isSelected = isSelected,
                    modifier = Modifier.padding(end = 15.dp)
                )
            }
        }
    }
}

@Composable
fun NumberOfDinerPicker(
    modifier: Modifier = Modifier,
    diners: Int,
    updateNumberOfDiners: (Int) -> Unit,
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
                color = MaterialTheme.colorScheme.onSurface,
            )
        }

        LazyRow {
            items(numberOfDiners) { dinersIndex ->
                val isSelected = diners.toString() == dinersIndex
                LemonNumberOfDinersSelector(
                    numberOfDiners = dinersIndex,
                    onClick = {
                        updateNumberOfDiners(dinersIndex.toInt())
                    },
                    isSelected = isSelected,
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
        ReservationTableDetailsScreenContent(
            navController = rememberNavController(),
            formState = ReservationFormState(),
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ReservationTableDetailsScreenDarkPreview() {
    LittleLemonTheme {
        ReservationTableDetailsScreenContent(
            navController = rememberNavController(),
            formState = ReservationFormState(),
        )
    }
}