package com.invictus.pillstracker.utils.composeUtils.commonUi

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import com.invictus.pillstracker.R
import com.invictus.pillstracker.utils.displayUnitConverter.UnitConverter.DP
import com.invictus.pillstracker.utils.displayUnitConverter.UnitConverter.SP


@Composable
fun OrangeGradientButton(modifier: Modifier = Modifier, text : String, textSize : TextUnit = 34.SP,
                         textPaddingHorizontal : Dp = 38.DP, textPaddingVertical: Dp = 4.DP) {

    val verticalGradient = Brush.verticalGradient(
        colors = listOf(colorResource(id = R.color.orange_gradient_button_top), colorResource(id = R.color.orange_gradient_button_bottom)),
        startY = 0f,
        endY = 100f
    )

    Text(
        text = text,
        fontSize = textSize,
        style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.SemiBold, color = Color.White),
        modifier = modifier
            .padding(0.DP)
            .clip(RoundedCornerShape(60.DP))
            .background(brush = verticalGradient)
            .padding(textPaddingHorizontal,textPaddingVertical,textPaddingHorizontal,textPaddingVertical)
    )
}