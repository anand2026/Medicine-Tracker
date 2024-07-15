package com.invictus.pillstracker.features.pinLock

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material.icons.outlined.Fingerprint
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import com.invictus.pillstracker.R
import com.invictus.pillstracker.utils.composeUtils.commonUi.LottieLoadingView
import com.invictus.pillstracker.utils.composeUtils.theme.appBackgroundColor
import com.invictus.pillstracker.utils.composeUtils.theme.textColor
import com.invictus.pillstracker.utils.composeUtils.theme.typo15Normal
import com.invictus.pillstracker.utils.composeUtils.theme.typo30Bold
import com.invictus.pillstracker.utils.displayUnitConverter.UnitConverter.DP
import kotlinx.coroutines.delay

const val pinSize = 4
//const val password = "2580"

@Composable
fun PinLockView(
    title: String,
    password: String,
    isAskReEnter: Boolean = false,
    reEnterCallback: ((String) -> Unit)? = null,
    closeCallback: (() -> Unit)? = null,
    callback: () -> Unit
) {
    var inputPin = remember { mutableStateListOf<Int>() }
    val error = remember { mutableStateOf("") }
    val showSuccess = remember { mutableStateOf(false) }

    val context = LocalContext.current

    // Calculation logic should reside in view model or presenter
    if (inputPin.size == 4) {

        if(isAskReEnter  && password.isNotEmpty()) {
            if (inputPin.joinToString("") == password) {
                showSuccess.value = true
                error.value = ""
            } else {
                reEnterCallback?.invoke("")
                inputPin.clear()
                error.value = stringResource(R.string.wrong_pin)
            }
        }else if(isAskReEnter) {
            reEnterCallback?.invoke(inputPin.joinToString(""))
            inputPin.clear()
        } else if (inputPin.joinToString("") == password) {
            showSuccess.value = true
            error.value = ""
        } else {
            inputPin.clear()
            error.value = stringResource(R.string.wrong_pin)
        }
    }

    LaunchedEffect(key1 = showSuccess.value){
        if(showSuccess.value){
            delay(1500)
            callback()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.appBackgroundColor),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.DP))

            Icon(
                painter = painterResource(id = R.drawable.lock_icon),
                contentDescription = "lock icon",
                modifier = Modifier.padding(64.DP),
                tint = MaterialTheme.colors.primary
            )

            Text(
                text = title,
                style = MaterialTheme.typography.typo30Bold,
                color = MaterialTheme.colors.textColor,
                modifier = Modifier.padding(16.DP)
            )

            Spacer(modifier = Modifier.height(20.DP))

            if (showSuccess.value) {
                LottieLoadingView(
                    context = context, file =
                    "success.json",
                    modifier = Modifier.size(100.DP)
                )
            } else {
                // PIN ICONS
                Row {
                    (0 until pinSize).forEach {
                        Icon(
                            imageVector = if (inputPin.size > it) Icons.Default.Circle else Icons.Outlined.Circle,
                            contentDescription = it.toString(),
                            modifier = Modifier.padding(8.DP),
                            tint = MaterialTheme.colors.primary
                        )
                    }
                }

                Text(
                    text = error.value,
                    color = MaterialTheme.colors.error,
                    modifier = Modifier.padding(16.DP)
                )

                Spacer(modifier = Modifier.height(10.DP))

                Row(
                    modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement
                        .SpaceEvenly
                ) {
                    (1..3).forEach {
                        PinKeyItem(onClick = { inputPin.add(it) }) {
                            Text(
                                text = it.toString(),
                                style = MaterialTheme.typography.typo30Bold,
                                color = MaterialTheme.colors.textColor,
                                modifier = Modifier.padding(4.DP)
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement
                        .SpaceEvenly
                ) {
                    (4..6).forEach {
                        PinKeyItem(onClick = { inputPin.add(it) }) {
                            Text(
                                text = it.toString(),
                                style = MaterialTheme.typography.typo30Bold,
                                color = MaterialTheme.colors.textColor,
                                modifier = Modifier.padding(4.DP)
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement
                        .SpaceEvenly
                ) {
                    (7..9).forEach {
                        PinKeyItem(onClick = { inputPin.add(it) }) {
                            Text(
                                text = it.toString(),
                                style = MaterialTheme.typography.typo30Bold,
                                color = MaterialTheme.colors.textColor,
                                modifier = Modifier.padding(4.DP)
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.DP), horizontalArrangement =
                    Arrangement
                        .SpaceEvenly, verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Outlined.Fingerprint,
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary,
                        modifier = Modifier
                            .size(36.DP)
                            .clickable {
//                        context.startActivity(BiometricActivity.newIntent(context))
                            }
                    )

                    PinKeyItem(
                        onClick = { inputPin.add(0) }, modifier = Modifier.padding(
                            horizontal = 16
                                .DP, vertical = 8.DP
                        )
                    ) {
                        Text(
                            text = "0",
                            style = MaterialTheme.typography.typo30Bold,
                            color = MaterialTheme.colors.textColor,
                            modifier = Modifier.padding(4.DP)
                        )
                    }

                    Icon(
                        imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary,
                        modifier = Modifier
                            .size(36.DP)
                            .clickable {
                                if (inputPin.isNotEmpty()) {
                                    inputPin.removeLast()
                                }
                            }
                    )
                }
            }
        }

        if (isAskReEnter) {
            Box(
                modifier = Modifier
                    .padding(20.DP)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_close_24),
                    contentDescription = null,
                    tint = MaterialTheme.colors.textColor,
                    modifier = Modifier
                        .size(32.DP)
                        .clickable {
                            closeCallback?.invoke()
                        }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PinKeyItem(
    onClick: () -> Unit,
    modifier: Modifier = Modifier.padding(8.DP),
    shape: Shape = androidx.compose.material.MaterialTheme.shapes.small.copy(CornerSize(percent = 50)),
    backgroundColor: Color = MaterialTheme.colors.primary,
    contentColor: Color = contentColorFor(backgroundColor),
    elevation: Dp = 4.DP,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier.clickable { onClick.invoke() },
        shape = shape,
        color = backgroundColor,
        contentColor = contentColor,
        elevation = elevation,
    ) {
        CompositionLocalProvider(LocalContentAlpha provides contentColor.alpha) {
            ProvideTextStyle(MaterialTheme.typography.typo15Normal) {
                Box(
                    modifier = Modifier
                        .defaultMinSize(minWidth = 64.DP, minHeight = 64.DP),
                    contentAlignment = Alignment.Center
                ) { content() }
            }
        }
    }
}


