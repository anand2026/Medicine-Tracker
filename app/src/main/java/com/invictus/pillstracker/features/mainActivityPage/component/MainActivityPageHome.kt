package com.invictus.pillstracker.features.mainActivityPage.component

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksActivityViewModel
import com.invictus.pillstracker.R
import com.invictus.pillstracker.features.autoDateTimePage.component.AutoDateTimePageHome
import com.invictus.pillstracker.features.autoDateTimePage.utils.AutoDateTimePageUtil
import com.invictus.pillstracker.features.graphCalendar.component.CalenderDateSelector
import com.invictus.pillstracker.features.homePage.utils.HomePageUtils
import com.invictus.pillstracker.features.homePage.utils.HomePageUtils.notificationPermissionIntent
import com.invictus.pillstracker.features.mainActivityPage.MainActivityPageState
import com.invictus.pillstracker.features.mainActivityPage.MainActivityPageViewModel
import com.invictus.pillstracker.features.mainActivityPage.data.BottomNavItemsIdentifiers
import com.invictus.pillstracker.features.onBoardingFlow.component.LogInitialInfoOnboarding
import com.invictus.pillstracker.features.onBoardingFlow.component.TrackYourHealthActivity
import com.invictus.pillstracker.features.onBoardingFlow.component.WelcomePage
import com.invictus.pillstracker.features.pinLock.EnterPatternLockPage
import com.invictus.pillstracker.features.premiumPage.component.PremiumPagePopUp
import com.invictus.pillstracker.features.turnNetOnPage.component.TurnNetOnPageHome
import com.invictus.pillstracker.utils.composeUtils.commonUi.PillsTrackerLoadingView
import splitties.toast.toast
import timber.log.Timber


@Composable
fun MainActivityPageHome() {

    val context = LocalContext.current

    val mainActivityPageViewModel: MainActivityPageViewModel = mavericksActivityViewModel()
    val isDeviceInternetOn by mainActivityPageViewModel.collectAsState(MainActivityPageState::isDeviceInternetOn)
    val globalNavItem by mainActivityPageViewModel.collectAsState(MainActivityPageState::globalNavItem)


    val isNeedToShowAutoTimePage = remember { mutableStateOf(true) }


    val launcherNotificationPermission = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
        val message = if (!HomePageUtils.isNotificationPermissionGiven()) {
            context.getString(R.string.something_wrong_try_again)
        } else {
            context.getString(R.string.success)
        }
        toast(message)
    }

    LaunchedEffect(key1 = Unit){
        try {
            if (!HomePageUtils.isNotificationPermissionGiven()) {
                launcherNotificationPermission.launch(notificationPermissionIntent())
            }
        } catch (e: Exception) {
            Timber.d("==>AgreeTermsPage_70 $e")
        }
    }

    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {

        if (AutoDateTimePageUtil.isAutoTimeOff() && isNeedToShowAutoTimePage.value || !isDeviceInternetOn) {
//        if (false) {
            Crossfade(
                targetState = AutoDateTimePageUtil.isAutoTimeOff() && isNeedToShowAutoTimePage.value,label = ""
            ) {
                if (it) AutoDateTimePageHome { isNeedToShowAutoTimePage.value = false }
            }

            Crossfade(targetState = !isDeviceInternetOn, label = "") {
                if (it) TurnNetOnPageHome()
            }
        } else {

            Crossfade(
                targetState = globalNavItem == BottomNavItemsIdentifiers.SPLASH_SCREEN,label = ""
            ) {
                if (it) WelcomePage(mainActivityPageViewModel)
            }

            Crossfade(
                targetState = globalNavItem == BottomNavItemsIdentifiers.ONBOARDING_TRACK_YOUR_HEALTH, label = ""
            ) {
                if (it) TrackYourHealthActivity(mainActivityPageViewModel)
            }

            Crossfade(
                targetState = globalNavItem == BottomNavItemsIdentifiers.LOG_INITIAL_INFO, label = ""
            ) {
                if (it) LogInitialInfoOnboarding(mainActivityPageViewModel)
            }

            Crossfade(
                targetState = globalNavItem == BottomNavItemsIdentifiers.CALENDAR_SELECTOR,label = ""
            ) {
                if (it) CalenderDateSelector(mainActivityPageViewModel)
            }

            Crossfade(
                targetState = globalNavItem == BottomNavItemsIdentifiers.ENTER_APP_LOCK,label = ""
            ) {
                if (it) EnterPatternLockPage(mainActivityPageViewModel)
            }
            Crossfade(
                targetState = globalNavItem == BottomNavItemsIdentifiers.PREMIUM_POPUP,label = ""
            ) {
                if (it) PremiumPagePopUp(mainActivityPageViewModel)
            }

            Crossfade(
                targetState = globalNavItem == BottomNavItemsIdentifiers.NONE,label = ""
            ) {
                if (it) MainActivityLandingPage(mainActivityPageViewModel)
            }
        }


        PillsTrackerLoadingView(context = context, isVisible = false, backgroundColor = colorResource(id = R.color.overlay_dark_40))
    }
}
