package com.invictus.pillstracker.utils.composeUtils.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.invictus.pillstracker.R
import com.invictus.pillstracker.utils.displayUnitConverter.UnitConverter.SP

val nunitoTypography = FontFamily(
    Font(R.font.nunito_regular,FontWeight.Normal),
    Font(R.font.nunito_medium,FontWeight.Medium),
    Font(R.font.nunito_bold, FontWeight.Bold),
    Font(R.font.nunito_light, FontWeight.Light),
    Font(R.font.nunito_extra_light, FontWeight.ExtraLight),
    Font(R.font.nunito_italic, FontWeight.Normal,style = FontStyle.Italic),
    Font(R.font.nunito_black, FontWeight.Black),
    Font(R.font.nunito_extra_bold, FontWeight.ExtraBold),
    Font(R.font.nunito_semi_bold, FontWeight.SemiBold),
)

val typography = Typography(

    h1 = TextStyle(
        fontFamily = nunitoTypography,
        fontWeight = FontWeight.Light,
        fontSize = 96.sp,
        letterSpacing = (-1.5).toFloat().sp,
    ),
    h2 = TextStyle(
        fontFamily = nunitoTypography,
        fontSize = 60.sp,
        letterSpacing = (-0.5).toFloat().sp
    ),
    h3 = TextStyle(
        fontFamily = nunitoTypography,
        fontWeight = FontWeight.Normal,
        fontSize = 48.sp,
        letterSpacing = 0.sp
    ),
    h4 = TextStyle(
        fontFamily = nunitoTypography,
        fontWeight = FontWeight.Normal,
        fontSize = 34.sp,
        letterSpacing = 0.25.toFloat().sp
    ),
    h5 = TextStyle(
        fontFamily = nunitoTypography,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        letterSpacing = 0.sp
    ),
    h6 = TextStyle(
        fontFamily = nunitoTypography,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        letterSpacing = 0.15.toFloat().sp
    ),
    subtitle1 = TextStyle(
        fontFamily = nunitoTypography,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.15.toFloat().sp,
        color = Color.Gray
    ),
    subtitle2 = TextStyle(
        fontFamily = nunitoTypography,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        letterSpacing = 0.1.toFloat().sp,
        color = Color.Gray
    ),
    body1 = TextStyle(
        fontFamily = nunitoTypography,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.5.toFloat().sp
    ),
    body2 = TextStyle(
        fontFamily = nunitoTypography,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        letterSpacing = 0.25.toFloat().sp
    ),
    button = TextStyle(
        fontFamily = nunitoTypography,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        letterSpacing = 1.25.toFloat().sp
    ),
    caption = TextStyle(
        fontFamily = nunitoTypography,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        letterSpacing = 0.4.toFloat().sp
    ),
    overline = TextStyle(
        fontFamily = nunitoTypography,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        letterSpacing = 1.5.toFloat().sp
    )
)

val Typography.typo80Black
    get() = TextStyle(
        fontFamily = nunitoTypography,
        fontWeight = FontWeight.Black,
        fontSize = 80.SP,
        lineHeight = 38.SP,
    )

val Typography.typo40ExtraBold
    get() = TextStyle(
        fontFamily = nunitoTypography,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 32.SP,
        lineHeight = 40.SP,
    )

val Typography.typo30Bold
    get() = TextStyle(
        fontFamily = nunitoTypography,
        fontWeight = FontWeight.Bold,
        fontSize = 30.SP,
        lineHeight = 38.SP,
    )

val Typography.typo34Bold
    get() = TextStyle(
        fontFamily = nunitoTypography,
        fontWeight = FontWeight.Bold,
        fontSize = 34.SP,
        lineHeight = 38.SP,
    )
val Typography.typo16SemiBold
    get() = TextStyle(
        fontFamily = nunitoTypography,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.SP,
        lineHeight = 18.SP,
    )

val Typography.typo20Bold
    get() = TextStyle(
        fontFamily = nunitoTypography,
        fontWeight = FontWeight.Bold,
        fontSize = 20.SP,
        lineHeight = 24.SP,
    )

val Typography.typo20SemiBold
    get() = TextStyle(
        fontFamily = nunitoTypography,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.SP,
        lineHeight = 24.SP,
    )

val Typography.typo16Normal
    get() = TextStyle(
        fontFamily = nunitoTypography,
        fontWeight = FontWeight.Normal,
        fontSize = 16.SP,
        lineHeight = 18.SP,
    )

val Typography.typo26SemiBold
    get() = TextStyle(
        fontFamily = nunitoTypography,
        fontWeight = FontWeight.SemiBold,
        fontSize = 26.SP,
        lineHeight = 34.SP,
    )
val Typography.typo22SemiBold
    get() = TextStyle(
        fontFamily = nunitoTypography,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.SP,
        lineHeight = 26.SP,
    )

val Typography.typo18Bold
    get() = TextStyle(
        fontFamily = nunitoTypography,
        fontWeight = FontWeight.Bold,
        fontSize = 18.SP,
        lineHeight = 26.SP,
    )

val Typography.typo15SemiBold
    get() = TextStyle(
        fontFamily = nunitoTypography,
        fontWeight = FontWeight.SemiBold,
        fontSize = 15.SP,
        lineHeight = 23.SP,
    )
val Typography.typo15Bold
    get() = TextStyle(
        fontFamily = nunitoTypography,
        fontWeight = FontWeight.Bold,
        fontSize = 15.SP,
        lineHeight = 23.SP,
    )

val Typography.typo15Normal
    get() = TextStyle(
        fontFamily = nunitoTypography,
        fontWeight = FontWeight.Normal,
        fontSize = 13.SP,
        lineHeight = 23.SP,
    )

val Typography.typo14SemiBold
    get() = TextStyle(
        fontFamily = nunitoTypography,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.SP,
        lineHeight = 22.SP,
    )
val Typography.typo14Bold
    get() = TextStyle(
        fontFamily = nunitoTypography,
        fontWeight = FontWeight.Bold,
        fontSize = 14.SP,
        lineHeight = 14.SP,
    )

val Typography.typo13Normal
    get() = TextStyle(
        fontFamily = nunitoTypography,
        fontWeight = FontWeight.Normal,
        fontSize = 13.SP,
        lineHeight = 16.SP,
    )

val Typography.typo12Normal
    get() = TextStyle(
        fontFamily = nunitoTypography,
        fontWeight = FontWeight.Normal,
        fontSize = 12.SP,
        lineHeight = 16.SP,
    )

val Typography.typo12SemiBold
    get() = TextStyle(
        fontFamily = nunitoTypography,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.SP,
        lineHeight = 16.SP,
    )

val Typography.typo12Bold
    get() = TextStyle(
        fontFamily = nunitoTypography,
        fontWeight = FontWeight.Bold,
        fontSize = 12.SP,
        lineHeight = 16.SP,
    )

val Typography.typo7SemiBold
    get() = TextStyle(
        fontFamily = nunitoTypography,
        fontWeight = FontWeight.SemiBold,
        fontSize = 7.SP,
        lineHeight = 16.SP,
    )
val Typography.typo10Normal
    get() = TextStyle(
        fontFamily = nunitoTypography,
        fontWeight = FontWeight.Normal,
        fontSize = 10.SP,
        lineHeight = 16.SP,
    )