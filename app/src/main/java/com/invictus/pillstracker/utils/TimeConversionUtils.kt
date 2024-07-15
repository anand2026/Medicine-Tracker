package com.invictus.pillstracker.utils

import org.joda.time.DateTime
import org.joda.time.DateTimeConstants
import org.joda.time.DateTimeZone
import org.joda.time.Days
import org.joda.time.format.DateTimeFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.concurrent.TimeUnit


object TimeConversionUtils {

    fun convertUTCTimeToLocalTime(timeStamp: Long): Long {
        return DateTime(timeStamp, DateTimeZone.getDefault()).toDate().time
    }

    fun convertMillisToAMPM(timeStamp: Long): String {
        return DateTimeFormat.forPattern("hh:mm a").print(DateTime(timeStamp))
    }

    fun convertMillisToFullDateTime(timeStamp: Long): String {
        return DateTimeFormat.forPattern("dd MMM yyyy hh:mm a").print(DateTime(timeStamp))
    }

    fun convertMillisToDDFormate(timeStamp: Long): String {
        return DateTimeFormat.forPattern("dd").print(DateTime(timeStamp))
    }

    fun convertMillisToYYYYFormate(timeStamp: Long): String {
        return DateTimeFormat.forPattern("yyyy").print(DateTime(timeStamp))
    }

    fun convertMillisToMMMyyyyFormate(timeStamp: Long): String {
        return DateTimeFormat.forPattern("MMM yyyy").print(DateTime(timeStamp))
    }

    fun convertMillisTDayMonthDateFormat(timeStamp: Long): String {
        return DateTimeFormat.forPattern("EEE, MMM dd").print(DateTime(timeStamp))
    }

    fun convertMillisToMonthDateYearFormat(timeStamp: Long): String {
        return DateTimeFormat.forPattern("MMM dd, yyyy").print(DateTime(timeStamp))
    }

    fun convertMillisToFullMonthDateYearFormat(timeStamp: Long): String {
        return DateTimeFormat.forPattern("MMMM dd, yyyy").print(DateTime(timeStamp))
    }

    fun convertMillisToddMMMyyyyFormate(timeStamp: Long): String {
        return DateTimeFormat.forPattern("dd MMM yyyy").print(DateTime(timeStamp))
    }

    fun convertMillisToddMMMFormate(timeStamp: Long): String {
        return DateTimeFormat.forPattern("dd MMM").print(DateTime(timeStamp))
    }

    fun convertMillisToThatDayHoursFormate(timeStamp: Long): String {
        return DateTimeFormat.forPattern("HH").print(DateTime(timeStamp))
    }


    fun convertMillsToDHMS(timeInMilliSeconds: Long): String {
        val seconds: Long = timeInMilliSeconds / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        return days.toString() + ":" + hours % 24 + ":" + minutes % 60 + ":" + seconds % 60
    }

    fun convertMillsToHMS(timeInMilliSeconds: Long): String {
        val seconds: Long = timeInMilliSeconds / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        return "" + hours % 24 + ":" + minutes % 60 + ":" + seconds % 60
    }

    fun convertMillsToH(timeInMilliSeconds: Long): String {
        val seconds: Long = timeInMilliSeconds / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        return "" + hours % 24
    }

    fun convertMillsToMS(timeInMilliSeconds: Long): String {
        val seconds: Long = timeInMilliSeconds / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        return "" + minutes % 60 + ":" + seconds % 60
    }

    fun getDurationBreakdown(millis: Long): String? {
        var millisObj = millis
        val sb = StringBuilder(64)

        if (millisObj >= 0) {
            val days: Long = TimeUnit.MILLISECONDS.toDays(millisObj)
            millisObj -= TimeUnit.DAYS.toMillis(days)
            val hours: Long = TimeUnit.MILLISECONDS.toHours(millisObj)
            millisObj -= TimeUnit.HOURS.toMillis(hours)
            val minutes: Long = TimeUnit.MILLISECONDS.toMinutes(millisObj)
            millisObj -= TimeUnit.MINUTES.toMillis(minutes)
            val seconds: Long = TimeUnit.MILLISECONDS.toSeconds(millisObj)

            sb.append(days)
            sb.append(" Days ")
            sb.append(hours)
            sb.append(" Hours ")
            sb.append(minutes)
            sb.append(" Minutes ")
            sb.append(seconds)
            sb.append(" Seconds")
        } else {
            sb.append("")
        }

        return sb.toString()
    }

    fun getDurationBreakdownHours(millis: Long): String {
        var millisObj = millis
        val sb = StringBuilder(64)

        if (millisObj >= 0) {
            val days: Long = TimeUnit.MILLISECONDS.toDays(millisObj)
            millisObj -= TimeUnit.DAYS.toMillis(days)
            val hours: Long = TimeUnit.MILLISECONDS.toHours(millisObj)

            sb.append(hours)
        } else {
            sb.append("")
        }

        return sb.toString()
    }

    fun getDurationBreakdownMinutes(millis: Long): String {
        var millisObj = millis
        val sb = StringBuilder(64)

        if (millisObj >= 0) {
            val days: Long = TimeUnit.MILLISECONDS.toDays(millisObj)
            millisObj -= TimeUnit.DAYS.toMillis(days)
            val hours: Long = TimeUnit.MILLISECONDS.toHours(millisObj)
            millisObj -= TimeUnit.HOURS.toMillis(hours)
            val minutes: Long = TimeUnit.MILLISECONDS.toMinutes(millisObj)

            sb.append(minutes)
        } else {
            sb.append("")
        }

        return sb.toString()
    }

    fun getDurationBreakdownDays(millis: Long): String {
        var millisObj = millis
        val sb = StringBuilder(64)

        if (millisObj >= 0) {
            val days: Long = TimeUnit.MILLISECONDS.toDays(millisObj)
            sb.append(days)
        } else {
            sb.append("")
        }

        return sb.toString()
    }

    fun displayTimeInHMS(timeinMillis: Long, formate: Int): String {
        var displayTime = ""
        val h = timeinMillis / 3600000
        val m = (timeinMillis - h * 3600000) / 60000
        val s = (timeinMillis - h * 3600000 - m * 60000) / 1000
        var hh = ""
        hh = if (h < 10) {
            "0$h"
        } else {
            h.toString() + ""
        }
        var mm = ""
        mm = if (m < 10) {
            "0$m"
        } else {
            m.toString() + ""
        }
        var ss = ""
        ss = if (s < 10) {
            "0$s"
        } else {
            s.toString() + ""
        }
        displayTime = if (formate == 100) {
            hh + "h "
        } else if (formate == 110) {
            hh + "h " + mm + "m "
        } else if (formate == 11) {
            mm + "m " + ss + "s "
        } else {
            hh + "h " + mm + "m " + ss + "s"
        }
        return displayTime
    }

    fun getDayNameFromMillis(millis: Long): String {
        return DateTime(millis).dayOfWeek().asText ?: ""
    }

    fun convertMMMMddyyyyToMillis(dateStr: String): Long {
        val dateFormat = DateTimeFormat.forPattern("MMMM dd, yyyy")
        val date = dateFormat.parseDateTime(dateStr)
        return date.millis
    }

    fun getCurrentWeekDates(): List<String> {
        val now = DateTime.now()
        val startOfWeek = now.withDayOfWeek(DateTimeConstants.MONDAY).withTimeAtStartOfDay()
        val endOfWeek = now.withDayOfWeek(DateTimeConstants.SUNDAY).withTime(23, 59, 59, 999)
        val dates = mutableListOf<String>()
        var current = startOfWeek
        while (!current.isAfter(endOfWeek)) {
            dates.add(convertMillisToFullMonthDateYearFormat(current.millis))
            current = current.plusDays(1)
        }
        return dates
    }

    val shortDaysOfWeek = listOf("M", "T", "W", "T", "F", "S", "S")

    fun getFirstDayOfMonthMillis(): Long {
        val now = DateTime.now()
        val firstDayOfMonth = now.withDayOfMonth(1).withTimeAtStartOfDay()
        return firstDayOfMonth.millis
    }

    fun getCurrentMonthWeeklyDates(): List<List<String>> {
        val now = DateTime.now()
        val firstDayOfMonth = now.withDayOfMonth(1).withTimeAtStartOfDay()
        val lastDayOfMonth = now.withDayOfMonth(now.dayOfMonth().maximumValue).withTime(23, 59, 59, 999)
        val weeklyDates = mutableListOf<List<String>>()
        var current = firstDayOfMonth
        while (!current.isAfter(lastDayOfMonth)) {
            val startOfWeek = current.withDayOfWeek(DateTimeConstants.MONDAY).withTimeAtStartOfDay()
            val endOfWeek = current.withDayOfWeek(DateTimeConstants.SUNDAY).withTime(23, 59, 59, 999)
            val weekDates = mutableListOf<String>()
            var day = startOfWeek
            while (!day.isAfter(endOfWeek)) {
                if (day.monthOfYear == now.monthOfYear) {
                    weekDates.add(convertMillisToFullMonthDateYearFormat(day.millis))
                }
                day = day.plusDays(1)
            }
            weeklyDates.add(weekDates)
            current = endOfWeek.plusMillis(1)
        }
        return weeklyDates
    }

    fun getLastDateOfCurrentMonth(): Int {
        val now = DateTime.now()
        val lastDayOfMonth = now.withDayOfMonth(now.dayOfMonth().maximumValue).withTime(23, 59, 59, 999)
        return lastDayOfMonth.dayOfMonth
    }

    fun getTwoDateDifferenceInDays(startTime: Long, endTime: Long): Int {
        return Days.daysBetween(DateTime(startTime).toLocalDate(), DateTime(endTime).toLocalDate()).days
    }

    fun convertMillisToDaysMonthHoursMinutes(milliseconds: Long, isNeedTags: Boolean = false): String {
        val totalSeconds = milliseconds / 1000
        val totalMinutes = totalSeconds / 60
        val totalHours = totalMinutes / 60
        val totalDays = totalHours / 24

        val months = (totalDays / 30).toInt()
        val days = (totalDays % 30).toInt()
        val hours = (totalHours % 24).toInt()
        val minutes = (totalMinutes % 60).toInt()
        return if (isNeedTags) {
            "$months months,$days days,$hours hours,$minutes minutes"
        } else {
            "$months,$days,$hours,$minutes"
        }
    }

    fun getCurrentMonthFullName(): String? {
        return DateTimeFormat.forPattern("MMMM").print(DateTime.now())
    }

    fun getCurrentYearNumber(): String? {
        return DateTimeFormat.forPattern("yyyy").print(DateTime.now())
    }


    fun Long.toLocalDate(): LocalDate {
        return java.time.Instant.ofEpochMilli(this)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
    }

    fun LocalDate.isAfterOrEqual(otherDate: LocalDate?): Boolean {
        if(otherDate == null)return false
        return this.isAfter(otherDate) || this.isEqual(otherDate)
    }
    fun LocalDate.isBeforeOrEqual(otherDate: LocalDate?): Boolean {
        if(otherDate == null)return false
        return this.isBefore(otherDate) || this.isEqual(otherDate)
    }

    fun LocalDate.atStartOfDayMillis(): Long {
        return this.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }

}