package com.invictus.pillstracker.features.mainActivityPage

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import com.invictus.pillstracker.utils.broadcastReceivers.InternetOnOffReceiver
import com.invictus.pillstracker.utils.composeUtils.theme.ComposePillsTrackerTheme
import com.invictus.pillstracker.utils.googleBilling.GoogleBillingSdkOperation


class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    private var mInternetOnOffReceiver: InternetOnOffReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initInterNetReceiver()
        GoogleBillingSdkOperation.registerBilling(this)
        setContent {
            ComposePillsTrackerTheme {
                LandingPage()
            }
        }
    }

    @Composable
    fun LandingPage() {

    }



    /**
     * ------------------------------Internet receiver----------------------
     */

    private fun initInterNetReceiver() {
        if (mInternetOnOffReceiver == null) {
            mInternetOnOffReceiver = InternetOnOffReceiver()
        }
    }

    override fun onStart() {
        super.onStart()
        runCatching {
            IntentFilter().apply {
                addAction(ConnectivityManager.CONNECTIVITY_ACTION)
            }.also {
                registerReceiver(mInternetOnOffReceiver, it)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        runCatching {
            if (mInternetOnOffReceiver != null) unregisterReceiver(mInternetOnOffReceiver)
        }
    }

}