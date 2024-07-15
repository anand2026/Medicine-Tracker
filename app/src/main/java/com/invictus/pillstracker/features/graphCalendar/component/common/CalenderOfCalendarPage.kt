package com.invictus.pillstracker.features.graphCalendar.component.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.invictus.pillstracker.data.sharedPrefs.PillsTrackerSharedPrefs
import com.invictus.pillstracker.features.graphCalendar.data.CalendarDayColorData
import com.invictus.pillstracker.features.graphCalendar.utils.CalendarUtils
import com.invictus.pillstracker.utils.composeUtils.calender.SimpleCalendarTitle
import com.invictus.pillstracker.utils.composeUtils.calender.displayText
import com.invictus.pillstracker.utils.composeUtils.calender.rememberFirstMostVisibleMonth
import com.invictus.pillstracker.utils.composeUtils.modifierExtension.dashedBorder
import com.invictus.pillstracker.utils.composeUtils.theme.FFDBD7FF
import com.invictus.pillstracker.utils.composeUtils.theme.allCorner10
import com.invictus.pillstracker.utils.composeUtils.theme.allCorner100
import com.invictus.pillstracker.utils.composeUtils.theme.calendarTopBarColor
import com.invictus.pillstracker.utils.composeUtils.theme.primaryColor
import com.invictus.pillstracker.utils.composeUtils.theme.secondaryColor
import com.invictus.pillstracker.utils.composeUtils.theme.textColor
import com.invictus.pillstracker.utils.composeUtils.theme.typo12Bold
import com.invictus.pillstracker.utils.displayUnitConverter.UnitConverter.DP
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun CalenderOfCalendarPage(adjacentMonths: Long = 500, dayWiseColor: SnapshotStateList<Pair<LocalDate, LocalDate>>) {
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(adjacentMonths) }
    val endMonth = remember { currentMonth.plusMonths(adjacentMonths) }
    val selections = remember { mutableStateListOf<CalendarDay>() }
    val daysOfWeek = remember { daysOfWeek() }
    var dayWiseColorLastDate = dayWiseColor.lastOrNull()?.first

    dayWiseColorLastDate = dayWiseColorLastDate?.plusDays(PillsTrackerSharedPrefs.CYCLE_LENGTH.toLong())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent),
    ) {
        val state = rememberCalendarState(
            startMonth = startMonth,
            endMonth = endMonth,
            firstVisibleMonth = currentMonth,
            firstDayOfWeek = daysOfWeek.first(),
        )
        val coroutineScope = rememberCoroutineScope()
        val visibleMonth = rememberFirstMostVisibleMonth(state, viewportPercent = 90f)
        SimpleCalendarTitle(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 8.dp),
            currentMonth = visibleMonth.yearMonth,
            goToPrevious = {
                coroutineScope.launch {
                    state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.previousMonth)
                }
            },
            goToNext = {
                coroutineScope.launch {
                    state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.nextMonth)
                }
            },
        )
        HorizontalCalendar(
            modifier = Modifier.testTag("Calendar"),
            state = state,
            dayContent = { day ->

                val periodStartDateForUpcomingMonths = CalendarUtils.getPeriodStartDateForUpcomingMonth(dayWiseColorLastDate,day.date)

                val ovulationCalc = CalendarUtils.ovulationCalculation(dayWiseColorLastDate)

                val isOvulationDay = CalendarUtils.isOvulationDay(ovulationCalc,day.date)

                val isFertilityWindowBefore = CalendarUtils.isFertilityWindowBefore(ovulationCalc,day.date)

                val isFertilityWindowAfter = CalendarUtils.isFertilityWindowAfter(ovulationCalc,day.date)

                val isOtherPreviousCycles = CalendarUtils.isOtherPreviousCycles(dayWiseColor,day.date)


                Day(
                    day,
                    selections.contains(day),
                    isOtherPreviousCycles,
                    periodStartDateForUpcomingMonths,
                    isOvulationDay,
                    isFertilityWindowBefore || isFertilityWindowAfter
                )
                { clicked ->
                    if (selections.contains(clicked)) {
                        selections.remove(clicked)
                    } else {
                        selections.add(clicked)
                    }
                }
            },
            monthHeader = {
                MonthHeader(daysOfWeek = daysOfWeek)
            },
        )
    }
}

@Composable
private fun MonthHeader(daysOfWeek: List<DayOfWeek>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.DP)
            .background(MaterialTheme.colors.calendarTopBarColor, MaterialTheme.shapes.allCorner10)
            .padding(10.DP)
            .testTag("MonthHeader"),
    ) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                fontSize = 15.sp,
                text = dayOfWeek.displayText(),
                fontWeight = FontWeight.Medium,
            )
        }
    }
}

@Composable
private fun Day(
    day: CalendarDay,
    isSelected: Boolean,
    isOtherPreviousCycles: Boolean,
    periodStartDates: Boolean,
    isOvulationDay: Boolean,
    isFertilityWindow: Boolean,
    onClick: (CalendarDay) -> Unit
) {

    var selectedColor = when {
        day.position == DayPosition.InDate || day.position == DayPosition.OutDate -> CalendarDayColorData(Color.Transparent, Color.Transparent, 0.DP, Color.Transparent)
        isOtherPreviousCycles || periodStartDates -> CalendarDayColorData(secondaryColor, Color.White, 1.DP, Color.White)
        isOvulationDay -> CalendarDayColorData(primaryColor, primaryColor, 0.DP, Color.White)
        isFertilityWindow -> CalendarDayColorData(FFDBD7FF, FFDBD7FF, 0.DP, Color.Black)
        else -> CalendarDayColorData(Color.Transparent, Color.Transparent, 0.DP, MaterialTheme.colors.textColor)
    }
    if(LocalDate.now().atStartOfDay().equals(day.date.atStartOfDay()) && selectedColor.textColor != Color.Transparent){
        selectedColor.dashedColor = Color.Red
        selectedColor.dashedWidth = 2.DP
    }


    Box(
        modifier = Modifier
            .aspectRatio(1f)
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

//@Preview
//@Composable
//private fun Example1Preview() {
//    Example1Page()
//}
