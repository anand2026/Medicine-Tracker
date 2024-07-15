package com.invictus.pillstracker.utils.composeUtils.commonUi

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.invictus.pillstracker.R
import com.invictus.pillstracker.utils.composeUtils.theme.AppNameColor
import com.invictus.pillstracker.utils.composeUtils.theme.DialogCancelColor
import com.invictus.pillstracker.utils.composeUtils.theme.HomeFloatingButtonColor
import com.invictus.pillstracker.utils.displayUnitConverter.UnitConverter.DP
import com.invictus.pillstracker.utils.displayUnitConverter.UnitConverter.SP

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DtoxAlertDialog(icon: Int? = null, title: String = "", message: String, showAlertDialog: MutableState<Boolean>, isSubmitClick: (() -> Unit)?) {

    if (!showAlertDialog.value) return

    Dialog(
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
        onDismissRequest = {
            showAlertDialog.value = false
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.DP)
                .background(Color.White, RoundedCornerShape(15.DP))
                .padding(24.DP)
        ) {

            if (title.isNotEmpty()) {

                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {

                    icon?.let {
                        Image(
                            contentScale = ContentScale.FillBounds,
                            painter = painterResource(id = it),
                            contentDescription = "Alert Image",
                            modifier = Modifier.size(40.DP)
                        )

                        Spacer(modifier = Modifier.size(8.DP))
                    }

                    Text(
                        text = title,
                        style = MaterialTheme.typography.h5.copy(
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.SP
                        ),
                        color = AppNameColor,
                    )
                }

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.DP)
                )
            }

            Text(
                text = message,
                style = MaterialTheme.typography.h5.copy(
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.SP
                ),
                color = AppNameColor,
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(18.DP)
            )

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    modifier = Modifier.clickable {
                        showAlertDialog.value = false
                    }.semantics { this.contentDescription = "Day" },
                    text = stringResource(id = R.string.cancel_tag),
                    style = MaterialTheme.typography.h5.copy(
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.SP
                    ),
                    color = DialogCancelColor,
                )

                Spacer(
                    modifier = Modifier.size(34.DP, 16.DP)
                )

                Text(
                    modifier = Modifier.clickable {
                        isSubmitClick?.invoke()
                        showAlertDialog.value = false
                    },
                    text = stringResource(id = R.string.yes),
                    style = MaterialTheme.typography.h5.copy(
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.SP
                    ),
                    color = HomeFloatingButtonColor,
                )
            }
        }
    }
}