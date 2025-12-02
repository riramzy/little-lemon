package com.example.littlelemon.ui.screens.reservation

import android.content.res.Configuration
import android.os.Build
import android.widget.Toast
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
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.littlelemon.ui.components.LemonDateSelector
import com.example.littlelemon.ui.components.LemonDurationSelector
import com.example.littlelemon.ui.components.LemonNavigationBar
import com.example.littlelemon.ui.components.LemonNumberOfDinersSelector
import com.example.littlelemon.ui.components.LemonTimeSelector
import com.example.littlelemon.ui.components.TopAppBar
import com.example.littlelemon.ui.theme.LittleLemonTheme
import com.example.littlelemon.utils.Screen
import java.time.LocalDate
import java.time.LocalTime
import java.time.MonthDay
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReservationTableDetailsScreen(
    onNextClicked: () -> Unit = {},
    vm: ReservationVm,
    navController: NavHostController,
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
                        vm.selectedDate.value == null ||
                        vm.selectedTime.value == null ||
                        vm.selectedDuration.value == null ||
                        vm.selectedNumberOfDiners.value == null
                    ) {
                        Toast.makeText(
                            context,
                            "Please select all fields",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        onNextClicked()
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
                    ),
                    vm = vm
                )
            }
            item {
                TimePicker(
                    modifier = Modifier.padding(
                        horizontal = 15.dp
                    ),
                    vm = vm
                )
            }
            item {
                DurationPicker(
                    modifier = Modifier.padding(
                        horizontal = 15.dp
                    ),
                    vm = vm
                )
            }
            item {
                NumberOfDinerPicker(
                    modifier = Modifier.padding(
                        horizontal = 15.dp
                    ),
                    vm = vm
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
    modifier: Modifier = Modifier,
    vm: ReservationVm
) {
    val currentMonth = YearMonth.now()
    val currentDay = MonthDay.now().dayOfMonth
    val daysInMonth = currentMonth.lengthOfMonth()
    val monthDays = (currentDay..daysInMonth).map { day ->
        MonthDay.of(currentMonth.month, day)
    }

    val selectedDate = vm.selectedDate.value

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
                val isSelected = selectedDate?.dayOfMonth == day.dayOfMonth
                LemonDateSelector(
                    day = day,
                    isSelected = isSelected,
                    onClick = {
                        vm.selectedDate.value = LocalDate.of(LocalDate.now().year, day.month, day.dayOfMonth)
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
    vm: ReservationVm
) {
    val selectedTime = vm.selectedTime.value

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
                val isSelected = selectedTime == time
                LemonTimeSelector(
                    time = time,
                    isSelected = isSelected,
                    onClick = {
                        vm.selectedTime.value = time
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
    vm: ReservationVm
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
                val isSelected = vm.selectedDuration.value == duration
                LemonDurationSelector(
                    duration = duration,
                    onClick = {
                        vm.selectedDuration.value = duration
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
    vm: ReservationVm
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
                val isSelected = vm.selectedNumberOfDiners.value.toString() == diners
                LemonNumberOfDinersSelector(
                    numberOfDiners = diners,
                    onClick = {
                        vm.selectedNumberOfDiners.value = diners.toInt()
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
        ReservationTableDetailsScreen(
            vm = ReservationVm(),
            navController = rememberNavController()
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ReservationTableDetailsScreenDarkPreview() {
    LittleLemonTheme {
        ReservationTableDetailsScreen(
            vm = ReservationVm(),
            navController = rememberNavController()
        )
    }
}