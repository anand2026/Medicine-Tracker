package com.invictus.pillstracker.utils.displayNotification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.invictus.pillstracker.R
import com.invictus.pillstracker.features.mainActivityPage.MainActivity
import splitties.init.appCtx
import splitties.intents.toPendingActivity
import splitties.resources.appStr


enum class NotificationInfo(val text: String) {
    TITLE("title"),
    DESCRIPTION("description"),
    IMAGE("image"),
}

enum class NotificationAction(val value: Int) {
    ACTION_OPEN_APP(1)
}


enum class NotificationHashCode(val value:Int){
    PERIOD_START_TOMORROW(1),
    PERIOD_START_TODAY(2),
    OVULATION_DAY_TOMORROW(3),
    OVULATION_DAY_TODAY(4),
    PERIOD_END_TOMORROW(5),
    PERIOD_END_TODAY(6),
}

fun getPendingIntent(action: Int, ctx: Context = appCtx): PendingIntent {
    val intent = when(action) {
            NotificationAction.ACTION_OPEN_APP.value-> Intent(ctx, MainActivity::class.java).apply {}

            else-> Intent(ctx, MainActivity::class.java).apply {}
        }
    return intent.toPendingActivity(action, PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
}

fun getChannelName(channelId: String): NotificationChannel {
    return when (channelId) {
        NotificationUtils.APP_RELATED_INFORMATION -> NotificationChannel(channelId, appStr(R.string.information), NotificationManager.IMPORTANCE_HIGH)
        else -> NotificationChannel(channelId, appStr(R.string.information), NotificationManager.IMPORTANCE_HIGH)
    }
}