package com.invictus.pillstracker.features.graphCalendar

import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.invictus.pillstracker.data.database.periodTrack.PeriodTrackModel
import com.invictus.pillstracker.data.database.periodTrack.PeriodTrackerValues
import com.invictus.pillstracker.utils.TimeConversionUtils.atStartOfDayMillis
import com.invictus.pillstracker.utils.TimeConversionUtils.isAfterOrEqual
import com.invictus.pillstracker.utils.TimeConversionUtils.isBeforeOrEqual
import com.invictus.pillstracker.utils.TimeConversionUtils.toLocalDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import timber.log.Timber
import java.time.LocalDate


class CalendarViewModel(
    initialState: CalendarState,
    val repo: CalendarRepository,
) : MavericksViewModel<CalendarState>(initialState) {

    init {
        getPeriodItemList()
    }

    private fun getPeriodItemList(){
        PeriodTrackerValues.getAllPeriodHistory().setOnEach(Dispatchers.IO) { periodItemList->
//            Timber.d("==>getPeriodItemList_26 ${CommonUtils.createStringFromDataObject(it)}")
            copy(periodItemList = periodItemList)
        }
    }

    fun insertPeriodLoggingData(startDate: LocalDate, endDate: LocalDate) {

        withState { state ->

            val periodItem = state.periodItemList.firstOrNull {
                (startDate.isAfterOrEqual(it.startDate.toLocalDate()) && startDate.isBeforeOrEqual(it.endDate.toLocalDate()))
                        || (endDate.isAfterOrEqual(it.startDate.toLocalDate()) && endDate.isBeforeOrEqual(it.endDate.toLocalDate()))
            }

//            Timber.d("==>insertPeriodLoggingData_46 ${CommonUtils.createStringFromDataObject(periodItem)}")

            viewModelScope.launch(Dispatchers.IO) {
                try {
                    if (periodItem != null) {
                        PeriodTrackerValues.insertPeriodItem(
                            periodItem.copy(
                                _id = periodItem._id,
                                startDate = startDate.atStartOfDayMillis(),
                                endDate = endDate.atStartOfDayMillis(),
                                logTime = DateTime.now().millis
                            )
                        )
                    } else {
                        PeriodTrackerValues.insertPeriodItem(
                            PeriodTrackModel(
                                startDate = startDate.atStartOfDayMillis(),
                                endDate = endDate.atStartOfDayMillis(),
                                logTime = DateTime.now().millis
                            )
                        )
                    }
                } catch (e: Exception) {
                    Timber.d("==>insertPeriodLoggingData_66 $e")
                }
            }

        }


    }

    companion object : MavericksViewModelFactory<CalendarViewModel, CalendarState> {
        override fun create(viewModelContext: ViewModelContext, state: CalendarState
        ): CalendarViewModel {
            val repository = CalendarRepository()
            return CalendarViewModel(state, repository)
        }
    }

}

data class CalendarState(
    val periodItemList: List<PeriodTrackModel> = emptyList()
) : MavericksState
