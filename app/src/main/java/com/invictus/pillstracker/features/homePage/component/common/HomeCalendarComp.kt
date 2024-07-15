package com.invictus.pillstracker.features.homePage.component.common

import android.content.res.Resources
import android.util.DisplayMetrics
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.invictus.pillstracker.features.graphCalendar.data.CalendarDayColorData
import com.invictus.pillstracker.features.graphCalendar.utils.CalendarUtils
import com.invictus.pillstracker.utils.composeUtils.modifierExtension.dashedBorder
import com.invictus.pillstracker.utils.composeUtils.theme.FFDBD7FF
import com.invictus.pillstracker.utils.composeUtils.theme.FFF24187
import com.invictus.pillstracker.utils.composeUtils.theme.FFFFE2EC
import com.invictus.pillstracker.utils.composeUtils.theme.allCorner100
import com.invictus.pillstracker.utils.composeUtils.theme.primaryColor
import com.invictus.pillstracker.utils.composeUtils.theme.secondaryColor
import com.invictus.pillstracker.utils.composeUtils.theme.textColor
import com.invictus.pillstracker.utils.composeUtils.theme.typo10Normal
import com.invictus.pillstracker.utils.composeUtils.theme.typo12Bold
import com.invictus.pillstracker.utils.composeUtils.theme.typo12Normal
import com.invictus.pillstracker.utils.displayUnitConverter.UnitConverter.DP
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.WeekDay
import timber.log.Timber
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Composable
fun HorizontalDateForHomePage(
    currentDate: LocalDate,
    selectedMonth: MutableState<LocalDate>,
    dayWiseColor: SnapshotStateList<Pair<LocalDate, LocalDate>>,
) {
    val cycleLength = CalendarUtils.getCycleLength().toLong()
    val startDate = remember { currentDate.minusDays(cycleLength/2) }
    val endDate = remember { currentDate.plusDays(cycleLength/2) }
    var selection by remember { mutableStateOf<LocalDate?>(null) }
//    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    val resources: Resources = LocalContext.current.resources
    val metrics: DisplayMetrics = resources.displayMetrics

    val state = rememberWeekCalendarState(
        startDate = startDate,
        endDate = endDate,
        firstVisibleWeekDate = currentDate,
    )

    LaunchedEffect(key1 = Unit){
        try{
            state.animateScrollToWeek(currentDate)
        }catch (e:Exception){
            Timber.d("==>HorizontalDateForHomePage_86 $e")
        }
    }

    LaunchedEffect(key1 = state.firstVisibleWeek){
        selectedMonth.value =  state.firstVisibleWeek.days.first().date
    }

    var dayWiseColorLastDate = dayWiseColor.lastOrNull()?.first



    dayWiseColorLastDate = dayWiseColorLastDate?.plusDays(cycleLength)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent),
    ) {
        // Draw light content on dark background.
        WeekCalendar(
                modifier = Modifier.padding(vertical = 4.dp),
                state = state,
                calendarScrollPaged = false,
                dayContent = { day ->



                    val periodStartDateForUpcomingMonths = CalendarUtils.getPeriodStartDateForUpcomingMonth(dayWiseColorLastDate,day.date)

                    val ovulationCalc = CalendarUtils.ovulationCalculation(dayWiseColorLastDate)

                    val isOvulationDay = CalendarUtils.isOvulationDay(ovulationCalc,day.date)

                    val isFertilityWindowBefore = CalendarUtils.isFertilityWindowBefore(ovulationCalc,day.date)

                    val isFertilityWindowAfter = CalendarUtils.isFertilityWindowAfter(ovulationCalc,day.date)

                    val isOtherPreviousCycles = CalendarUtils.isOtherPreviousCycles(dayWiseColor,day.date)

                    Day(
                        day,
                        false,
                        isOtherPreviousCycles,
                        periodStartDateForUpcomingMonths,
                        isOvulationDay,
                        isFertilityWindowBefore || isFertilityWindowAfter
                    )
                    { clicked ->
//                        if (selections.contains(clicked)) {
//                            selections.remove(clicked)
//                        } else {
//                            selections.add(clicked)
//                        }
                    }
                },
            )
    }
}

private val dateFormatter = DateTimeFormatter.ofPattern("dd")
val monthYearFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMMM yyyy")

@Composable
private fun Day(
    day: WeekDay,
    isSelected: Boolean,
    isOtherPreviousCycles: Boolean,
    periodStartDates: Boolean,
    isOvulationDay: Boolean,
    isFertilityWindow: Boolean,
    onClick: (CalendarDay) -> Unit
) {

    var selectedColor = when {
//        day.position == DayPosition.InDate || day.position == DayPosition.OutDate -> CalendarDayColorData(Color.Transparent, Color.Transparent, 0.DP, Color.Transparent)
        isOtherPreviousCycles || periodStartDates -> CalendarDayColorData(secondaryColor, Color.White, 1.DP, Color.White)
        isOvulationDay -> CalendarDayColorData(primaryColor, primaryColor, 0.DP, Color.White)
        isFertilityWindow -> CalendarDayColorData(FFDBD7FF, FFDBD7FF, 0.DP, Color.Black)
        else -> CalendarDayColorData(Color.White, Color.White, 0.DP, Color.Black)
    }
    if(LocalDate.now().atStartOfDay().equals(day.date.atStartOfDay())){
        selectedColor.dashedColor = Color.Red
        selectedColor.dashedWidth = 2.DP
    }


    Box(
        modifier = Modifier
            .padding(3.DP)
            .background(
                selectedColor.dayColor,
                MaterialTheme.shapes.allCorner100
            )
            .dashedBorder(selectedColor.dashedWidth, selectedColor.dashedColor, 100.DP)
            .size(50.DP),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = day.date.dayOfMonth.toString(),
            style = MaterialTheme.typography.typo12Bold,
            color = selectedColor.textColor,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}


@Composable
fun DayComponent(
    dayPassedCount: Int = 5,
    date: String = "9",
    isCurrentDay: Boolean = false,
    isAfterCurrentDay: Boolean = true,
    day: String = "T",
) {


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(4.DP)
    ) {
        Box {
            Box(
                modifier = Modifier
                    .background(
                        if (isCurrentDay) secondaryColor else if (isAfterCurrentDay) Color.White else FFFFE2EC,
                        MaterialTheme.shapes.allCorner100
                    )
                    .dashedBorder(if (isAfterCurrentDay) 0.DP else 1.DP, if (isAfterCurrentDay) Color.White else FFF24187, 100.DP)
                    .size(50.DP)
            ) {
                Text(
                    text = date,
                    style = MaterialTheme.typography.typo12Bold,
                    color = if (isCurrentDay) Color.White else if (isAfterCurrentDay) Color.Black else FFF24187,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            if (dayPassedCount != 0 && !isAfterCurrentDay) {
                Box(
                    modifier = Modifier
                        .background(
                            FFF24187,
                            MaterialTheme.shapes.allCorner100
                        )
                        .size(15.DP)
                        .align(Alignment.TopStart)
                ) {
                    Text(
                        text = dayPassedCount.toString(),
                        style = MaterialTheme.typography.typo10Normal,
                        color = Color.White,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }

        Spacer(Modifier.height(10.DP))

        Text(
            text = day,
            style = MaterialTheme.typography.typo12Normal,
            color = MaterialTheme.colors.textColor
        )
    }
}

