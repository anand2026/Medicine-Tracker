package com.invictus.pillstracker.utils.composeUtils.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import com.invictus.pillstracker.utils.displayUnitConverter.UnitConverter.DP

val shapes = Shapes(
    small = RoundedCornerShape(4.DP),
    medium = RoundedCornerShape(4.DP),
    large = RoundedCornerShape(0.DP)
)

val Shapes.noRoundedCorner
    get() = RoundedCornerShape(0.DP)
val Shapes.rightCorner60
    get() = RoundedCornerShape(topEnd = 60.DP)
val Shapes.allCorner22
    get() = RoundedCornerShape(22.DP)
val Shapes.allCorner10
    get() = RoundedCornerShape(10.DP)
val Shapes.allCorner50
    get() = RoundedCornerShape(50.DP)
val Shapes.allCorner100
    get() = RoundedCornerShape(100.DP)
val Shapes.topCorner20
    get() = RoundedCornerShape(topStart = 20.DP, topEnd = 20.DP)