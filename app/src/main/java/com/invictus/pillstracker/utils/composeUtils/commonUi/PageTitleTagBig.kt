package com.invictus.pillstracker.utils.composeUtils.commonUi

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.invictus.pillstracker.utils.displayUnitConverter.UnitConverter.DP
import com.invictus.pillstracker.utils.displayUnitConverter.UnitConverter.SP
import com.invictus.pillstracker.utils.composeUtils.theme.AppNameColor

@Composable
fun PageTitleTagBig(title: String) {

    Column(modifier = Modifier.fillMaxWidth()) {

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.DP)
        )

        AutoSizeText(
            text = title,
            textStyle = MaterialTheme.typography.h5.copy(
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.SemiBold,
                fontSize = 32.SP
            ),
            color = AppNameColor,
        )
    }
}