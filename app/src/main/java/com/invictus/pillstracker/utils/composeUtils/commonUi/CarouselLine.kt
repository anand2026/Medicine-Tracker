package com.invictus.pillstracker.utils.composeUtils.commonUi

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.invictus.pillstracker.R
import com.invictus.pillstracker.utils.displayUnitConverter.UnitConverter.DP

@Composable
fun CarouselLine(selected: Boolean) {
    Spacer(modifier = Modifier
        .height(4.DP)
        .width(24.DP)
        .background(colorResource(id = if(selected) R.color.white else android.R.color.transparent), RoundedCornerShape(20.DP)))
}