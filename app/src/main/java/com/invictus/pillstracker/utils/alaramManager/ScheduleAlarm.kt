package com.invictus.pillstracker.utils.alaramManager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.SystemClock
import com.invictus.pillstracker.data.sharedPrefs.PillsTrackerSharedPrefs
import com.invictus.pillstracker.utils.TimeConversionUtils.atStartOfDayMillis
import com.invictus.pillstracker.utils.TimeConversionUtils.isBeforeOrEqual
import com.invictus.pillstracker.utils.TimeConversionUtils.toLocalDate
import com.invictus.pillstracker.utils.broadcastReceivers.NotificationReceiver
import com.invictus.pillstracker.utils.displayNotification.NotificationHashCode
import org.joda.time.DateTime
import splitties.init.appCtx
import splitties.intents.toPendingBroadcast
import splitties.systemservices.alarmManager
import java.time.LocalDate

class ScheduleAlarm {

    companion object {
        private const val REPEAT_15_MIN = 15 * 60 * 1000L
        private fun setEventAlarm(pendingIntent: PendingIntent, scheduleTime: Long, isRepeatInEvery15Min: Boolean? = false) {
            //Timber.d("==>>setAlarm")
            alarmManager.cancel(pendingIntent);
            if (isRepeatInEvery15Min == true) {
                alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), REPEAT_15_MIN, pendingIntent)
            } else {
                alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, scheduleTime, pendingIntent)
            }
        }

        fun setAlarmForNotification(startDate: LocalDate, endDate: LocalDate) {

            val periodEndTomorrowIntent = Intent(appCtx, NotificationReceiver::class.java).apply { action = NotificationHashCode.PERIOD_END_TOMORROW.value.toString() }
            val periodEndTomorrowPendingIntent = periodEndTomorrowIntent.toPendingBroadcast(NotificationHashCode.PERIOD_END_TOMORROW.value, PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

            if(!endDate.isBeforeOrEqual(LocalDate.now().atStartOfDay().toLocalDate())){
                val timeToSchedule = DateTime(startDate.plusDays(PillsTrackerSharedPrefs.PERIOD_LENGTH.toLong()-2).atStartOfDayMillis()).plusHours(8).millis
                //Timber.d("==>setAlarm periodEndTomorrowIntent1 ${TimeConversionUtils.convertMillisToFullMonthDateYearFormat(timeToSchedule)}")
                setEventAlarm(periodEndTomorrowPendingIntent,timeToSchedule)
            }else {
                val timeToSchedule = DateTime(startDate.plusDays(PillsTrackerSharedPrefs.PERIOD_LENGTH-1+PillsTrackerSharedPrefs.CYCLE_LENGTH.toLong()-1).atStartOfDayMillis()).plusHours(8).millis
                //Timber.d("==>setAlarm periodEndTomorrowIntent2 ${TimeConversionUtils.convertMillisToFullMonthDateYearFormat(timeToSchedule)}")
                setEventAlarm(periodEndTomorrowPendingIntent,timeToSchedule)
            }

            val periodEndTodayIntent = Intent(appCtx, NotificationReceiver::class.java).apply { action = NotificationHashCode.PERIOD_END_TODAY.value.toString() }
            val periodEndTodayPendingIntent = periodEndTodayIntent.toPendingBroadcast(NotificationHashCode.PERIOD_END_TODAY.value, PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

            if(!endDate.isBeforeOrEqual(LocalDate.now().atStartOfDay().toLocalDate())){
                val timeToSchedule = DateTime(startDate.plusDays(PillsTrackerSharedPrefs.PERIOD_LENGTH.toLong()-1).atStartOfDayMillis()).plusHours(8).millis
                //Timber.d("==>setAlarm periodEndTodayIntent1 ${TimeConversionUtils.convertMillisToFullMonthDateYearFormat(timeToSchedule)}")
                setEventAlarm(periodEndTodayPendingIntent,timeToSchedule)
            }else{
                val timeToSchedule = DateTime(startDate.plusDays(PillsTrackerSharedPrefs.CYCLE_LENGTH-1+PillsTrackerSharedPrefs.PERIOD_LENGTH.toLong()).atStartOfDayMillis()).plusHours(8).millis
                //Timber.d("==>setAlarm periodEndTodayIntent2 ${TimeConversionUtils.convertMillisToFullMonthDateYearFormat(timeToSchedule)}")
                setEventAlarm(periodEndTodayPendingIntent,timeToSchedule)
            }

            val periodStartTomorrowIntent = Intent(appCtx, NotificationReceiver::class.java).apply { action = NotificationHashCode.PERIOD_START_TOMORROW.value.toString() }
            val periodStartTomorrowPendingIntent = periodStartTomorrowIntent.toPendingBroadcast(NotificationHashCode.PERIOD_START_TOMORROW.value, PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

            if(!endDate.isBeforeOrEqual(LocalDate.now().atStartOfDay().toLocalDate())){
                val timeToSchedule = DateTime(startDate.plusDays(PillsTrackerSharedPrefs.CYCLE_LENGTH.toLong()-1).atStartOfDayMillis()).plusHours(8).millis
                //Timber.d("==>setAlarm periodStartTomorrowIntent1 ${TimeConversionUtils.convertMillisToFullMonthDateYearFormat(timeToSchedule)}")
                setEventAlarm(periodStartTomorrowPendingIntent,timeToSchedule)
            }else{
                val timeToSchedule = DateTime(startDate.plusDays(PillsTrackerSharedPrefs.CYCLE_LENGTH.toLong()-1).atStartOfDayMillis()).plusHours(8).millis
                //Timber.d("==>setAlarm periodStartTomorrowIntent2 ${TimeConversionUtils.convertMillisToFullMonthDateYearFormat(timeToSchedule)}")
                setEventAlarm(periodStartTomorrowPendingIntent,timeToSchedule)
            }

            val periodStartTodayIntent = Intent(appCtx, NotificationReceiver::class.java).apply { action = NotificationHashCode.PERIOD_START_TODAY.value.toString() }
            val periodStartTodayPendingIntent = periodStartTodayIntent.toPendingBroadcast(NotificationHashCode.PERIOD_START_TODAY.value, PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)


            if(!endDate.isBeforeOrEqual(LocalDate.now().atStartOfDay().toLocalDate())){
                val timeToSchedule = DateTime(startDate.plusDays(PillsTrackerSharedPrefs.CYCLE_LENGTH.toLong()).atStartOfDayMillis()).plusHours(8).millis
                //Timber.d("==>setAlarm periodStartTodayIntent1 ${TimeConversionUtils.convertMillisToFullMonthDateYearFormat(timeToSchedule)}")
                setEventAlarm(periodStartTodayPendingIntent,timeToSchedule)
            }else{
                val timeToSchedule = DateTime(startDate.plusDays(PillsTrackerSharedPrefs.CYCLE_LENGTH.toLong()).atStartOfDayMillis()).plusHours(8).millis
                //Timber.d("==>setAlarm periodStartTodayIntent2 ${TimeConversionUtils.convertMillisToFullMonthDateYearFormat(timeToSchedule)}")
                setEventAlarm(periodStartTodayPendingIntent,timeToSchedule)
            }

            val ovulationStartTomorrowIntent = Intent(appCtx, NotificationReceiver::class.java).apply { action = NotificationHashCode.OVULATION_DAY_TOMORROW.value.toString() }
            val ovulationStartTomorrowPendingIntent = ovulationStartTomorrowIntent.toPendingBroadcast(NotificationHashCode.OVULATION_DAY_TOMORROW.value, PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)


            if(!startDate.plusDays(PillsTrackerSharedPrefs.CYCLE_LENGTH.toLong()-1-14).atStartOfDayMillis().toLocalDate().isBefore(LocalDate.now().atStartOfDay().toLocalDate())){
                val timeToSchedule = DateTime(startDate.plusDays(PillsTrackerSharedPrefs.CYCLE_LENGTH.toLong()-1-14).atStartOfDayMillis()).plusHours(8).millis
                //Timber.d("==>setAlarm ovulationStartTomorrowIntent1 ${TimeConversionUtils.convertMillisToFullMonthDateYearFormat(timeToSchedule)}")
                setEventAlarm(ovulationStartTomorrowPendingIntent,timeToSchedule)
            }

            val ovulationStartTodayIntent = Intent(appCtx, NotificationReceiver::class.java).apply { action = NotificationHashCode.OVULATION_DAY_TODAY.value.toString() }
            val ovulationStartTodayPendingIntent = ovulationStartTodayIntent.toPendingBroadcast(NotificationHashCode.OVULATION_DAY_TODAY.value, PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)


            if(!startDate.plusDays(PillsTrackerSharedPrefs.CYCLE_LENGTH.toLong()-14).atStartOfDayMillis().toLocalDate().isBefore(LocalDate.now().atStartOfDay().toLocalDate())){
                val timeToSchedule = DateTime(startDate.plusDays(PillsTrackerSharedPrefs.CYCLE_LENGTH.toLong()-14).atStartOfDayMillis()).plusHours(8).millis
                //Timber.d("==>setAlarm ovulationStartTodayIntent1 ${TimeConversionUtils.convertMillisToFullMonthDateYearFormat(timeToSchedule)}")
                setEventAlarm(ovulationStartTodayPendingIntent,timeToSchedule)
            }

        }
    }
}