package com.invictus.pillstracker.utils.composeUtils.commonUi

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.invictus.pillstracker.utils.displayUnitConverter.UnitConverter.DP
import com.invictus.pillstracker.utils.displayUnitConverter.UnitConverter.SP
import com.invictus.pillstracker.utils.composeUtils.theme.AppNameColor

@Composable
fun PageTitleBarWithClose(closeIcon: Int, pageTitle: String, titleLottie: String = "", isClose: (() -> Unit)) {

    val context = LocalContext.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Image(
            contentScale = ContentScale.FillBounds,
            painter = painterResource(id = closeIcon),
            contentDescription = null,
            modifier = Modifier
                .size(42.DP)
                .clickable {
                    isClose.invoke()
                }
        )

        Spacer(
            modifier = Modifier.size(
                if (titleLottie.isNotEmpty()) {
                    50.DP
                } else {
                    15.DP
                }
            )
        )

        Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {

            AutoSizeText(
                text = pageTitle,
                textStyle = MaterialTheme.typography.h5.copy(
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.SP
                ),
                color = AppNameColor,
            )

            if (titleLottie.isNotEmpty()) {
                Column {
                    LottieLoadingView(
                        context = context,
                        modifier = Modifier
                            .size(50.DP),
                        file = titleLottie
                    )
                    Spacer(modifier = Modifier.size(6.DP))
                }
            }
        }

        Spacer(modifier = Modifier.size(58.DP, 42.DP))

    }
}