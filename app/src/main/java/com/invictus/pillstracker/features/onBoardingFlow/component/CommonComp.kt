package com.invictus.pillstracker.features.onBoardingFlow.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Divider
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.invictus.pillstracker.R
import com.invictus.pillstracker.features.graphCalendar.data.CalendarDayColorData
import com.invictus.pillstracker.utils.composeUtils.calender.SimpleCalendarTitle
import com.invictus.pillstracker.utils.composeUtils.calender.clickable
import com.invictus.pillstracker.utils.composeUtils.calender.displayText
import com.invictus.pillstracker.utils.composeUtils.calender.rememberFirstMostVisibleMonth
import com.invictus.pillstracker.utils.composeUtils.modifierExtension.dashedBorder
import com.invictus.pillstracker.utils.composeUtils.theme.allCorner10
import com.invictus.pillstracker.utils.composeUtils.theme.allCorner100
import com.invictus.pillstracker.utils.composeUtils.theme.calendarTopBarColor
import com.invictus.pillstracker.utils.composeUtils.theme.primaryColor
import com.invictus.pillstracker.utils.composeUtils.theme.secondaryColor
import com.invictus.pillstracker.utils.composeUtils.theme.textColor
import com.invictus.pillstracker.utils.composeUtils.theme.typo12Bold
import com.invictus.pillstracker.utils.composeUtils.theme.typo30Bold
import com.invictus.pillstracker.utils.displayUnitConverter.UnitConverter.DP
import com.invictus.pillstracker.utils.displayUnitConverter.UnitConverter.SP
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import splitties.resources.appStr
import splitties.toast.toast
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth


/** ---------------------------------------------------- Calendar ----------------------------------------------------**/

@Composable
fun OnBoardingCalendar(adjacentMonths: Long = 500, selections: SnapshotStateList<CalendarDay>) {
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(adjacentMonths) }
    val endMonth = remember { currentMonth }
    val daysOfWeek = remember { daysOfWeek() }


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

                Day(
                    day,
                    selections.contains(day),
                )
                { clicked ->

                    if(day.date.isAfter(LocalDate.now())){
                        toast(appStr(R.string.select_date_before_today))
                    }else if (selections.contains(clicked)) {
                        selections.remove(clicked)
                    } else {
                        selections.clear()
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
                color = MaterialTheme.colors.textColor
            )
        }
    }
}

@Composable
private fun Day(
    day: CalendarDay,
    isSelected: Boolean,
    onClick: (CalendarDay) -> Unit
) {

    var selectedColor = when {
        day.position == DayPosition.InDate || day.position == DayPosition.OutDate -> CalendarDayColorData(Color.Transparent, Color.Transparent, 0.DP, Color.Transparent)
        isSelected -> CalendarDayColorData(secondaryColor, Color.White, 1.DP, Color.White)
        else -> CalendarDayColorData(Color.Transparent, Color.Transparent, 0.DP, MaterialTheme.colors.textColor)
    }
    if(LocalDate.now().atStartOfDay().equals(day.date.atStartOfDay())){
        selectedColor.dashedColor = Color.Red
        selectedColor.dashedWidth = 2.DP
    }
    if(day.date.isAfter(LocalDate.now())){
        selectedColor.textColor = MaterialTheme.colors.textColor.copy(alpha = 0.3f)
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
            .size(50.DP)
            .clickable {
                onClick(day)
            }.semantics { this.contentDescription = "Day Selection" }
        ,
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



/** ---------------------------------------------------- Picker ----------------------------------------------------**/


@Composable
fun rememberPickerState() = remember { PickerState() }

class PickerState {
    var selectedItem by mutableStateOf("")
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Picker(
    items: List<String>,
    state: PickerState = rememberPickerState(),
    modifier: Modifier = Modifier,
    startIndex: Int = 0,
    visibleItemsCount: Int = 3,
    textModifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current,
    dividerColor: Color = secondaryColor,
) {

    val visibleItemsMiddle = visibleItemsCount / 2
    val listScrollCount = Integer.MAX_VALUE
    val listScrollMiddle = listScrollCount / 2
    val listStartIndex =
        listScrollMiddle - listScrollMiddle % items.size - visibleItemsMiddle + startIndex

    fun getItem(index: Int) = items[index % items.size]

    val listState = rememberLazyListState(initialFirstVisibleItemIndex = listStartIndex)
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    val itemHeightPixels = remember { mutableStateOf(0) }
    val itemHeightDp = pixelsToDp(itemHeightPixels.value)

    val fadingEdgeGradient = remember {
        Brush.verticalGradient(
            0f to Color.Transparent,
            0.5f to Color.Black,
            1f to Color.Transparent
        )
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .map { index -> getItem(index + visibleItemsMiddle) }
            .distinctUntilChanged()
            .collect { item -> state.selectedItem = item }
    }

    Box(modifier = modifier) {

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            LazyColumn(
                state = listState,
                flingBehavior = flingBehavior,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
//                    .fillMaxWidth()
                    .height(itemHeightDp * visibleItemsCount)

//                .fadingEdge(fadingEdgeGradient)
            ) {
                items(listScrollCount) { index ->
                    Text(
                        text = getItem(index),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = textStyle,
                        color = if(state.selectedItem == getItem(index)) primaryColor else MaterialTheme.colors.textColor,
                        modifier = Modifier
                            .onSizeChanged { size -> itemHeightPixels.value = size.height }
                            .then(textModifier)
                    )
                }
            }

            Text(
                text = stringResource(R.string.days),
                style = MaterialTheme.typography.typo30Bold,
                color = primaryColor
            )

        }

        Divider(
            color = dividerColor,
            modifier = Modifier.offset(y = itemHeightDp * visibleItemsMiddle)
        )

        Divider(
            color = dividerColor,
            modifier = Modifier.offset(y = itemHeightDp * (visibleItemsMiddle + 1))
        )

    }

}


@Composable
fun pixelsToDp(pixels: Int) = with(LocalDensity.current) { pixels.toDp() }

@Composable
fun ClickableTextBetweenSentences(
    fullSentence: String,
    clickableTexts: List<String>,
    fullTextColor: Color = colorResource(id = R.color.approve_terms_message),
    clickableTextColor: Color = secondaryColor,
    fontSize: TextUnit = 12.SP,
    fontWeight: FontWeight = FontWeight.Normal,
    lineHeight: TextUnit = 14.SP,
    onClick: (String) -> Unit
) {

    val annotatedString = buildAnnotatedString {
        append(fullSentence)

        clickableTexts.forEach {
            addStyle(
                style = SpanStyle(
                    color = clickableTextColor,
                    fontSize = fontSize,
                    textDecoration = TextDecoration.Underline,
                ),
                start = fullSentence.indexOf(it),
                end = fullSentence.indexOf(it) + it.length,
            )

            addStringAnnotation(tag = "URL", annotation = it, start = fullSentence.indexOf(it), end = fullSentence.indexOf(it) + it.length)
        }
    }

    ClickableText(
        text = annotatedString,
        style = MaterialTheme.typography.body1.copy(
            color = fullTextColor,
            fontSize = fontSize,
            textAlign = TextAlign.Center,
            lineHeight = lineHeight,
            fontWeight = fontWeight
        ),
        onClick = { offset ->
            annotatedString.getStringAnnotations("URL", start = offset, end = offset)
                .firstOrNull()?.let { annotation ->
                    onClick.invoke(annotation.item)
                }
        }
    )
}