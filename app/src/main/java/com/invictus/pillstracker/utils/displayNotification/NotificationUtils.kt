package com.invictus.pillstracker.utils.displayNotification

import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.invictus.pillstracker.R
import splitties.init.appCtx
import splitties.resources.appStr

object NotificationUtils {

    //CHANNEL_ID
    const val APP_RELATED_INFORMATION = "app_related_information"
    const val FCM_PROMOTION_MESSAGE = "fcm_promotion_message"

    fun showNotification(context: Context, data: Map<String, String>, notificationAction: NotificationAction, channelId: String = APP_RELATED_INFORMATION) {

        val title = data[NotificationInfo.TITLE.text] ?: appStr(R.string.app_name)
        val description = data[NotificationInfo.DESCRIPTION.text] ?: appStr(R.string.app_name)


        val notificationManager = appCtx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = getChannelName(channelId)
        notificationManager.createNotificationChannel(channel)

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.blue_heart)
            .setLargeIcon(BitmapFactory.decodeResource( context.resources, R.mipmap.ic_launcher))
            .setContentTitle(title)
            .setContentText(description)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(getPendingIntent(notificationAction.value, context))

        with(NotificationManagerCompat.from(context)) {
            notificationManager.notify(notificationAction.value, builder.build())
        }

    }
}
