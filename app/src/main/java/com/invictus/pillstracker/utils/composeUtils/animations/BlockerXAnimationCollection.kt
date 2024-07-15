package com.invictus.pillstracker.utils.composeUtils.animations

import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SlideUpAnimation(content: @Composable() AnimatedVisibilityScope.() -> Unit) {
    val visibleState = remember { MutableTransitionState(false) }
        .apply { targetState = true }
    AnimatedVisibility(
        visibleState = visibleState,
        enter = slideInVertically(
            // Start the slide from 40 (pixels) above where the content is supposed to go, to
            // produce a parallax effect
            animationSpec = tween(durationMillis = 1000),
            initialOffsetY = { 100 }
        ) + fadeIn(animationSpec = tween(durationMillis = 1000), initialAlpha = 0f),
        exit = slideOutVertically() + fadeOut() + scaleOut(targetScale = 1.2f)
    ) {
        content()
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ScaleInAnimation(content: @Composable() AnimatedVisibilityScope.() -> Unit) {
    val visibleState1 = remember { MutableTransitionState(false) }
        .apply { targetState = true }
    AnimatedVisibility(
        visibleState = visibleState1,
        enter = scaleIn(animationSpec = tween(durationMillis = 1000)),
        exit = scaleOut(animationSpec = tween(durationMillis = 1000))
    ) {

        content()
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HorizontalSlideInAnimation( content: @Composable() AnimatedVisibilityScope.() -> Unit) {
    val visibleState = remember { MutableTransitionState(false) }
        .apply { targetState = true }
    AnimatedVisibility(
        visibleState = visibleState,
        enter = slideInHorizontally(animationSpec = tween(durationMillis = 1000)),
        exit = slideOutHorizontally(animationSpec = tween(durationMillis = 1000))
    ) {

        content()
    }
}

@Composable
fun HorizontalSlideRightToLeftInAnimation(visibleState: MutableTransitionState<Boolean>, content: @Composable() AnimatedVisibilityScope.() -> Unit) {
    AnimatedVisibility(
        visibleState = visibleState,
        enter = slideInHorizontally(initialOffsetX = { 1000 },animationSpec = tween(durationMillis = 1000)),
        exit = slideOutHorizontally(animationSpec = tween(durationMillis = 1000))
    ) {

        content()
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ScaleInSlideDownAnimation(visibleState: MutableTransitionState<Boolean>, content: @Composable() AnimatedVisibilityScope.() -> Unit) {
    AnimatedVisibility(
        visibleState = visibleState,
        enter = scaleIn(animationSpec = tween(durationMillis = 1000), initialScale = 50f),
        exit = scaleOut(animationSpec = tween(durationMillis = 1000))
    ) {

        content()
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SlideDownAnimation(
    visibleState: MutableTransitionState<Boolean>,
    content: @Composable() AnimatedVisibilityScope.() -> Unit,
) {
    AnimatedVisibility(
        visibleState = visibleState,
        enter = scaleIn(animationSpec = tween(durationMillis = 1000)) + slideInVertically(animationSpec = tween(durationMillis = 1000)),
        exit = scaleOut(animationSpec = tween(durationMillis = 1000))
    ) {
        content()
    }
}