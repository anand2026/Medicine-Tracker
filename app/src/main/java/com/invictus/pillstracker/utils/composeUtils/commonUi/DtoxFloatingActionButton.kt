package com.invictus.pillstracker.utils.composeUtils.commonUi

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.invictus.pillstracker.utils.displayUnitConverter.UnitConverter.DP
import com.invictus.pillstracker.utils.composeUtils.theme.InAppBlockingFloatingGradientLowerColor
import com.invictus.pillstracker.utils.composeUtils.theme.InAppBlockingFloatingGradientUpperColor

@Composable
fun DtoxFloatingActionButton(
    modifier: Modifier = Modifier,
    brush: Brush = Brush.verticalGradient(
        colors = listOf(InAppBlockingFloatingGradientUpperColor, InAppBlockingFloatingGradientLowerColor),
        startY = 0f,
        endY = 180f
    ),
    size : Int = 80,
    onClick: (() -> Unit)
) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(size.DP)
            .background(brush = brush, shape = CircleShape)
            .clickable {
                onClick.invoke()
            },
    ) {
        Icon(
            modifier = Modifier.size(size.div(2.0).toFloat().DP),
            tint = Color.White,
            imageVector = Icons.Filled.Add,
            contentDescription = null
        )
    }
}