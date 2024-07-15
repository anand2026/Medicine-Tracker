package com.invictus.pillstracker.features.turnNetOnPage.utils

import android.content.Intent
import android.provider.Settings
import splitties.systemservices.connectivityManager
import timber.log.Timber

object TurnNetOnPageUtils {

    fun isOnline(): Boolean {
        return try {
            val netInfo = connectivityManager.activeNetworkInfo
            //should check null because in airplane mode it will be null
            netInfo != null && netInfo.isConnected
        } catch (e: Exception) {
            Timber.d(e)
            false
        }
    }

    fun settingPageIntent() = Intent(Settings.ACTION_SETTINGS)

}