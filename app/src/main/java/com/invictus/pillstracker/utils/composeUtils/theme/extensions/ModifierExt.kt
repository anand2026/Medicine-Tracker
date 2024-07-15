package com.invictus.pillstracker.utils.composeUtils.theme.extensions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import com.invictus.pillstracker.utils.displayUnitConverter.UnitConverter.DP

inline fun Modifier.noRippleClickable(
    crossinline onClick: () -> Unit
): Modifier = composed {
    val interactionSource = remember { MutableInteractionSource() }
    clickable(
        indication = null,
        interactionSource = interactionSource
    ) {
        onClick()
    }
}

inline fun Modifier.rippleClickable(
    crossinline onClick: () -> Unit
): Modifier = composed {
    val interactionSource = remember { MutableInteractionSource() }
    clickable(
        onClick = {
            onClick()
        },
        interactionSource = interactionSource,
        indication = rememberRipple(
            radius = 190.DP,
        )
    )
}