package com.invictus.pillstracker.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Context.POWER_SERVICE
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.os.VibrationEffect
import android.provider.Browser
import android.provider.ContactsContract
import android.text.Html
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.getDrawable
import androidx.core.net.toUri
import com.google.gson.Gson
import com.invictus.pillstracker.R
import com.invictus.pillstracker.data.sharedPrefs.PillsTrackerSharedPrefs
import com.invictus.pillstracker.features.mainActivityPage.MainActivity
import splitties.init.appCtx
import splitties.systemservices.vibrator
import splitties.toast.UnreliableToastApi
import splitties.toast.toast
import timber.log.Timber
import java.lang.reflect.InvocationTargetException
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*
import java.util.regex.Pattern


object CommonUtils {

    var IS_BLOCK_WD_SHOW = false
    var IS_ACCESSIBILITY_SKIP = false

    var settingAppPackageNames: MutableList<String?> = ArrayList()
    fun getSettingAppPackgeName(): List<String?> {
        if (settingAppPackageNames.isEmpty()) {
            settingAppPackageNames.add("com.android.settings")
            settingAppPackageNames.add("com.android.systemui")
            settingAppPackageNames.add("com.android.settingsaccessibility")
            settingAppPackageNames.add("com.google.android.packageinstaller")
            settingAppPackageNames.add("com.sec.android.app.controlpanel")
            settingAppPackageNames.add("com.sec.android.app.taskmanager")
            settingAppPackageNames.add("com.samsung.android.sm")
            settingAppPackageNames.add("com.samsung.android.lool")
            settingAppPackageNames.add("com.miui.securitycenter")
            settingAppPackageNames.add("com.miui.powerkeeper")
            settingAppPackageNames.add("com.miui.cleanmaster")
            settingAppPackageNames.add("com.huawei.systemmanager")
            settingAppPackageNames.add("com.lenovo.powerguard")
            settingAppPackageNames.add("com.lenovo.powersetting")
            settingAppPackageNames.add("com.lenovo.security")
            settingAppPackageNames.add("com.mediatek.security")
            settingAppPackageNames.add("com.meizu.safe")
            settingAppPackageNames.add("com.meizu.battery")
            settingAppPackageNames.add("com.samsung.android.sm_cn")
            settingAppPackageNames.add("com.coloros.safecenter")
            settingAppPackageNames.add("com.color.safecenter")
            settingAppPackageNames.add("com.oppo.safe")
            settingAppPackageNames.add("com.vivo.abe")
            settingAppPackageNames.add("com.iqoo.secure")
            settingAppPackageNames.add("com.vivo.permissionmanager")
            settingAppPackageNames.add("com.yulong.android.security")
            settingAppPackageNames.add("com.gionee.softmanager")
            settingAppPackageNames.add("com.letv.android.letvsafe")
            settingAppPackageNames.add("com.zte.heartyservice")
            settingAppPackageNames.add("com.zte.powersavemode")
            settingAppPackageNames.add("com.asus.mobilemanager")
            settingAppPackageNames.add("com.oneplus.security")
            settingAppPackageNames.add("com.asus.powersaver")
            settingAppPackageNames.add("com.tencent.qqpimsecure")
            settingAppPackageNames.add("com.dragon.android.pandaspace")
            settingAppPackageNames.add("com.baidu.appsearch")
            settingAppPackageNames.add("com.lbe.security")
            settingAppPackageNames.add("com.kingroot.master")
            settingAppPackageNames.add("com.ijinshan.kbatterydoctor")
            settingAppPackageNames.add("com.cleanmaster.mguard_cn")
            settingAppPackageNames.add("cn.com.opda.android.clearmaster")
            settingAppPackageNames.add("com.qihoo.cleandroid_cn")
            settingAppPackageNames.add("cn.opda.a.phonoalbumshoushou")
            settingAppPackageNames.add("com.qihoo360.mobilesafe")
            settingAppPackageNames.add("com.zhuoyi.security.lite")
            settingAppPackageNames.add("com.android.purebackground")
            settingAppPackageNames.add("com.aliyun.SecurityCenter")
            settingAppPackageNames.add("com.zui.safecenter")
            settingAppPackageNames.add("com.lenovo.safecenter")
            settingAppPackageNames.add("com.samsung.memorymanager")
            settingAppPackageNames.add("com.samsung.android.sm")
            settingAppPackageNames.add("com.samsung.android.uds")
            settingAppPackageNames.add("com.smartisanos.security")
            settingAppPackageNames.add("com.tinno.customwhitelistapp")
            settingAppPackageNames.add("com.miui.securitycore")
            settingAppPackageNames.add("com.sonymobile.superstamina")
            settingAppPackageNames.add("com.lenovo.apprestriction")
            settingAppPackageNames.add("com.coloros.oppoguardelf")
            settingAppPackageNames.add("com.htc.htcpowermanager")
            settingAppPackageNames.add("com.lenovo.security.permissioncontrol")
            settingAppPackageNames.add("com.android.vending")
        }
        return settingAppPackageNames
    }

    var smsCallAppList: MutableSet<String> = HashSet()
    fun getSMSCallApps(context: Context): Set<String> {
        if (smsCallAppList.isEmpty()) {
            val intentSms = Intent(Intent.ACTION_VIEW, Uri.parse("sms:"))
            val pkgManagerSms = context.packageManager
            val activitiesSms =
                pkgManagerSms.queryIntentActivities(intentSms, 0)
            for (resolveInfo in activitiesSms) {
                smsCallAppList.add(resolveInfo.activityInfo.packageName)
            }
            val intentContect =
                Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
            val pkgManagerContect = context.packageManager
            val activitiesContect =
                pkgManagerContect.queryIntentActivities(intentContect, 0)
            for (resolveInfo in activitiesContect) {
                smsCallAppList.add(resolveInfo.activityInfo.packageName)
            }
            val intentDial = Intent(Intent.ACTION_DIAL, Uri.parse("tel:"))
            val pkgManagerDial = context.packageManager
            val activitiesDial =
                pkgManagerDial.queryIntentActivities(intentDial, 0)
            for (resolveInfo in activitiesDial) {
                smsCallAppList.add(resolveInfo.activityInfo.packageName)
            }
        }
        return smsCallAppList
    }

    fun getCurrentLauncher(): String {
        val localPackageManager = appCtx.packageManager
        val intent = Intent("android.intent.action.MAIN")
        intent.addCategory("android.intent.category.HOME")
        return (localPackageManager?.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY)?.activityInfo?.packageName) ?: ""
    }

    fun openWebPage(url: String?, context: Context) {
        val webpage: Uri = Uri.parse(url)
        try {
            val intent = Intent(Intent.ACTION_VIEW, webpage)
            intent.putExtra(Browser.EXTRA_APPLICATION_ID, "com.android.chrome")
            intent.putExtra("create_new_tab", false)
            intent.putExtra("new_window", false)
            intent.putExtra("skip_tab_queue", false)
            intent.setPackage("com.android.chrome")
            intent.addCategory("android.intent.category.BROWSABLE")
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }catch (e : Exception){
            Timber.d(e)
            val intent = Intent(Intent.ACTION_VIEW, webpage)
            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(intent)
            } else {
                toast(R.string.page_not_found)
            }
        }
    }


    fun convertHoursToMilles(hours: String?): Long {
        return if (hours != null) {
            (hours.toDouble() * 60 * 60 * 1000).toLong()
        } else {
            return 0L
        }
    }

    fun convertMinutesToMilles(minutes: String): Long {
        return (minutes.toDouble() * 60 * 1000).toLong()
    }

    fun boldStringBuilder(textView: TextView, boldText: String, normalText: String) {
        val sourceString = "<b>$boldText</b> $normalText"
        textView.text = Html.fromHtml(sourceString)
    }


    fun textLinkBuilder(textView: TextView, text: String) {
        val styledText = "<u><font color='blue'>$text</font></u>"
        textView.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE)
    }

    fun disableEnableControlsWithProgressBar(
        actionView: View?,
        progressBarView: View?,
        enable: Boolean,
        vg: ViewGroup?
    ) {
        try {
            for (i in 0 until vg?.childCount!!) {
                val child = vg.getChildAt(i)
                child?.isEnabled = enable
                if (child is ViewGroup) {
                    disableEnableControls(enable, child)
                }
            }
        } catch (e: Exception) {
            Timber.d(e)
        }
        if (enable) {
            actionView?.visibility = View.VISIBLE
            progressBarView?.visibility = View.GONE
        } else {
            actionView?.visibility = View.GONE
            progressBarView?.visibility = View.VISIBLE
        }
    }

    fun disableEnableControls(enable: Boolean, vg: ViewGroup?) {
        try {
            for (i in 0 until vg?.childCount!!) {
                val child = vg.getChildAt(i)
                child?.isEnabled = enable
                if (child is ViewGroup) {
                    disableEnableControls(enable, child)
                }
            }
        } catch (e: Exception) {
            Timber.d(e)
        }
    }


    fun setDeviceCompanyNameInPref(): String {
        when {
            "${Build.BRAND}-${Build.MANUFACTURER}".toLowerCase(Locale.ENGLISH).contains("lg") -> {
                PillsTrackerSharedPrefs.MOBILE_COMPANY_NAME = "lg"
            }
            "${Build.BRAND}-${Build.MANUFACTURER}".toLowerCase(Locale.ENGLISH)
                .contains("huawei") -> {
                PillsTrackerSharedPrefs.MOBILE_COMPANY_NAME = "huawei"
            }
            "${Build.BRAND}-${Build.MANUFACTURER}".toLowerCase(Locale.ENGLISH).contains("moto") -> {
                PillsTrackerSharedPrefs.MOBILE_COMPANY_NAME = "moto"
            }
            "${Build.BRAND}-${Build.MANUFACTURER}".toLowerCase(Locale.ENGLISH)
                .contains("samsung") -> {
                PillsTrackerSharedPrefs.MOBILE_COMPANY_NAME = "samsung"
            }
            "${Build.BRAND}-${Build.MANUFACTURER}".toLowerCase(Locale.ENGLISH)
                .contains("xiaomi") -> {
                PillsTrackerSharedPrefs.MOBILE_COMPANY_NAME = "xiaomi"
            }
            "${Build.BRAND}-${Build.MANUFACTURER}".toLowerCase(Locale.ENGLISH).equals(
                "oneplus",
                ignoreCase = true
            ) -> {
                PillsTrackerSharedPrefs.MOBILE_COMPANY_NAME = "oneplus"
            }
        }

        if (PillsTrackerSharedPrefs.MOBILE_COMPANY_NAME.isEmpty()) {
            PillsTrackerSharedPrefs.MOBILE_COMPANY_NAME = Build.BRAND.toLowerCase(Locale.ENGLISH)
        }

        return PillsTrackerSharedPrefs.MOBILE_COMPANY_NAME
    }

    val mIVersionCode: String
        get() = try {
            @SuppressLint("PrivateApi") val propertyClass =
                Class.forName("android.os.SystemProperties")
            val method =
                propertyClass.getMethod("get", String::class.java)
            method.invoke(propertyClass, "ro.miui.ui.version.code") as String
        } catch (e: ClassNotFoundException) {
            Timber.d(e)
            ""
        } catch (e: NoSuchMethodException) {
            Timber.d(e)
            ""
        } catch (e: IllegalAccessException) {
            Timber.d(e)
            ""
        } catch (e: InvocationTargetException) {
            Timber.d(e)
            ""
        }

    /*fun checkAutoTimeIsOff(): Boolean {
        return if (BuildConfig.DEBUG) {
                false
            //(Settings.Global.getInt(DToxApplication.context().contentResolver, Settings.Global.AUTO_TIME, 0) == 0)
        } else {
            (Settings.Global.getInt(DToxApplication.context().contentResolver, Settings.Global.AUTO_TIME, 0) == 0)
        }
    }*/

    fun getAllInstalledAppPackageName(context: Context): HashSet<String> {
        val apkPackageName = HashSet<String>()
        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
        val resolveInfoList = context.packageManager.queryIntentActivities(intent, 0)
        for (resolveInfo in resolveInfoList) {
            val activityInfo = resolveInfo.activityInfo

            if (activityInfo?.applicationInfo != null
                && activityInfo.applicationInfo.packageName != null
            ) {
                if (!activityInfo.applicationInfo.packageName
                        .equals(context.packageName, true)
                ) {
                    apkPackageName.add(activityInfo.applicationInfo.packageName)
                }
            }
        }
        return apkPackageName
    }

    fun getAppName(ApkPackageName: String, context: Context): String {
        var appName = ""
        val applicationInfo: ApplicationInfo?
        try {
            applicationInfo = context.packageManager.getApplicationInfo(ApkPackageName, 0)
            if (applicationInfo != null) {
                appName = context.packageManager.getApplicationLabel(applicationInfo).toString()
            }

        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return appName
    }

    fun getAppIconByPackageName(ApkTempPackageName: String, context: Context): Drawable? {
        return try {
            context.packageManager.getApplicationIcon(ApkTempPackageName)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            getDrawable(context, R.mipmap.ic_launcher)
        }
    }

    fun <T> createDataObjectFromString(stringDataObject: String, modelClass: Class<T>): T? {
        return try {
            Gson().fromJson(stringDataObject, modelClass)
        } catch (e: Exception) {
            //Timber.d(e)
            null
        }
    }

    fun createStringFromDataObject(src: Any?): String {
        return try {
            Gson().toJson(src)
        } catch (e: Exception) {
            Timber.d(e)
            ""
        }
    }

    fun turnOnScreen(){
        val powerManager: PowerManager = appCtx.getSystemService(POWER_SERVICE) as PowerManager
        val wakeLock: PowerManager.WakeLock = powerManager.newWakeLock(
            PowerManager.FULL_WAKE_LOCK or
                    PowerManager.ACQUIRE_CAUSES_WAKEUP or
                    PowerManager.ON_AFTER_RELEASE, "appname::WakeLock"
        )
        wakeLock.acquire(1*60*1000L /*1 minutes*/)
    }

    fun startVibration() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val mVibratePattern = longArrayOf(0, 500, 2000, 500, 2000, 500, 2000)
                val mAmplitudes = intArrayOf(0, 50, 0, 50, 0, 50, 0)
                vibrator.vibrate(VibrationEffect.createWaveform(mVibratePattern, mAmplitudes, 0))
            } else {
                //deprecated in API 26
                val mVibratePattern = longArrayOf(0, 400, 2000, 400, 2000, 400, 2000)
                vibrator.vibrate(mVibratePattern, 0)
            }
        } catch (e: Exception) {
            Timber.d(e)
        }
    }

    fun stopVibration() {
        try {
            vibrator.cancel()
        } catch (e: Exception) {
            Timber.d(e)
        }
    }

    fun roundOff(value: Float): Float {
        return BigDecimal(value.toDouble())
            .setScale(2, RoundingMode.HALF_EVEN)
            .toFloat()
    }

    fun getCountryCurrencySymbolMap(): String {
        return  Currency.getInstance(Locale.getDefault())?.symbol ?: "$"
        /*val countryCode = telephonyManager.networkCountryIso.uppercase()
        return try {
            getCountryCurrencySymbolMap1()!![countryCode] ?: "$"
        }catch (e : Exception){
            "$"
        }*/
    }

    private fun getAllCountryCurrencySymbolMap(): HashMap<String, String>? {
        val map: HashMap<String, String> = HashMap()
        for (countryCode in Locale.getISOCountries()) {
            val locale = Locale("", countryCode)
            try {
                val currency = Currency.getInstance(locale)
                val currencySymbol = currency.getSymbol(locale)
                map[countryCode] = currencySymbol
            } catch (e: java.lang.Exception) {
                // Some countries may not have a valid currency symbol, so we ignore them
            }
        }
        return map
    }

    fun calculatePercentage(part : Int, whole : Int): Int {
        return ((part.toDouble() / whole) * 100).toInt()
    }

    fun getVideoIdFromYTLink(ytUrl: String?): String? {
        val pattern = "(?<=youtu.be/|watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*"
        val compiledPattern = Pattern.compile(pattern)
        return if (ytUrl.isNullOrEmpty()) {
            "error"
        } else {
            val matcher = compiledPattern.matcher(ytUrl)
            if (matcher.find()) {
                matcher.group()
            } else {
                "error"
            }
        }
    }

    fun composeEmail(context: Context) {
        context.sendEmail("missioninvictus@gmail.com",context.getString(R.string.support_email_subject),"")
    }

    fun composeEmailForFeatureRequest(context: Context) {
        context.sendEmail("missioninvictus@gmail.com",context.getString(R.string.request_feature_on_pillstracker),"")
    }

    @OptIn(UnreliableToastApi::class)
    fun Context.sendEmail(email: String, subject: String, text: String? = null) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = "mailto:".toUri()
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            putExtra(Intent.EXTRA_SUBJECT, subject)
            if (text != null) {
                putExtra(Intent.EXTRA_TEXT, text)
            }
        }

        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            toast("$e")
        }
    }

    fun openPillsTrackerOnPlayStore(context: Activity) {
        try {
            runCatching {
                Intent().apply {
                    action = Intent.ACTION_VIEW
                    data = Uri.parse("https://play.google.com/store/apps/details?id=${appCtx.packageName}")
                }.also {
                    context.startActivity(it)
                }
            }
        } catch (e: Exception) {
            Timber.d("==>openPillsTrackerOnPlayStore_484 $e")
        }
    }

    fun pillsTrackerShareIntent() = Intent().apply {
        action = Intent.ACTION_SEND
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, appCtx.getString(R.string.share_text_title))
        putExtra(Intent.EXTRA_TEXT, "${appCtx.getString(R.string.share_text_description)}\n\n${SettingPageUrls.PILLS_TRACKER_PLAY_STORE.value}")
    }.also {
        Intent.createChooser(it, "Share via")
    }

    fun restartApp() {
        appCtx.startActivity(
            Intent(appCtx, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        )
    }

}