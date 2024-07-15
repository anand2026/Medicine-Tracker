package com.invictus.pillstracker.utils.composeUtils.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.invictus.pillstracker.utils.composeUtils.theme.appBackgroundColor

// dark palettes
private val lightColorPalette = lightColors(
    primary = primaryColor,
    secondary = secondaryColor,
)

private val darkColorPalette = darkColors(
    primary = primaryColor,
    secondary = secondaryColor,
)


@Composable
fun ComposePillsTrackerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {



    MaterialTheme(
        typography = typography,
        colors = if(darkTheme) darkColorPalette else lightColorPalette,
        shapes = shapes,
        content = content
    )
}
