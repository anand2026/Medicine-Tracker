package com.invictus.pillstracker.utils.composeUtils.commonUi

import android.animation.ValueAnimator
import android.content.Context
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.viewinterop.AndroidView
import com.airbnb.lottie.LottieAnimationView
import com.invictus.pillstracker.R
import com.invictus.pillstracker.utils.displayUnitConverter.UnitConverter.DP
import timber.log.Timber

//@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PillsTrackerLoadingView(
    context: Context,
    isVisible: Boolean,
    modifier: Modifier = Modifier,
    backgroundColor: Color = colorResource(id = R.color.overlay_dark_60),
) {
    Crossfade(targetState = isVisible, label = "") { state ->
        if (state) {

            val lottieView = remember {
                LottieAnimationView(context).apply {
                    setAnimation("detox_loading.json")
                    repeatCount = ValueAnimator.INFINITE
                    speed = 0.7f
                }
            }

            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(backgroundColor)
                    .clickable {
                        Timber.d("==>>")
                    }.semantics { this.contentDescription = "Loading" }, contentAlignment = Alignment.Center
            ) {

                AndroidView(
                    { lottieView },
                    modifier = modifier.size(120.DP)
                ) {
                    it.playAnimation()
                }
            }
        }
    }
}

