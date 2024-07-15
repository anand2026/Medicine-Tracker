package com.invictus.pillstracker.utils.composeUtils.commonUi

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.invictus.pillstracker.utils.displayUnitConverter.UnitConverter.DP
import com.invictus.pillstracker.utils.composeUtils.theme.HomeFloatingButtonColor
import com.invictus.pillstracker.utils.composeUtils.theme.TimerProgressColor

@Composable
fun DetoxSwitch(
    switchWidth: Int,
    isEnable: Boolean,
    switchOffBgColor: Color = TimerProgressColor,
    switchOnBgColor: Color = HomeFloatingButtonColor,
    switchOnRoundColor: Color = Color.White,
    switchOffRoundColor: Color = Color.White,
    switchBorderColor: Color = Color.Transparent,
    onSwitchClick: ((Boolean) -> Unit)
) {

    val switchMainPosition = (switchWidth.div(3.79)).toFloat()
    val animatableValue = remember { Animatable(-switchMainPosition) }
    animatableValue.updateBounds(-switchMainPosition, switchMainPosition)
    val interactionSource = remember { MutableInteractionSource() }

    val (switchMainColor, switchBackground) = if (isEnable) {
        switchOnRoundColor to switchOnBgColor
    } else {
        switchOffRoundColor to switchOffBgColor
    }


    Box(
        modifier = Modifier
            .width(switchWidth.DP)
            .aspectRatio(2.185f)
            .border(
                border = BorderStroke(1.DP, switchBorderColor),
                shape = RoundedCornerShape(50),
            )
            .clickable(indication = null, interactionSource = interactionSource) {
                onSwitchClick.invoke(!isEnable)
            }
            .background(switchBackground, RoundedCornerShape(50)),
        contentAlignment = Alignment.Center
    ) {

        Box(
            Modifier
                .offset(x = Dp(animatableValue.value))
                .background(switchMainColor, RoundedCornerShape(50))
                .size(switchWidth.div(2.69).toFloat().DP)
        ) {
            LaunchedEffect(isEnable) {
                if (isEnable) {
                    animatableValue.animateTo(switchMainPosition)
                } else {
                    animatableValue.animateTo(-switchMainPosition)
                }
            }
        }
    }
}

