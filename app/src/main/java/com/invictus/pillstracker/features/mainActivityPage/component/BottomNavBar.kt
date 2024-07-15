package com.invictus.pillstracker.features.mainActivityPage.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.airbnb.mvrx.compose.collectAsState
import com.invictus.pillstracker.features.mainActivityPage.MainActivityPageState
import com.invictus.pillstracker.features.mainActivityPage.MainActivityPageViewModel
import com.invictus.pillstracker.features.mainActivityPage.data.BottomNavItemDataModel
import com.invictus.pillstracker.utils.composeUtils.calender.clickable
import com.invictus.pillstracker.utils.composeUtils.theme.FF7583FF
import com.invictus.pillstracker.utils.composeUtils.theme.FF89969F
import com.invictus.pillstracker.utils.composeUtils.theme.FF968AFD
import com.invictus.pillstracker.utils.composeUtils.theme.allCorner100
import com.invictus.pillstracker.utils.composeUtils.theme.homePageBottomColor
import com.invictus.pillstracker.utils.displayUnitConverter.UnitConverter.DP

@Composable
fun BottomNavBar(viewModel: MainActivityPageViewModel) {

    val bottomNavItemModel by viewModel.collectAsState(MainActivityPageState::bottomNavItem)
    val selectedNavItem by viewModel.collectAsState(MainActivityPageState::selectedNavItem)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.homePageBottomColor)
            .padding(20.DP),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        bottomNavItemModel.forEach {
            BottomNavItem(it,it.navIdentifier == selectedNavItem,viewModel)
        }
    }
}

@Composable
fun BottomNavItem(bottomNavItemDataModel: BottomNavItemDataModel, isCurrentItemSelected: Boolean, viewModel: MainActivityPageViewModel) {
    if(isCurrentItemSelected){
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.radialGradient(arrayListOf(FF7583FF, FF968AFD)),
                    MaterialTheme.shapes.allCorner100
                )
                .size(50.DP)
        ) {
            Icon(
                painter = painterResource(id = bottomNavItemDataModel.navIcon),
                contentDescription = "Bottom Nav Item",
                modifier = Modifier.size(35.DP).align(Alignment.Center),
                tint = Color.White
            )
        }
    } else{
        Icon(
            painter = painterResource(id = bottomNavItemDataModel.navIcon),
            contentDescription = "Bottom Nav Not Selected Icon",
            tint = FF89969F,
            modifier = Modifier.size(35.DP).clickable {
                viewModel.setSelectedNavItem(bottomNavItemDataModel.navIdentifier)
            }.semantics { this.contentDescription = "Bottom Nav Select" }
        )
    }
}