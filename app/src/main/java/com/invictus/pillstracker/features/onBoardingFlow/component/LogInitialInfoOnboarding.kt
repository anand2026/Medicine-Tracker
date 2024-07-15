package com.invictus.pillstracker.features.onBoardingFlow.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.invictus.pillstracker.R
import com.invictus.pillstracker.data.database.periodTrack.PeriodTrackModel
import com.invictus.pillstracker.data.database.periodTrack.PeriodTrackerValues
import com.invictus.pillstracker.data.sharedPrefs.PillsTrackerSharedPrefs
import com.invictus.pillstracker.features.mainActivityPage.MainActivityPageViewModel
import com.invictus.pillstracker.features.mainActivityPage.data.BottomNavItemsIdentifiers
import com.invictus.pillstracker.utils.TimeConversionUtils.atStartOfDayMillis
import com.invictus.pillstracker.utils.composeUtils.calender.clickable
import com.invictus.pillstracker.utils.composeUtils.theme.TimerProgressColor
import com.invictus.pillstracker.utils.composeUtils.theme.allCorner100
import com.invictus.pillstracker.utils.composeUtils.theme.appBackgroundColor
import com.invictus.pillstracker.utils.composeUtils.theme.extensions.noRippleClickable
import com.invictus.pillstracker.utils.composeUtils.theme.primaryColor
import com.invictus.pillstracker.utils.composeUtils.theme.secondaryColor
import com.invictus.pillstracker.utils.composeUtils.theme.textColor
import com.invictus.pillstracker.utils.composeUtils.theme.typo12Normal
import com.invictus.pillstracker.utils.composeUtils.theme.typo15SemiBold
import com.invictus.pillstracker.utils.composeUtils.theme.typo22SemiBold
import com.invictus.pillstracker.utils.composeUtils.theme.typo30Bold
import com.invictus.pillstracker.utils.displayUnitConverter.UnitConverter.DP
import com.kizitonwose.calendar.core.CalendarDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import splitties.resources.appStr
import splitties.toast.toast

@Composable
fun LogInitialInfoOnboarding(mainActivityPageViewModel: MainActivityPageViewModel) {

    val valuesPickerState1 = rememberPickerState()
    val valuesPickerState2 = rememberPickerState()
    val questionList = remember { arrayListOf(appStr(R.string.how_long_is_your_menstrual_cycle), appStr(R.string.how_long_is_your_period), appStr(R.string.when_did_your_last_period_start)) }
    val infoList = remember { arrayListOf(appStr(R.string.cycle_length_info), appStr(R.string.period_length_info), "") }

    val optionList = remember {
        arrayListOf(
            ArrayList<Int>().apply { addAll(15..70) }.map { it.toString() },
            ArrayList<Int>().apply { addAll(2..15) }.map { it.toString() },
        )
    }
    val selectedIndex = remember { mutableIntStateOf(0) }

    val daySelection = remember { mutableStateListOf<CalendarDay>() }

    Surface(
        color = MaterialTheme.colors.appBackgroundColor
    ) {
        Scaffold(
            topBar = {
                if (selectedIndex.value != 0) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = "Back Button",
                        tint = MaterialTheme.colors.textColor,
                        modifier = Modifier
                            .size(24.DP)
                            .clickable {
                                selectedIndex.value = selectedIndex.value - 1
                            }.semantics { this.contentDescription = "Back Button Item" }
                    )
                }
            },
            bottomBar = {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .noRippleClickable {
                                when (selectedIndex.value) {
                                    0 -> {
                                        if (valuesPickerState1.selectedItem.isEmpty()) {
                                            toast(appStr(R.string.please_select_a_value))
                                        } else {
                                            PillsTrackerSharedPrefs.CYCLE_LENGTH = valuesPickerState1.selectedItem.toInt()
                                            selectedIndex.value = selectedIndex.value + 1
                                        }
                                    }

                                    1 -> {
                                        if (valuesPickerState2.selectedItem.isEmpty()) {
                                            toast(appStr(R.string.please_select_a_value))
                                        } else {
                                            PillsTrackerSharedPrefs.PERIOD_LENGTH = valuesPickerState2.selectedItem.toInt()
                                            selectedIndex.value = selectedIndex.value + 1
                                        }
                                    }

                                    2 -> {
                                        if (daySelection.isNotEmpty()) {
                                            GlobalScope.launch(Dispatchers.IO) {
                                                PeriodTrackerValues.deleteAllPeriodHistory()
                                                PeriodTrackerValues.insertPeriodItem(
                                                    PeriodTrackModel(
                                                        startDate = daySelection.first().date.atStartOfDayMillis(),
                                                        endDate = daySelection.first().date
                                                            .plusDays((PillsTrackerSharedPrefs.PERIOD_LENGTH - 1).toLong())
                                                            .atStartOfDayMillis(),
                                                        logTime = DateTime.now().millis
                                                    )
                                                )
                                            }
                                            PillsTrackerSharedPrefs.IS_ONBOARDING_COMPLETE = true
                                            mainActivityPageViewModel.setSelectedGlobalNavItem(if(PillsTrackerSharedPrefs.SUB_STATUS) BottomNavItemsIdentifiers.NONE else BottomNavItemsIdentifiers.PREMIUM_POPUP)
                                        } else {
                                            toast(appStr(R.string.please_select_a_date))
                                        }
                                    }
                                }
                            }.semantics { this.contentDescription = "Select" }
                            .background(secondaryColor, MaterialTheme.shapes.allCorner100)
                            .padding(vertical = 16.DP, horizontal = 42.DP),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = stringResource(R.string.done),
                            style = MaterialTheme.typography.typo22SemiBold,
                            color = Color.White
                        )
                    }
                }
            },
            backgroundColor = Color.Transparent,
            modifier = Modifier.padding(16.DP)
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(Modifier.height(20.DP))

                Text(
                    text = stringResource(R.string.welcome),
                    style = MaterialTheme.typography.typo30Bold,
                    color = secondaryColor
                )

                Spacer(Modifier.height(20.DP))

                Text(
                    text = questionList[selectedIndex.value],
                    style = MaterialTheme.typography.typo22SemiBold,
                    color = MaterialTheme.colors.textColor
                )

                Spacer(Modifier.height(10.DP))

                Text(
                    text = infoList[selectedIndex.value],
                    style = MaterialTheme.typography.typo12Normal.copy(textAlign = TextAlign.Center),
                    color = TimerProgressColor
                )

                Spacer(Modifier.height(30.DP))

                if(selectedIndex.value != 2){
                    Text(
                        text = stringResource(R.string.i_am_not_sure),
                        style = MaterialTheme.typography.typo15SemiBold,
                        color = primaryColor,
                        modifier = Modifier.clickable {
                            when(selectedIndex.value){
                                0 -> {
                                    PillsTrackerSharedPrefs.CYCLE_LENGTH = 28
                                }
                                1 -> {
                                    PillsTrackerSharedPrefs.PERIOD_LENGTH = 4
                                }
                            }
                            selectedIndex.value = selectedIndex.value + 1
                        }.semantics { this.contentDescription = "Item Selector" }
                    )

                    Spacer(Modifier.height(30.DP))
                }


                when(selectedIndex.value){
                    0 -> {
                        Picker(
                            state = valuesPickerState1,
                            items = optionList[selectedIndex.value],
                            visibleItemsCount = 5,
                            modifier = Modifier.weight(0.3f),
                            textModifier = Modifier.padding(8.dp),
                            textStyle = MaterialTheme.typography.typo30Bold
                        )
                    }
                    1->{
                        Picker(
                            state = valuesPickerState2,
                            items = optionList[selectedIndex.value],
                            visibleItemsCount = 5,
                            modifier = Modifier.weight(0.3f),
                            textModifier = Modifier.padding(8.dp),
                            textStyle = MaterialTheme.typography.typo30Bold
                        )
                    }
                    2->{
                        OnBoardingCalendar(selections = daySelection)
                    }
                }

            }
        }
    }
}
