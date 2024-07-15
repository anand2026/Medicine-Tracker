package com.invictus.pillstracker.features.turnNetOnPage.component

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.invictus.pillstracker.R
import com.invictus.pillstracker.features.turnNetOnPage.utils.TurnNetOnPageUtils
import com.invictus.pillstracker.utils.composeUtils.commonUi.FillActionButton
import com.invictus.pillstracker.utils.composeUtils.theme.AppNameColor
import com.invictus.pillstracker.utils.displayUnitConverter.UnitConverter.DP
import com.invictus.pillstracker.utils.displayUnitConverter.UnitConverter.SP
import splitties.toast.toast


@Composable
fun TurnNetOnPageHome() {

    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (!TurnNetOnPageUtils.isOnline()) toast(R.string.something_went_wrong_try_again)
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
                        .height(100.DP)
                )
            }

            item {



                Image(
                    contentScale = ContentScale.FillBounds,
                    painter = painterResource(id = R.drawable.bg_no_internet),
                    contentDescription = "No Internet",
                    modifier = Modifier
                        .size(320.DP)
                )
            }

            item {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.DP)
                )
            }

            item {
                Text(
                    text = stringResource(id = R.string.no_internet_title),
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
                    text = stringResource(id = R.string.no_internet_desc),
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
                        launcher.launch(TurnNetOnPageUtils.settingPageIntent())
                        toast(R.string.no_internet_desc)
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


