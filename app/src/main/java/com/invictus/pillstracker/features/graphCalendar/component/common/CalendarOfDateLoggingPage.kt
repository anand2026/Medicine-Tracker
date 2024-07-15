package com.invictus.pillstracker.features.graphCalendar.component.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.invictus.pillstracker.R
import com.invictus.pillstracker.data.sharedPrefs.PillsTrackerSharedPrefs
import com.invictus.pillstracker.features.graphCalendar.data.backgroundHighlight
import com.invictus.pillstracker.utils.composeUtils.calender.ContinuousSelectionHelper.getSelection
import com.invictus.pillstracker.utils.composeUtils.calender.DateSelection
import com.invictus.pillstracker.utils.composeUtils.calender.StatusBarColorUpdateEffect
import com.invictus.pillstracker.utils.composeUtils.calender.displayText
import com.invictus.pillstracker.utils.composeUtils.theme.allCorner10
import com.invictus.pillstracker.utils.composeUtils.theme.calendarTopBarColor
import com.invictus.pillstracker.utils.composeUtils.theme.extensions.noRippleClickable
import com.invictus.pillstracker.utils.composeUtils.theme.secondaryColor
import com.invictus.pillstracker.utils.composeUtils.theme.textColor
import com.invictus.pillstracker.utils.composeUtils.theme.typo12Bold
import com.invictus.pillstracker.utils.displayUnitConverter.UnitConverter.DP
import com.kizitonwose.calendar.compose.VerticalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth


@Composable
fun CalendarOfDateLoggingPage(
    isNeedToAskPeriodStart: Boolean,
    close: () -> Unit = {},
    dateSelected: (startDate: LocalDate, endDate: LocalDate) -> Unit = { _, _ -> },
) {
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(3) }
    val endMonth = remember { currentMonth }
    val today = remember { LocalDate.now() }
    var selection by remember { mutableStateOf(DateSelection()) }
    val daysOfWeek = remember { daysOfWeek() }


    StatusBarColorUpdateEffect(Color.White)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent),
        ) {
            Column {
                val state = rememberCalendarState(
                    startMonth = startMonth,
                    endMonth = endMonth,
                    firstVisibleMonth = currentMonth,
                    firstDayOfWeek = daysOfWeek.first(),
                )
                CalendarTop(
                    daysOfWeek = daysOfWeek,
                    selection = selection,
                    close = close,
                    clearDates = { selection = DateSelection() },
                )
                VerticalCalendar(
                    state = state,
                    contentPadding = PaddingValues(bottom = 100.dp),
                    dayContent = { value ->
                        Day(
                            value,
                            today = today,
                            selection,
                        ) { day ->

                            if (day.position == DayPosition.MonthDate &&
                                (day.date == today || day.date.isBefore(today))
                            ) {
                                if(isNeedToAskPeriodStart) {
                                    selection = getSelection(
                                        clickedDate = day.date,
                                        dateSelection = selection,
                                    )
                                    selection = getSelection(
                                        clickedDate = day.date.plusDays(PillsTrackerSharedPrefs.PERIOD_LENGTH.toLong()-1),
                                        dateSelection = selection,
                                    )
                                }else{
                                    selection = getSelection(
                                        clickedDate = day.date,
                                        dateSelection = selection,
                                    )
                                }
                            }
                        }
                    },
                    monthHeader = { month -> MonthHeader(month) },
                )
            }
            CalendarBottom(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .background(Color.Transparent)
                    .align(Alignment.BottomCenter),
                selection = selection,
                save = {
                    val (startDate, endDate) = selection
                    if(startDate != null && isNeedToAskPeriodStart) {
                        dateSelected(startDate,startDate.plusDays(PillsTrackerSharedPrefs.PERIOD_LENGTH.toLong()))
                    }else if (startDate != null && endDate != null) {
                        dateSelected(startDate, endDate)
                    }
                },
            )
        }
}


@Composable
private fun Day(day: CalendarDay, today: LocalDate, selection: DateSelection,onClick: (CalendarDay) -> Unit) {

    var textColor = Color.Transparent
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .noRippleClickable {
                onClick(day)
            }.semantics { this.contentDescription = "Day" }
            .padding(3.DP)
            .backgroundHighlight(
                day = day,
                today = today,
                selection = selection,
                selectionColor = secondaryColor,
                continuousSelectionColor = MaterialTheme.colors.textColor.copy(alpha = 0.3f),
            ) { textColor = it }
            .size(50.DP)
        ,
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = day.date.dayOfMonth.toString(),
            style = MaterialTheme.typography.typo12Bold,
            color = textColor,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun MonthHeader(calendarMonth: CalendarMonth) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, bottom = 8.dp, start = 16.dp, end = 16.dp),
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = calendarMonth.yearMonth.displayText(),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.textColor
        )
    }
}

@Composable
private fun CalendarTop(
    modifier: Modifier = Modifier,
    daysOfWeek: List<DayOfWeek>,
    selection: DateSelection,
    close: () -> Unit,
    clearDates: () -> Unit,
) {
    Column(modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp, bottom = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Row(
                modifier = Modifier.height(IntrinsicSize.Max),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f)
                        .clip(CircleShape)
                        .clickable(onClick = close)
                        .padding(12.dp),
                    painter = painterResource(id = R.drawable.ic_baseline_close_24),
                    contentDescription = "Close",
                    tint = MaterialTheme.colors.textColor
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    modifier = Modifier
                        .clip(RoundedCornerShape(percent = 50))
                        .clickable(onClick = clearDates)
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    text = "Clear",
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.End,
                    color = MaterialTheme.colors.textColor
                )
            }
            val daysBetween = selection.daysBetween
            val text = if (daysBetween == null) {
                stringResource(R.string.select_days)
            } else {
                // Ideally you'd do this using the strings.xml file
                "${daysBetween+1} ${if (daysBetween == 1L) stringResource(R.string.day) else stringResource(R.string.days)} "
            }
            Text(
                modifier = Modifier.padding(horizontal = 14.dp),
                text = text,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = MaterialTheme.colors.textColor
            )
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
                        color = MaterialTheme.colors.textColor
                    )
                }
            }
        }
        Divider()
    }
}

@Composable
private fun CalendarBottom(
    modifier: Modifier = Modifier,
    selection: DateSelection,
    save: () -> Unit,
) {
    Column(modifier.fillMaxWidth()) {
        Divider()
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Button(
                modifier = Modifier
                    .height(40.dp)
                    .width(100.dp),
                onClick = save,
                enabled = selection.daysBetween != null,
            ) {
                Text(text = "Save")
            }
        }
    }
}

