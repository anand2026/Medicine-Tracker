package com.invictus.pillstracker.features.autoDateTimePage.component

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.invictus.pillstracker.R
import com.invictus.pillstracker.features.autoDateTimePage.utils.AutoDateTimePageUtil
import com.invictus.pillstracker.utils.composeUtils.commonUi.FillActionButton
import com.invictus.pillstracker.utils.composeUtils.commonUi.SimpleVideoPlayer
import com.invictus.pillstracker.utils.composeUtils.theme.AppNameColor
import com.invictus.pillstracker.utils.displayUnitConverter.UnitConverter.DP
import com.invictus.pillstracker.utils.displayUnitConverter.UnitConverter.SP
import splitties.toast.toast


@Composable
fun AutoDateTimePageHome(permissionGiven: () -> Unit) {

    val context = LocalContext.current
    val activity = LocalContext.current as Activity

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (AutoDateTimePageUtil.isAutoTimeOff()) {
            toast(R.string.something_went_wrong_try_again)
        } else {
            permissionGiven()
        }
    }

    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {


        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {

            item {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.DP)
                )
            }


            item {
                Box(
                    modifier = Modifier
                        .padding(24.DP, 0.DP)
                        .fillMaxWidth()
                        .aspectRatio(1f)
//                        .border(2.DP, HomeFloatingButtonColor, RoundedCornerShape(0.DP))
                ) {
                    SimpleVideoPlayer(context, "instructions_datetime.mp4")
                }
            }


            item {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.DP)
                )
            }

            item {
                Text(
                    text = stringResource(id = R.string.no_auto_time_title),
                    style = MaterialTheme.typography.h5.copy(
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 24.SP
                    ),
                    color = AppNameColor,
                )
            }

            item {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.DP)
                )
            }

            item {
                Text(
                    text = stringResource(id = R.string.no_auto_time_desc),
                    style = MaterialTheme.typography.h5.copy(
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.SP
                    ),
                    color = AppNameColor,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(36.DP, 0.DP)
                )
            }

            item {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(32.DP)
                )
            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.DP, 0.DP)
                ) {
                    FillActionButton(stringResource(id = R.string.turn_on)) {
                        launcher.launch(AutoDateTimePageUtil.dateSettingsPageIntent())
                        toast(R.string.no_auto_time_desc)
                    }
                }
            }



            item {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.DP)
                )
            }
        }
    }
}


