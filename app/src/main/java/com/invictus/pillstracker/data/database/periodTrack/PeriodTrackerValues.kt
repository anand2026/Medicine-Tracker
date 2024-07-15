package com.invictus.pillstracker.data.database.periodTrack

import com.invictus.pillstracker.data.database.PillsTrackerDatabase
import com.invictus.pillstracker.utils.TimeConversionUtils.toLocalDate
import com.invictus.pillstracker.utils.alaramManager.ScheduleAlarm


object PeriodTrackerValues{

    fun getAllPeriodHistory() = PillsTrackerDatabase.getInstance().periodTrackerDao().getPeriodList()
    private fun getPeriodListNormal() = PillsTrackerDatabase.getInstance().periodTrackerDao().getPeriodListNormal()

    suspend fun insertPeriodItem(item: PeriodTrackModel) {
        PillsTrackerDatabase.getInstance().periodTrackerDao().insertPeriodItem(item)
        getPeriodListNormal().lastOrNull()?.let { track-> ScheduleAlarm.setAlarmForNotification(track.startDate.toLocalDate().atStartOfDay().toLocalDate(),track.endDate.toLocalDate().atStartOfDay().toLocalDate()) }
//        CalendarUtils.calculatePeriodDays(getPeriodListNormal())
    }

    suspend fun deleteAllPeriodHistory() {
        PillsTrackerDatabase.getInstance().periodTrackerDao().deleteAll()
    }

}
