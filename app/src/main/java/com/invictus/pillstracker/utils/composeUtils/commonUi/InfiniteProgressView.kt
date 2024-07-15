package com.invictus.pillstracker.utils.composeUtils.commonUi

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import com.invictus.pillstracker.utils.composeUtils.theme.blue200
import com.invictus.pillstracker.utils.composeUtils.theme.blue500
import com.invictus.pillstracker.utils.displayUnitConverter.UnitConverter.DP


@Composable
fun InfiniteProgressView(
    modifier: Modifier = Modifier,
    progressColor: Color = blue500,
    progressBackgroundColor: Color = blue200,
    strokeWidth: Dp = 10.DP,
    strokeBackgroundWidth: Dp = 10.DP,
    progressDirection: AnimationDirection = AnimationDirection.RIGHT,
    roundedBorder: Boolean = false,
    durationInMilliSecond: Int = 800,
    radius: Dp = 80.DP
) {


    val stroke = with(LocalDensity.current) {
        Stroke(width = strokeWidth.toPx(), cap = if (roundedBorder) StrokeCap.Round else StrokeCap.Square)
    }

    val strokeBackground = with(LocalDensity.current) {
        Stroke(width = strokeBackgroundWidth.toPx())
    }

    val transition = rememberInfiniteTransition()

    val animatedRestart by transition.animateFloat(
        initialValue = 0f,
        targetValue = if (progressDirection == AnimationDirection.RIGHT) 360f else -360f,
        animationSpec = infiniteRepeatable(tween(durationInMilliSecond, easing = LinearEasing))
    )

    Canvas(
        modifier
            .size(radius * 2)
    ) {


        val higherStrokeWidth =
            if (stroke.width > strokeBackground.width) stroke.width else strokeBackground.width
        val radius = (size.minDimension - higherStrokeWidth) / 2
        val halfSize = size / 2.0f
        val topLeft = Offset(
            halfSize.width - radius,
            halfSize.height - radius
        )
        val size = Size(radius * 2, radius * 2)

        drawArc(
            startAngle = 0f,
            sweepAngle = 360f,
            topLeft = topLeft,
            color = progressBackgroundColor,
            useCenter = false,
            size = size,
            style = strokeBackground
        )

        drawArc(
            color = progressColor,
            startAngle = animatedRestart,
            sweepAngle = 90f,
            useCenter = false,
            topLeft = topLeft,
            size = size,
            style = stroke,
        )
    }
}