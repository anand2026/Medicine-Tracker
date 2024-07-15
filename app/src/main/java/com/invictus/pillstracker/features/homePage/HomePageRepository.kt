package com.invictus.pillstracker.features.homePage

import androidx.compose.ui.graphics.Color
import com.invictus.pillstracker.R
import com.invictus.pillstracker.features.homePage.data.PeriodOvulationData
import com.invictus.pillstracker.features.homePage.data.PeriodStateIdentifier
import com.invictus.pillstracker.utils.composeUtils.theme.FF849EFB
import com.invictus.pillstracker.utils.composeUtils.theme.FF94D9C0
import com.invictus.pillstracker.utils.composeUtils.theme.FFAFCBF5
import com.invictus.pillstracker.utils.composeUtils.theme.FFAFE9E5
import com.invictus.pillstracker.utils.composeUtils.theme.FFE6C7FF
import com.invictus.pillstracker.utils.composeUtils.theme.FFF5D2DF
import com.invictus.pillstracker.utils.composeUtils.theme.FFFFC7C7
import com.invictus.pillstracker.utils.composeUtils.theme.FFFFE2C7
import com.invictus.pillstracker.utils.composeUtils.theme.FFFFE3AD
import splitties.resources.appStr

class HomePageRepository {

    fun getMoodEmojiData():List<Pair<String,Pair<Int, Color>>>{
        return listOf(
            Pair(appStr(R.string.sad),Pair(R.drawable.mood_sad, FFF5D2DF)),
            Pair(appStr(R.string.badmood),Pair(R.drawable.mood_badmood, FFAFE9E5)),
            Pair(appStr(R.string.happy),Pair(R.drawable.mood_happy, FFFFE2C7)),
            Pair(appStr(R.string.fun_mood),Pair(R.drawable.fun_mood, FFFFE3AD)),
            Pair(appStr(R.string.frustated),Pair(R.drawable.frustated_mood, FFAFCBF5)),
            Pair(appStr(R.string.shocked),Pair(R.drawable.shocked, FFE6C7FF)),
            Pair(appStr(R.string.calm),Pair(R.drawable.calmed_mood, FFFFC7C7)),
            Pair(appStr(R.string.nice),Pair(R.drawable.nice, FF94D9C0)),
            Pair(appStr(R.string.relax),Pair(R.drawable.relax_mood, FF849EFB)),
        )
    }

    fun getPeriodAndOvulationData(periodStateIdentifier: PeriodStateIdentifier, str: String): PeriodOvulationData {
        return when(periodStateIdentifier){
            PeriodStateIdentifier.NORMAL -> PeriodOvulationData(
                "${appStr(R.string.cycle_day)}: ",
                "${appStr(R.string.day)} $str",
                "",
                R.drawable.pink_heart,
                PeriodStateIdentifier.NORMAL
            )
            PeriodStateIdentifier.PERIOD -> PeriodOvulationData(
                "${appStr(R.string.period)}: ",
                "${appStr(R.string.day)} $str",
                "",
                R.drawable.dark_red_heart,
                PeriodStateIdentifier.PERIOD
            )
            PeriodStateIdentifier.OVULATION -> PeriodOvulationData(
                appStr(R.string.prediction),
                if(str.isNotEmpty()) appStr(R.string.ovulation) else appStr(R.string.fertility_day),
                if(str.isNotEmpty()) appStr(R.string.high_chances_of_getting_pregnant) else appStr(R.string.medium_chances_of_getting_pregnant),
                R.drawable.blue_heart,
                PeriodStateIdentifier.OVULATION
            )
            else -> PeriodOvulationData(
                "Day 10",
                "12.4%",
                "12.4%",
                R.drawable.pink_heart,
                PeriodStateIdentifier.NORMAL
            )
        }
    }

}