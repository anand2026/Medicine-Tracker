package com.invictus.pillstracker.features.onBoardingFlow.component

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.invictus.pillstracker.R
import com.invictus.pillstracker.features.mainActivityPage.MainActivityPageViewModel
import com.invictus.pillstracker.features.mainActivityPage.data.BottomNavItemsIdentifiers
import com.invictus.pillstracker.utils.CommonUtils
import com.invictus.pillstracker.utils.SignInPageUrls
import com.invictus.pillstracker.utils.composeUtils.theme.FFAFE9E5
import com.invictus.pillstracker.utils.composeUtils.theme.allCorner22
import com.invictus.pillstracker.utils.composeUtils.theme.appBackgroundColor
import com.invictus.pillstracker.utils.composeUtils.theme.extensions.noRippleClickable
import com.invictus.pillstracker.utils.composeUtils.theme.rightCorner60
import com.invictus.pillstracker.utils.composeUtils.theme.textColor
import com.invictus.pillstracker.utils.composeUtils.theme.typo15Normal
import com.invictus.pillstracker.utils.composeUtils.theme.typo15SemiBold
import com.invictus.pillstracker.utils.composeUtils.theme.typo30Bold
import com.invictus.pillstracker.utils.displayUnitConverter.UnitConverter.DP
import timber.log.Timber

@Composable
fun TrackYourHealthActivity(mainActivityPageViewModel: MainActivityPageViewModel) {

    val context = LocalContext.current

    Surface(
        color = FFAFE9E5,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Image(
                painter = painterResource(id = R.drawable.bg_onboarding_1),
                contentDescription = "Intro Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
            
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 50.DP)
                    .background(
                        MaterialTheme.colors.appBackgroundColor,
                        MaterialTheme.shapes.rightCorner60
                    )
                    .padding(top = 50.DP, start = 50.DP, end = 50.DP, bottom = 24.DP,)
                    .align(Alignment.BottomStart),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = stringResource(R.string.track_your_activity),
                    style = MaterialTheme.typography.typo30Bold,
                    color = MaterialTheme.colors.textColor
                )

                Spacer(Modifier.height(23.DP))

                Text(
                    text = stringResource(id = R.string.track_health_description),
                    style = MaterialTheme.typography.typo15Normal,
                    color = MaterialTheme.colors.textColor
                )

                Spacer(Modifier.height(30.DP))




                NextButton(stringResource(id = R.string.continue_tag)){
                    mainActivityPageViewModel.setSelectedGlobalNavItem(BottomNavItemsIdentifiers.LOG_INITIAL_INFO)
                }

                Spacer(Modifier.height(4.DP))

                TermsCheckBox(context)

                Spacer(Modifier.height(16.DP))
//                Spacer(Modifier.height(13.DP))
//
//                Text(
//                    text = stringResource(R.string.skip),
//                    style = MaterialTheme.typography.typo15Normal,
//                    color = MaterialTheme.colors.textColor
//                )

            }
        }
    }
}

@Composable
fun NextButton(text: String, callback: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth().noRippleClickable { callback() }.semantics { this.contentDescription = "Next" }
            .background(MaterialTheme.colors.primary, MaterialTheme.shapes.allCorner22),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.typo15SemiBold,
            color = MaterialTheme.colors.textColor,
            modifier = Modifier.align(Alignment.Center)
                .padding(20.DP)
        )
    }
}

@Composable
private fun TermsCheckBox(context: Context) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {

//        CheckBoxUi(isCheckBoxChecked)

        ClickableTextBetweenSentences(
            fullSentence = stringResource(id = R.string.terms_approve_checkbox_message)
                .replace("123", stringResource(id = R.string.privacy_policy))
                .replace("987", stringResource(id = R.string.terms_of_Use)),
            clickableTexts = listOf(
                stringResource(id = R.string.privacy_policy),
                stringResource(id = R.string.terms_of_Use)
            ),
            onClick = {
                when (it) {
                    context.getString(R.string.privacy_policy) -> CommonUtils.openWebPage(SignInPageUrls.POLICY.value, context)
                    context.getString(R.string.terms_of_Use) -> CommonUtils.openWebPage(SignInPageUrls.TERMS.value, context)
                    else -> Timber.d("==>>")
                }
            }
        )
    }
}