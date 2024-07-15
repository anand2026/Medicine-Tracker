package com.invictus.pillstracker.features.onBoardingFlow.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.invictus.pillstracker.R
import com.invictus.pillstracker.data.sharedPrefs.PillsTrackerSharedPrefs
import com.invictus.pillstracker.features.mainActivityPage.MainActivityPageViewModel
import com.invictus.pillstracker.features.mainActivityPage.data.BottomNavItemsIdentifiers
import com.invictus.pillstracker.utils.composeUtils.theme.splashScreenColor
import com.invictus.pillstracker.utils.composeUtils.theme.typo18Bold
import com.invictus.pillstracker.utils.composeUtils.theme.typo80Black
import com.invictus.pillstracker.utils.displayUnitConverter.UnitConverter.DP
import com.invictus.pillstracker.utils.displayUnitConverter.UnitConverter.SP
import kotlinx.coroutines.delay

@Composable
fun WelcomePage(mainActivityPageViewModel: MainActivityPageViewModel) {

    LaunchedEffect(key1 = Unit) {
        delay(1000)
        mainActivityPageViewModel.setSelectedGlobalNavItem(
            when{
                !PillsTrackerSharedPrefs.IS_ONBOARDING_COMPLETE -> BottomNavItemsIdentifiers.ONBOARDING_TRACK_YOUR_HEALTH
//                !PillsTrackerSharedPrefs.SUB_STATUS -> BottomNavItemsIdentifiers.PREMIUM_POPUP
                else-> {BottomNavItemsIdentifiers.NONE}
            }
        )
    }

    Surface(
        color = MaterialTheme.colors.splashScreenColor,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.align(Alignment.Center)
            ) {

                Image(
                    contentScale = ContentScale.FillBounds,
                    painter = painterResource(id = R.drawable.ic_logo_with_bg),
                    contentDescription = "PillsTracker Image",
                    modifier = Modifier
                        .size(100.DP)
                        .clip(RoundedCornerShape(12))
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.DP)
                )

                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.typo80Black.copy(fontSize = 22.SP),
                    color = MaterialTheme.colors.primary
                )
            }

            Text(
                text = stringResource(id = R.string.your_female_health_assistant),
                style = MaterialTheme.typography.typo18Bold,
                color = MaterialTheme.colors.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.DP, 32.DP)
                    .align(Alignment.BottomCenter)
            )
        }
    }

}
