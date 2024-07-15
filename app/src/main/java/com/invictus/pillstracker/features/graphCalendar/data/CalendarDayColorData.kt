package com.invictus.pillstracker.features.graphCalendar.data

import androidx.annotation.Keep
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

@Keep
data class CalendarDayColorData(
    val dayColor: Color,
    var dashedColor: Color,
    var dashedWidth: Dp,
    var textColor: Color,
)
