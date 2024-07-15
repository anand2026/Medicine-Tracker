package com.invictus.pillstracker.data.sharedPrefs

import android.content.SharedPreferences
import androidx.annotation.Keep
import com.chibatching.kotpref.KotprefModel
import com.invictus.pillstracker.data.sharedPrefs.PillsTrackerPrefKeyName.KOT_PREF_NAME

@Keep
object PillsTrackerSharedPrefs : KotprefModel() {

    override val kotprefName: String = KOT_PREF_NAME

    fun registerOnSharedPreferenceChangeListener(listenerPrefValueChange: SharedPreferences.OnSharedPreferenceChangeListener?) {
        PillsTrackerSharedPrefs.preferences.registerOnSharedPreferenceChangeListener(listenerPrefValueChange)
    }

    fun unregisterOnSharedPreferenceChangeListener(listenerPrefValueChange: SharedPreferences.OnSharedPreferenceChangeListener?) {
        listenerPrefValueChange?.let {
            PillsTrackerSharedPrefs.preferences.unregisterOnSharedPreferenceChangeListener(listenerPrefValueChange)
        }
    }

    var FIREBASE_RECIPIENT_TOKEN: String by stringPref("", key = PillsTrackerPrefKeyName.FIREBASE_RECIPIENT_TOKEN)
    var MOBILE_COMPANY_NAME: String by stringPref("", key = PillsTrackerPrefKeyName.MOBILE_COMPANY_NAME)

    var PERIOD_LENGTH: Int by intPref(0, key = PillsTrackerPrefKeyName.PERIOD_LENGTH)
    var CYCLE_LENGTH: Int by intPref(0, key = PillsTrackerPrefKeyName.CYCLE_LENGTH)
    var IS_PERIOD_DETAIL_MANUAL_SET: Boolean by booleanPref(false, key = PillsTrackerPrefKeyName.IS_PERIOD_DETAIL_MANUAL_SET)
    var IS_ONBOARDING_COMPLETE: Boolean by booleanPref(false, key = PillsTrackerPrefKeyName.IS_ONBOARDING_COMPLETE)
    var PROFILE_PICTURE_URI: String by stringPref("", key = PillsTrackerPrefKeyName.PROFILE_PICTURE_URI)
    var PROFILE_PICTURE_BACKGROUND_URI: String by stringPref("", key = PillsTrackerPrefKeyName.PROFILE_PICTURE_BACKGROUND_URI)
    var IS_REVIEW_GIVEN: Boolean by booleanPref(false, key = PillsTrackerPrefKeyName.IS_REVIEW_GIVEN)
    var PIN_LOCK_PASSWORD: String by stringPref("", key = PillsTrackerPrefKeyName.PIN_LOCK_PASSWORD)
    var SUB_STATUS: Boolean by booleanPref(false, key = PillsTrackerPrefKeyName.SUB_STATUS)

}
