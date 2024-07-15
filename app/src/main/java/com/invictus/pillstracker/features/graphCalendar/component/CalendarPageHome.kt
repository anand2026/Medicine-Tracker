package com.invictus.pillstracker.features.graphCalendar.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.invictus.pillstracker.R
import com.invictus.pillstracker.features.graphCalendar.CalendarState
import com.invictus.pillstracker.features.graphCalendar.CalendarViewModel
import com.invictus.pillstracker.features.graphCalendar.component.common.CalenderOfCalendarPage
import com.invictus.pillstracker.utils.TimeConversionUtils.toLocalDate
import com.invictus.pillstracker.utils.composeUtils.theme.FFDBD7FF
import com.invictus.pillstracker.utils.composeUtils.theme.allCorner100
import com.invictus.pillstracker.utils.composeUtils.theme.appBackgroundColor
import com.invictus.pillstracker.utils.composeUtils.theme.primaryColor
import com.invictus.pillstracker.utils.composeUtils.theme.secondaryColor
import com.invictus.pillstracker.utils.composeUtils.theme.textColor
import com.invictus.pillstracker.utils.composeUtils.theme.typo12Bold
import com.invictus.pillstracker.utils.displayUnitConverter.UnitConverter.DP
import java.time.LocalDate

@Composable
fun CalendarPageHome() {

    val viewModel: CalendarViewModel = mavericksViewModel()
    val periodItemList by viewModel.collectAsState(CalendarState::periodItemList)

    val dayWiseColor = remember { mutableStateListOf<Pair<LocalDate, LocalDate>>() }

    LaunchedEffect(key1 = periodItemList) {
        dayWiseColor.clear()
        periodItemList.forEach {
            dayWiseColor.add(Pair(it.startDate.toLocalDate(),it.endDate.toLocalDate()))
        }
    }

    Surface(
        color = MaterialTheme.colors.appBackgroundColor,
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            bottomBar = {},
            topBar = {},
            backgroundColor = Color.Transparent,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.DP)
        ) { pad ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(pad)
            ) {
                item {
                    CalenderOfCalendarPage(dayWiseColor = dayWiseColor)
                }

                item {
                    Spacer(Modifier.height(30.DP))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ColorInfo(stringResource(R.string.menstruation), secondaryColor)
                        ColorInfo(stringResource(R.string.ovulation), primaryColor)
                    }

                    Spacer(Modifier.height(16.DP))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ColorInfo(stringResource(R.string.fertility_day), FFDBD7FF)
                    }

                }

            }
        }
    }
}

@Composable
fun ColorInfo(text: String, color: Color) {
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(20.DP)
                .background(
                    color,
                    MaterialTheme.shapes.allCorner100
                )
        )

        Spacer(Modifier.width(16.DP))

        Text(
            text = text,
            style = MaterialTheme.typography.typo12Bold,
            color = MaterialTheme.colors.textColor
        )

    }
}