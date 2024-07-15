package com.invictus.pillstracker.utils.composeUtils.commonUi

import android.animation.ValueAnimator
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.airbnb.lottie.LottieAnimationView

@Composable
fun LottieLoadingView(
    context: Context,
    file: String,
    modifier: Modifier = Modifier,
    animSpeed: Float = 1f,
    repeatValueAnimator: Int = ValueAnimator.INFINITE,
) {
    val lottieView = remember {
        LottieAnimationView(context).apply {
            setAnimation(file)
            repeatCount = repeatValueAnimator
            speed = animSpeed
        }
    }
    AndroidView(
        { lottieView },
        modifier = modifier
    ) {
        it.playAnimation()
    }
}
