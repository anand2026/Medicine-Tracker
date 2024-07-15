package com.invictus.pillstracker.features.mainActivityPage

import com.invictus.pillstracker.R
import com.invictus.pillstracker.features.mainActivityPage.data.BottomNavItemDataModel
import com.invictus.pillstracker.features.mainActivityPage.data.BottomNavItemsIdentifiers
import splitties.resources.appStr

class MainActivityRepository {
    fun getBottomNavItemList(): List<BottomNavItemDataModel> {
        return arrayListOf(
            BottomNavItemDataModel(
                navIcon = R.drawable.ic_home3,
                navIdentifier = BottomNavItemsIdentifiers.HOME,
                navName = appStr(R.string.progress)
            ),
            BottomNavItemDataModel(
                navIcon = R.drawable.ic_calendar,
                navIdentifier = BottomNavItemsIdentifiers.CALENDAR,
                navName = ""
            ),
            BottomNavItemDataModel(
                navIcon = R.drawable.ic_profile,
                navIdentifier = BottomNavItemsIdentifiers.PROFILE,
                navName = appStr(R.string.my_tools),
            ),
        )
    }
}