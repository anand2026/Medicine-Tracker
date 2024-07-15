package com.invictus.pillstracker.utils.netUtil


import com.invictus.pillstracker.utils.DeleteUserParams
import com.invictus.pillstracker.utils.SetStreakParams
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber

class PillsTrackerApiCalls {
    fun callSetStreak(noOfDrinks: Int) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                ApiController.mApiInterface.setStreak(setStreakParams = SetStreakParams(noOfDrinks = noOfDrinks))
                ApiController.mApiInterface.drinkHistory(drinkHistoryParams = DeleteUserParams())
            } catch (e: Exception) {
                Timber.d(e)
            }
        }
    }
}
