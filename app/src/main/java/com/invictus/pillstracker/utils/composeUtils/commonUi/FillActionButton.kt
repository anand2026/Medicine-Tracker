package com.invictus.pillstracker.utils.composeUtils.commonUi

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import com.invictus.pillstracker.utils.composeUtils.theme.AccessibilityButtonColor
import com.invictus.pillstracker.utils.composeUtils.theme.AppNameColor
import com.invictus.pillstracker.utils.displayUnitConverter.UnitConverter.DP
import com.invictus.pillstracker.utils.displayUnitConverter.UnitConverter.SP

@Composable
fun FillActionButton(buttonText: String, textColor: Color = AppNameColor, buttonColor: Color = AccessibilityButtonColor, isFillWidth: Boolean = true, buttonIcon: Int? = null, fontSize : TextUnit = 24.SP, onClick: (() -> Unit)) {

    val modifier = if (isFillWidth) {
        Modifier.fillMaxWidth()
    } else {
        Modifier.wrapContentWidth()
    }


    Row(verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .clickable {
                onClick.invoke()
            }.semantics { this.contentDescription = "Action Click" }
            .background(buttonColor, RoundedCornerShape(10.DP))
            .padding(20.DP, 8.DP)) {

        buttonIcon?.let {
            Icon(
                tint = textColor,
                painter = painterResource(id = it),
                modifier = Modifier.size(16.DP),
                contentDescription = "Filled Button"
            )

            Spacer(modifier = Modifier.size(8.DP))
        }

        Text(
            text = buttonText,
            style = MaterialTheme.typography.h5.copy(
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                fontSize = fontSize
            ),
            color = textColor,
        )

    }


}