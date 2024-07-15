package com.invictus.pillstracker.utils.displayNotification

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.invictus.pillstracker.R
import com.invictus.pillstracker.data.sharedPrefs.PillsTrackerSharedPrefs
import splitties.init.appCtx
import timber.log.Timber


class MyFirebaseMessagingService : FirebaseMessagingService() {

    companion object {
        val FEATURE_PROMOTION = "1"
    }


    override fun onNewToken(token: String) {
        super.onNewToken(token)
        PillsTrackerSharedPrefs.FIREBASE_RECIPIENT_TOKEN = token
        Timber.d("=-=-> Firebase Token is updated $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Timber.d("onMessageReceived: ==>>")
        handleRemoteMessage(remoteMessage)
    }



    private fun handleRemoteMessage(remoteMessage: RemoteMessage) {
        try {

            val data = remoteMessage.data
            val title = remoteMessage.notification?.title ?: appCtx.getString(R.string.app_name)
            val description = remoteMessage.notification?.body ?: ""
            val flag = data["flag"] ?: ""

            Timber.d("onMessageReceived: **==>>title==>> $title")
            Timber.d("onMessageReceived: **==>>description==>> $description")
            Timber.d("onMessageReceived: **==>>flag==>> $flag")
            Timber.d("onMessageReceived: **==>>data==>> $data")

            when (flag) {
                FEATURE_PROMOTION -> {
                    handleFeaturePromotion(data)
                }
            }

        } catch (e: Exception) {
            Timber.d(e)
        }
    }

    private fun handleFeaturePromotion(data: Map<String, String>) {
        NotificationUtils.showNotification(appCtx,data,NotificationAction.ACTION_OPEN_APP,NotificationUtils.FCM_PROMOTION_MESSAGE)
    }


}