package com.invictus.pillstracker.features.homePage.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.invictus.pillstracker.R
import com.invictus.pillstracker.data.sharedPrefs.PillsTrackerSharedPrefs
import com.invictus.pillstracker.features.graphCalendar.CalendarState
import com.invictus.pillstracker.features.graphCalendar.CalendarViewModel
import com.invictus.pillstracker.features.graphCalendar.utils.CalendarUtils
import com.invictus.pillstracker.features.homePage.HomePageState
import com.invictus.pillstracker.features.homePage.HomePageViewModel
import com.invictus.pillstracker.features.homePage.component.common.HorizontalDateForHomePage
import com.invictus.pillstracker.features.homePage.component.common.monthYearFormatter
import com.invictus.pillstracker.features.homePage.data.PeriodStateIdentifier
import com.invictus.pillstracker.features.mainActivityPage.MainActivityPageViewModel
import com.invictus.pillstracker.features.mainActivityPage.data.BottomNavItemsIdentifiers
import com.invictus.pillstracker.utils.TimeConversionUtils.toLocalDate
import com.invictus.pillstracker.utils.composeUtils.calender.clickable
import com.invictus.pillstracker.utils.composeUtils.theme.allCorner50
import com.invictus.pillstracker.utils.composeUtils.theme.darkRedGradient
import com.invictus.pillstracker.utils.composeUtils.theme.homeHeartAreaBackground
import com.invictus.pillstracker.utils.composeUtils.theme.homePageBottomColor
import com.invictus.pillstracker.utils.composeUtils.theme.lightPinkGradient
import com.invictus.pillstracker.utils.composeUtils.theme.textColor
import com.invictus.pillstracker.utils.composeUtils.theme.topCorner20
import com.invictus.pillstracker.utils.composeUtils.theme.typo15Bold
import com.invictus.pillstracker.utils.composeUtils.theme.typo15SemiBold
import com.invictus.pillstracker.utils.displayUnitConverter.UnitConverter.DP
import java.time.LocalDate

@Composable
fun HomePage(mainActivityViewModel: MainActivityPageViewModel) {

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = MaterialTheme.colors.homeHeartAreaBackground
    )

    val calendarViewModel: CalendarViewModel = mavericksViewModel()
    val viewModel: HomePageViewModel = mavericksViewModel()

    val currentDate = remember { LocalDate.now() }
    val selectedMonth = remember { mutableStateOf(LocalDate.now()) }
    val emojiData by viewModel.collectAsState(HomePageState::moodEmojiData)
    val periodOvulationDataIntro by viewModel.collectAsState(HomePageState::periodAndOvulationData)

    val periodItemList by calendarViewModel.collectAsState(CalendarState::periodItemList)
    val selectedMood = remember { mutableStateListOf<Int>() }

    val dayWiseColor = remember { mutableStateListOf<Pair<LocalDate, LocalDate>>() }
    LaunchedEffect(key1 = periodItemList) {
        dayWiseColor.clear()
        periodItemList.forEach {
            dayWiseColor.add(Pair(it.startDate.toLocalDate(), it.endDate.toLocalDate()))
        }
    }
        val isNeedToAskPeriodStart = CalendarUtils.isNeedToAskPeriodStart(dayWiseColor.lastOrNull()?.first?.plusDays(PillsTrackerSharedPrefs.CYCLE_LENGTH.toLong()), dayWiseColor.lastOrNull()?.second?.plusDays(PillsTrackerSharedPrefs.CYCLE_LENGTH.toLong()),LocalDate.now().atStartOfDay().toLocalDate())

    LaunchedEffect(dayWiseColor) {
//        with(Dispatchers.Default){
            val ovulationCalc = CalendarUtils.ovulationCalculation(dayWiseColor.lastOrNull()?.first?.plusDays(PillsTrackerSharedPrefs.CYCLE_LENGTH.toLong()))

            val isOvulationDay = CalendarUtils.isOvulationDay(ovulationCalc, LocalDate.now().atStartOfDay().toLocalDate())

            val isFertilityWindowBefore = CalendarUtils.isFertilityWindowBefore(ovulationCalc,LocalDate.now().atStartOfDay().toLocalDate())

            val isFertilityWindowAfter = CalendarUtils.isFertilityWindowAfter(ovulationCalc,LocalDate.now().atStartOfDay().toLocalDate())

            val isPeriodStarted = CalendarUtils.isPeriodStarted(dayWiseColor.lastOrNull()?.first, dayWiseColor.lastOrNull()?.second,LocalDate.now().atStartOfDay().toLocalDate())

            when{
                isPeriodStarted.first -> viewModel.periodOvulationDataIntro(PeriodStateIdentifier.PERIOD,isPeriodStarted.second.toString())
                isOvulationDay -> viewModel.periodOvulationDataIntro(PeriodStateIdentifier.OVULATION,"ovulation")
                isFertilityWindowBefore || isFertilityWindowAfter -> viewModel.periodOvulationDataIntro(PeriodStateIdentifier.OVULATION)
                else -> viewModel.periodOvulationDataIntro(PeriodStateIdentifier.NORMAL,isPeriodStarted.second.toString())
            }
//        }

    }

    Surface(
        color = MaterialTheme.colors.homeHeartAreaBackground,
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            bottomBar = {},
            topBar = {},
            backgroundColor = Color.Transparent,
            modifier = Modifier
                .fillMaxSize()
        ) { pad ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(pad),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.DP, 0.DP),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Spacer(Modifier.height(20.DP))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AppBarNameComponent("To PillsTracker")

                            AppBarNotificationComponent(0)
                        }


                        Spacer(Modifier.height(20.DP))

                        periodOvulationDataIntro?.let {
                            HomePageOvulationComp(
                                it,
                                monthYearFormatter.format(selectedMonth.value)
                            ) {}
                        }



                        Spacer(Modifier.height(17.DP))

                        Box(
                            modifier = Modifier
                                .background(brush = Brush.horizontalGradient(if (isNeedToAskPeriodStart) darkRedGradient else lightPinkGradient), MaterialTheme.shapes.allCorner50)
                                .clickable {
                                    mainActivityViewModel.setSelectedGlobalNavItem(BottomNavItemsIdentifiers.CALENDAR_SELECTOR)
                                }.semantics { this.contentDescription = "Calendar Selector" }
                                .padding(horizontal = 16.DP, 10.DP)
                        ) {
                            Text(
                                text = stringResource(if (isNeedToAskPeriodStart) R.string.period_start else R.string.edit_period),
                                style = MaterialTheme.typography.typo15SemiBold,
                                color = Color.White
                            )
                        }
//                    HomePageOvulationComp(
//                        true,
//                        Pair(stringResource(R.string.ovulation),stringResource(R.string.high_chances_of_getting_pregnant)),
//                        "March 2022"
//                    ){}

                        Spacer(Modifier.height(17.DP))


                        HorizontalDateForHomePage(currentDate, selectedMonth, dayWiseColor)

                        Spacer(Modifier.height(10.DP))
                    }
                }



                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                MaterialTheme.colors.homePageBottomColor,
                                MaterialTheme.shapes.topCorner20
                            )
                            .padding(20.DP)
                    ) {
                        Text(
                            text = stringResource(R.string.your_mood),
                            style = MaterialTheme.typography.typo15Bold,
                            color = MaterialTheme.colors.textColor
                        )

                        Spacer(Modifier.height(8.DP))

                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.DP)
                        ) {
                            emojiData.forEachIndexed { index, item ->
                                item {
                                    EmojiSelectionComponent(item.first, item.second.first, item.second.second, selectedMood.contains(index)) {
                                        selectedMood.clear()
                                        selectedMood.add(index)
                                    }
                                }
                            }

                        }
                        Spacer(Modifier.height(200.DP))


                    }
                }

            }
        }
    }
}