package com.invictus.pillstracker.features.profile.data

import androidx.annotation.Keep
import com.invictus.pillstracker.R

@Keep
data class ProfilePageDataItem(
    val icon:Int = R.drawable.ic_profile,
    val title:String = "",
    val isForwardArrowVisible: Boolean = false,
    val identifier: ProfileItemIdentifier = ProfileItemIdentifier.SUPPORT,
    val isPremium: Boolean = false
)

enum class ProfileItemIdentifier{
    CHART_AND_REPORT,
    PERIOD_AND_OVULATION,
    APP_LOCK,
    REQUEST_FEATURE,
    SHARE_APP,
    RATE_US,
    SUPPORT,
    PRIVACY_POLICY,
    TERMS_OF_USE,
}