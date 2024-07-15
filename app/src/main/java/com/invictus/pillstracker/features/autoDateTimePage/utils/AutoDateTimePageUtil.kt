package com.invictus.pillstracker.features.autoDateTimePage.utils

import android.content.Intent
import android.provider.Settings
import splitties.init.appCtx

object AutoDateTimePageUtil {

    fun isAutoTimeOff(): Boolean {
        return if (com.invictus.pillstracker.BuildConfig.DEBUG) {
            //false
            (Settings.Global.getInt(appCtx.contentResolver, Settings.Global.AUTO_TIME, 0) == 0)
        } else {
            (Settings.Global.getInt(appCtx.contentResolver, Settings.Global.AUTO_TIME, 0) == 0)
        }
    }

    fun dateSettingsPageIntent() = Intent().apply {
        action = Settings.ACTION_DATE_SETTINGS
    }

}