package com.invictus.pillstracker.features.profile.utils

import com.invictus.pillstracker.R
import com.invictus.pillstracker.features.profile.data.ProfileItemIdentifier
import com.invictus.pillstracker.features.profile.data.ProfilePageDataItem
import splitties.resources.appStr

object ProfilePageUtils {

    fun profilePageItems(): ArrayList<ProfilePageDataItem> {
        return arrayListOf(
//            ProfilePageDataItem(
//                icon = R.drawable.ic_outline_show_chart,
//                title = appStr(R.string.chart_and_report),
//                isForwardArrowVisible = true,
//                identifier = ProfileItemIdentifier.CHART_AND_REPORT
//            ),
//            ProfilePageDataItem(
//                icon = R.drawable.ic_icons_heart,
//                title = appStr(R.string.period_and_ovulation),
//                isForwardArrowVisible = true,
//                identifier = ProfileItemIdentifier.PERIOD_AND_OVULATION
//            ),
            ProfilePageDataItem(
                icon = R.drawable.lock_icon,
                title = appStr(R.string.app_lock),
                isForwardArrowVisible = true,
                identifier = ProfileItemIdentifier.APP_LOCK,
                isPremium = true
            ),
            ProfilePageDataItem(
                icon = R.drawable.request_feature,
                title = appStr(R.string.request_feature),
                isForwardArrowVisible = true,
                identifier = ProfileItemIdentifier.REQUEST_FEATURE
            ),
            ProfilePageDataItem(
                icon = R.drawable.ic_help_circle,
                title = appStr(R.string.contact_support),
                isForwardArrowVisible = true,
                identifier = ProfileItemIdentifier.SUPPORT
            ),
            ProfilePageDataItem(
                icon = R.drawable.share,
                title = appStr(R.string.share),
                isForwardArrowVisible = true,
                identifier = ProfileItemIdentifier.SHARE_APP
            ),
            ProfilePageDataItem(
                icon = R.drawable.privacy_policy,
                title = appStr(R.string.privacy_policy),
                isForwardArrowVisible = true,
                identifier = ProfileItemIdentifier.PRIVACY_POLICY
            ),
            ProfilePageDataItem(
                icon = R.drawable.terms_of_usage,
                title = appStr(R.string.terms_of_usage),
                isForwardArrowVisible = true,
                identifier = ProfileItemIdentifier.TERMS_OF_USE
            ),
        )
    }
}