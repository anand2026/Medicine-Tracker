package com.invictus.pillstracker.features.graphCalendar.data

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.invictus.pillstracker.R
import com.invictus.pillstracker.utils.composeUtils.calender.ContinuousSelectionHelper.isInDateBetweenSelection
import com.invictus.pillstracker.utils.composeUtils.calender.ContinuousSelectionHelper.isOutDateBetweenSelection
import com.invictus.pillstracker.utils.composeUtils.calender.DateSelection
import com.invictus.pillstracker.utils.composeUtils.modifierExtension.dashedBorder
import com.invictus.pillstracker.utils.composeUtils.theme.allCorner100
import com.invictus.pillstracker.utils.composeUtils.theme.secondaryColor
import com.invictus.pillstracker.utils.composeUtils.theme.textColor
import com.invictus.pillstracker.utils.displayUnitConverter.UnitConverter.DP
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import java.time.LocalDate

private class HalfSizeShape(private val clipStart: Boolean) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        val half = size.width / 2f
        val offset = if (layoutDirection == LayoutDirection.Ltr) {
            if (clipStart) Offset(half, 0f) else Offset.Zero
        } else {
            if (clipStart) Offset.Zero else Offset(half, 0f)
        }
        return Outline.Rectangle(Rect(offset, Size(half, size.height)))
    }
}

/**
 * Modern Airbnb highlight style, as seen in the app.
 * See also [backgroundHighlightLegacy].
 */
fun Modifier.backgroundHighlight(
    day: CalendarDay,
    today: LocalDate,
    selection: DateSelection,
    selectionColor: Color,
    continuousSelectionColor: Color,
    textColor: (Color) -> Unit,
): Modifier = composed {
    val (startDate, endDate) = selection
    val padding = 4.dp
    when (day.position) {
        DayPosition.MonthDate -> {
            when {
                day.date.isAfter(today) -> {
                    textColor(MaterialTheme.colors.textColor.copy(alpha = 0.3f))
                    this
                }
                startDate == day.date && endDate == null -> {
                    textColor(MaterialTheme.colors.textColor)
                    padding(padding)
                        .background(color = selectionColor, shape = CircleShape)
                }
                day.date == startDate -> {
                    textColor(Color.Black)
                    background(
                        secondaryColor,
                        MaterialTheme.shapes.allCorner100
                    )
                        .dashedBorder(1.DP, Color.White, 100.DP)
                }
                startDate != null && endDate != null && (day.date > startDate && day.date < endDate) -> {
                    textColor(Color.Black)
                        background(
                            secondaryColor,
                            MaterialTheme.shapes.allCorner100
                        )
                        .dashedBorder(1.DP, Color.White, 100.DP)
                }
                day.date == endDate -> {
                    textColor(Color.Black)
                    background(
                        secondaryColor,
                        MaterialTheme.shapes.allCorner100
                    )
                        .dashedBorder(1.DP, Color.White, 100.DP)
                }
                day.date == today -> {
                    textColor(MaterialTheme.colors.textColor)
                    padding(padding)
                        .border(
                            width = 1.dp,
                            shape = CircleShape,
                            color = MaterialTheme.colors.textColor,
                        )
                }
                else -> {
                    textColor(MaterialTheme.colors.textColor)
                    this
                }
            }
        }
        DayPosition.InDate -> {
            textColor(Color.Transparent)
            if (startDate != null && endDate != null &&
                isInDateBetweenSelection(day.date, startDate, endDate)
            ) {
                padding(vertical = padding)
            } else this
        }
        DayPosition.OutDate -> {
            textColor(Color.Transparent)
            if (startDate != null && endDate != null &&
                isOutDateBetweenSelection(day.date, startDate, endDate)
            ) {
                padding(vertical = padding)
            } else this
        }
    }
}

/**
 * Old Airbnb highlight style.
 * See also [backgroundHighlight].
 */
fun Modifier.backgroundHighlightLegacy(
    day: CalendarDay,
    today: LocalDate,
    selection: DateSelection,
    selectionColor: Color,
    textColor: (Color) -> Unit,
): Modifier = composed {
    val (startDate, endDate) = selection
    val padding = 4.dp
    when (day.position) {
        DayPosition.MonthDate -> {
            when {
                day.date.isBefore(today) -> {
                    textColor(colorResource(R.color.white))
                    this
                }
                startDate == day.date && endDate == null -> {
                    textColor(Color.White)
                    padding(padding)
                        .background(color = selectionColor, shape = CircleShape)
                }
                day.date == startDate -> {
                    textColor(Color.White)
                    padding(vertical = padding)
                        .background(
                            color = selectionColor,
                            shape = RoundedCornerShape(
                                topStartPercent = 50,
                                bottomStartPercent = 50,
                            ),
                        )
                }
                startDate != null && endDate != null && (day.date > startDate && day.date < endDate) -> {
                    textColor(Color.White)
                    padding(vertical = padding)
                        .background(color = selectionColor)
                }
                day.date == endDate -> {
                    textColor(Color.White)
                    padding(vertical = padding)
                        .background(
                            color = selectionColor,
                            shape = RoundedCornerShape(topEndPercent = 50, bottomEndPercent = 50),
                        )
                }
                day.date == today -> {
                    textColor(Color.Gray)
                    padding(padding)
                        .border(
                            width = 1.dp,
                            shape = CircleShape,
                            color = colorResource(R.color.white),
                        )
                }
                else -> {
                    textColor(Color.Gray)
                    this
                }
            }
        }
        DayPosition.InDate -> {
            textColor(Color.Transparent)
            if (startDate != null && endDate != null &&
                isInDateBetweenSelection(day.date, startDate, endDate)
            ) {
                padding(vertical = padding)
                    .background(color = selectionColor)
            } else this
        }
        DayPosition.OutDate -> {
            textColor(Color.Transparent)
            if (startDate != null && endDate != null &&
                isOutDateBetweenSelection(day.date, startDate, endDate)
            ) {
                padding(vertical = padding)
                    .background(color = selectionColor)
            } else this
        }
    }
}
