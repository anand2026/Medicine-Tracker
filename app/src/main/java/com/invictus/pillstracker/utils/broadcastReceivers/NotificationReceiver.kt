package com.invictus.pillstracker.utils.broadcastReceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.invictus.pillstracker.utils.displayNotification.NotificationAction
import com.invictus.pillstracker.utils.displayNotification.NotificationHashCode
import com.invictus.pillstracker.utils.displayNotification.NotificationInfo
import com.invictus.pillstracker.utils.displayNotification.NotificationUtils


class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(p0: Context?, p1: Intent?) {

        when(p1?.action?:""){
            NotificationHashCode.PERIOD_END_TOMORROW.value.toString()-> p0?.let { NotificationUtils.showNotification(it, mapOf(
                NotificationInfo.TITLE.text to "Get Ready for the Last Day of Your Period",
                NotificationInfo.DESCRIPTION.text to "Your period is ending tomorrow. Prepare for a smooth transition."
            ),NotificationAction.ACTION_OPEN_APP) }

            NotificationHashCode.PERIOD_END_TODAY.value.toString()-> p0?.let { NotificationUtils.showNotification(it, mapOf(
                NotificationInfo.TITLE.text to "Last Day of Your Period",
                NotificationInfo.DESCRIPTION.text to "Make the most of the day and stay comfortable."
            ),NotificationAction.ACTION_OPEN_APP) }

            NotificationHashCode.OVULATION_DAY_TOMORROW.value.toString()-> p0?.let { NotificationUtils.showNotification(it, mapOf(
                NotificationInfo.TITLE.text to "Ovulation Tomorrow: Plan Ahead",
                NotificationInfo.DESCRIPTION.text to "Take care and plan for the upcoming changes."
            ),NotificationAction.ACTION_OPEN_APP) }

            NotificationHashCode.OVULATION_DAY_TODAY.value.toString()-> p0?.let { NotificationUtils.showNotification(it, mapOf(
                NotificationInfo.TITLE.text to "Ovulation Today: Stay Mindful",
                NotificationInfo.DESCRIPTION.text to "Be mindful of your body's needs and changes."
            ),NotificationAction.ACTION_OPEN_APP) }

            NotificationHashCode.PERIOD_START_TOMORROW.value.toString()-> p0?.let { NotificationUtils.showNotification(it, mapOf(
                NotificationInfo.TITLE.text to "Period Starts Tomorrow: Be Prepared",
                NotificationInfo.DESCRIPTION.text to "Prepare yourself for a comfortable start to your period."
            ),NotificationAction.ACTION_OPEN_APP) }

            NotificationHashCode.PERIOD_START_TODAY.value.toString()-> p0?.let { NotificationUtils.showNotification(it, mapOf(
                NotificationInfo.TITLE.text to "Period Starts Today: Take Care",
                NotificationInfo.DESCRIPTION.text to "Remember to prioritize your comfort and well-being today."
            ),NotificationAction.ACTION_OPEN_APP) }


            else -> {}
        }
    }
}