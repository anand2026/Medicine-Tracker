package com.invictus.pillstracker.features.homePage.data

enum class PeriodStateIdentifier {
    NORMAL,
    PERIOD,
    OVULATION
}

data class PeriodOvulationData(
    val txt1: String,
    val txt2: String,
    val txt3: String,
    val image: Int,
    val identifier: PeriodStateIdentifier,
)