package com.invictus.pillstracker.utils

import splitties.init.appCtx

enum class SignInPageUrls(val value : String) {
    FEEDBACK("https://forms.gle/ve9eJyQFLd7Av1MM7"),
    TERMS("https://pillstracker.blogspot.com/p/pillstracker-terms-and-conditions.html"),
    POLICY("https://pillstracker.blogspot.com/p/pillstracker-privacy-policy.html"),
}

enum class SettingPageUrls(val value : String) {
    PILLS_TRACKER_ONE_LINK("https://pillstracker.onelink.me/20vl/pillstrackerref"),
    PILLS_TRACKER_PLAY_STORE("https://play.google.com/store/apps/details?id=${appCtx.packageName}"),
}