package com.invictus.pillstracker.features.graphCalendar.utils

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.invictus.pillstracker.data.database.periodTrack.PeriodTrackModel
import com.invictus.pillstracker.data.sharedPrefs.PillsTrackerSharedPrefs
import com.invictus.pillstracker.utils.TimeConversionUtils.isAfterOrEqual
import com.invictus.pillstracker.utils.TimeConversionUtils.isBeforeOrEqual
import com.invictus.pillstracker.utils.TimeConversionUtils.toLocalDate
import com.kizitonwose.calendar.core.yearMonth
import com.patrykandpatrick.vico.core.extension.sumByFloat
import timber.log.Timber
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.math.ceil

object CalendarUtils {


    private const val ovulationDayBefore = 14L
    private const val fertilityWindowMinus = 5L
    private const val fertilityWindowPlus = 1L

    private var periodNoOfDays = PillsTrackerSharedPrefs.PERIOD_LENGTH
    private var cycleLength = PillsTrackerSharedPrefs.CYCLE_LENGTH

    fun getCycleLength(): Int {
        cycleLength = PillsTrackerSharedPrefs.CYCLE_LENGTH; return cycleLength
    }
    fun getPeriodNoOfDays(): Int {
        periodNoOfDays = PillsTrackerSharedPrefs.PERIOD_LENGTH; return periodNoOfDays
    }

    fun isNeedToAskPeriodStart(dayWiseColorLastDate: LocalDate?,dayWiseColorEndTimeLastDate: LocalDate?, date: LocalDate): Boolean {
        if(dayWiseColorLastDate == null || dayWiseColorEndTimeLastDate == null)return false
        return date.isAfterOrEqual(dayWiseColorLastDate)
//        val periodChronicDays = (dayWiseColorLastDate.let { (ChronoUnit.DAYS.between(it, date) % cycleLength) }.toInt())
//        val isAfterTime = (date.isAfter(dayWiseColorLastDate) && date.isAfter(dayWiseColorEndTimeLastDate))
////        val noOfDaysHere = ChronoUnit.DAYS.between(dayWiseColorLastDate, dayWiseColorEndTimeLastDate).toInt()
//        return isAfterTime
    }

    fun getPeriodStartDateForUpcomingMonth(dayWiseColorLastDate: LocalDate?, date: LocalDate): Boolean {
        val periodChronicDays = (dayWiseColorLastDate?.let { (ChronoUnit.DAYS.between(it, date) % PillsTrackerSharedPrefs.CYCLE_LENGTH) }?.toInt() ?: -1)
        return (0 until PillsTrackerSharedPrefs.PERIOD_LENGTH).any { it == periodChronicDays } && (date.isAfterOrEqual(dayWiseColorLastDate))
    }

    fun ovulationCalculation(dayWiseColorLastDate: LocalDate?): LocalDate? {
        return dayWiseColorLastDate?.minusDays(ovulationDayBefore)
    }

    fun isOvulationDay(ovulationCalc: LocalDate?, date: LocalDate) = ovulationCalc?.dayOfMonth == date.dayOfMonth && ovulationCalc.yearMonth == date.yearMonth
    fun isFertilityWindowBefore(ovulationCalc: LocalDate?, date: LocalDate) = (ovulationCalc?.minusDays(fertilityWindowMinus)?.isBefore(date) == true || ovulationCalc?.minusDays(fertilityWindowMinus)?.isEqual(date) == true)
            && (ovulationCalc.isAfter(date) || ovulationCalc.isEqual(date))
    fun isFertilityWindowAfter(ovulationCalc: LocalDate?, date: LocalDate) = (ovulationCalc?.plusDays(fertilityWindowPlus)?.isAfter(date) == true || ovulationCalc?.plusDays(fertilityWindowPlus)?.isEqual(date) == true)
            && (ovulationCalc.isBefore(date) || ovulationCalc.isEqual(date))
    fun isOtherPreviousCycles(dayWiseColor: SnapshotStateList<Pair<LocalDate, LocalDate>>, date: LocalDate) = dayWiseColor.firstOrNull {
        (it.first.isBefore(date) || it.first.isEqual(date)) &&
                (it.second.isAfter(date) || it.second.isEqual(date))
    } != null


    fun calculatePeriodDays(getAllPeriodHistory: List<PeriodTrackModel>) {
        if (getAllPeriodHistory.isEmpty() || PillsTrackerSharedPrefs.IS_PERIOD_DETAIL_MANUAL_SET) return
        if (getAllPeriodHistory.size == 1) return

        val avgDays = if (getAllPeriodHistory.isNotEmpty()) {
            val totalDays = getAllPeriodHistory.sumByFloat {
                ChronoUnit.DAYS.between(
                    it.startDate.toLocalDate(),
                    it.endDate.toLocalDate()
                ).toFloat() + 1
            }
//            Timber.d("==>calculatePeriodDays_20 $totalDays")
            totalDays / getAllPeriodHistory.size
        } else {
            0.0
        }

        PillsTrackerSharedPrefs.PERIOD_LENGTH = ceil(avgDays.toDouble()).toInt()
//        Timber.d("==>calculatePeriodDays_22 $avgDays ${PillsTrackerSharedPrefs.PERIOD_LENGTH}")
        val cycleLengthCalculation = getAllPeriodHistory.zipWithNext().sumByFloat { (a, b) -> (b.startDate - a.startDate).toFloat()/ (1000 * 60 * 60 * 24) }
        PillsTrackerSharedPrefs.CYCLE_LENGTH = ceil(cycleLengthCalculation.toDouble()).toInt()

    }

    fun isPeriodStarted(startDay: LocalDate?, endDay: LocalDate?, currentDay: LocalDate): Pair<Boolean, Long> {
        if(startDay == null || endDay == null)return Pair(false,-1)
        Timber.d("==>isPeriodStarted_88 ${startDay.dayOfMonth} ${endDay.dayOfMonth} ${currentDay.dayOfMonth}")
        val isPeriodStarted = currentDay.isAfterOrEqual(startDay) && currentDay.isBeforeOrEqual(endDay)
        val periodDays = ChronoUnit.DAYS.between(startDay, currentDay)+1
        Timber.d("==>isPeriodStarted_91 $periodDays")
        return Pair(isPeriodStarted,periodDays)
    }

}