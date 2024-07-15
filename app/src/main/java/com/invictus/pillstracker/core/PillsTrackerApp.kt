package com.invictus.pillstracker.core

import android.app.Application
import com.airbnb.mvrx.Mavericks
import com.chibatching.kotpref.Kotpref
import com.invictus.pillstracker.data.database.PillsTrackerDatabase
import com.invictus.pillstracker.utils.CommonUtils.setDeviceCompanyNameInPref
import com.invictus.pillstracker.utils.firebaseDataUtils.FirebaseDataUtil
import timber.log.Timber


class PillsTrackerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initMVRXInstance()
        initRoomDBInstance()
        initTimber()
        initSharePreference()
        setDeviceCompanyNameInPref()
        initCrashlitics()
    }

    private fun initRoomDBInstance() {
        PillsTrackerDatabase.getInstance()


    }

    private fun initSharePreference() {
        Kotpref.init(this)
    }

    private fun initTimber() {
        if (com.invictus.pillstracker.BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initMVRXInstance() {
        Mavericks.initialize(this)
    }


    private fun initCrashlitics() {
        FirebaseDataUtil.firebaseApp()
        FirebaseDataUtil.firebaseUser()?.uid?.let {
            FirebaseDataUtil.firebaseCrashlyticsInstance().setUserId(it)
        }
        FirebaseDataUtil.firebaseCrashlyticsInstance().setCrashlyticsCollectionEnabled(true)
    }

}