package com.invictus.pillstracker.features.profile.component

import android.app.Activity
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.play.core.review.ReviewManagerFactory
import com.invictus.pillstracker.data.sharedPrefs.PillsTrackerSharedPrefs
import com.invictus.pillstracker.features.mainActivityPage.MainActivityPageViewModel
import com.invictus.pillstracker.features.mainActivityPage.data.BottomNavItemsIdentifiers
import com.invictus.pillstracker.features.profile.data.ProfileItemIdentifier
import com.invictus.pillstracker.features.profile.utils.ProfilePageUtils
import com.invictus.pillstracker.utils.CommonUtils
import com.invictus.pillstracker.utils.SignInPageUrls
import com.invictus.pillstracker.utils.composeUtils.theme.homePageBottomColor
import com.invictus.pillstracker.utils.displayUnitConverter.UnitConverter.DP

@Composable
fun ProfilePage(viewModel: MainActivityPageViewModel) {

    val profileItem = remember { ProfilePageUtils.profilePageItems() }
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = MaterialTheme.colors.homePageBottomColor
    )

    val context = LocalContext.current
    val reviewManager = remember { ReviewManagerFactory.create((context as Activity)) }

    LaunchedEffect(key1 = Unit) {
        if (!PillsTrackerSharedPrefs.IS_REVIEW_GIVEN) {
            reviewManager
                .requestReviewFlow()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        reviewManager
                            .launchReviewFlow((context as Activity), it.result)
                            .addOnCompleteListener {
                                PillsTrackerSharedPrefs.IS_REVIEW_GIVEN = true
                            }
                    }
                }
        }
    }

    Surface(
        color = MaterialTheme.colors.homePageBottomColor
    ) {
        Scaffold(
            topBar = {
                ProfilePageTopAppBar()
            },
            backgroundColor = Color.Transparent,
            modifier = Modifier.padding(horizontal = 38.DP)
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {

                item{
                    Spacer(Modifier.height(10.DP))
                    ProfilePictureWithBackgroundPicture({},{})

                    Spacer(Modifier.height(10.DP))
                }

                profileItem.forEach {
                    item{
                        ProfileItemComp(it){
                            if(it.isPremium && !PillsTrackerSharedPrefs.SUB_STATUS){
                                viewModel.setSelectedGlobalNavItem(BottomNavItemsIdentifiers.PREMIUM_POPUP)
                            }else {
                                when (it.identifier) {
                                    ProfileItemIdentifier.CHART_AND_REPORT -> viewModel.setSelectedNavItem(BottomNavItemsIdentifiers.CALENDAR)
                                    ProfileItemIdentifier.PERIOD_AND_OVULATION -> viewModel.setSelectedNavItem(BottomNavItemsIdentifiers.HOME)

                                    ProfileItemIdentifier.APP_LOCK -> {
                                        viewModel.setSelectedGlobalNavItem(BottomNavItemsIdentifiers.ENTER_APP_LOCK)
                                    }

                                    ProfileItemIdentifier.REQUEST_FEATURE -> {
                                        CommonUtils.composeEmailForFeatureRequest(context)
                                    }

                                    ProfileItemIdentifier.SHARE_APP -> {
                                        CommonUtils.pillsTrackerShareIntent().also {
                                            context.startActivity(it)
                                        }
                                    }

                                    ProfileItemIdentifier.RATE_US -> {
                                        CommonUtils.openPillsTrackerOnPlayStore(context as Activity)
                                    }

                                    ProfileItemIdentifier.SUPPORT -> {
                                        CommonUtils.composeEmail(context)
                                    }

                                    ProfileItemIdentifier.PRIVACY_POLICY -> {
                                        CommonUtils.openWebPage(SignInPageUrls.POLICY.value, context)
                                    }

                                    ProfileItemIdentifier.TERMS_OF_USE -> {
                                        CommonUtils.openWebPage(SignInPageUrls.TERMS.value, context)
                                    }

                                    else -> {}
                                }
                            }
                        }

                        Spacer(Modifier.height(10.DP))
                    }
                }


            }
        }
    }

}