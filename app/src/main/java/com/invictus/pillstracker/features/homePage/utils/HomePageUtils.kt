package com.invictus.pillstracker.features.homePage.utils

import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import splitties.init.appCtx

object HomePageUtils {

    fun isNotificationPermissionGiven() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        ContextCompat.checkSelfPermission(appCtx, android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
    } else {
        true
    }

    fun notificationPermissionIntent() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        android.Manifest.permission.POST_NOTIFICATIONS
    } else {
        ""
    }

}