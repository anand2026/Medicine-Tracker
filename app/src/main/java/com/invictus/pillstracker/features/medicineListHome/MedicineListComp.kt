package com.invictus.pillstracker.features.medicineListHome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.invictus.pillstracker.R
import com.invictus.pillstracker.utils.composeUtils.theme.FF8C8E97
import com.invictus.pillstracker.utils.composeUtils.theme.textColor
import com.invictus.pillstracker.utils.composeUtils.theme.typo16Normal
import com.invictus.pillstracker.utils.composeUtils.theme.typo34Bold
import com.invictus.pillstracker.utils.displayUnitConverter.UnitConverter.DP

@Composable
fun MedicineListTopAppBar(topText:String,selectedDayDropDown:String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.DP, vertical = 10.DP)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = topText,
                style = MaterialTheme.typography.typo16Normal,
                color = FF8C8E97
            )

            Spacer(Modifier.height(12.DP))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
            ) {
                Text(
                    text = selectedDayDropDown,
                    style = MaterialTheme.typography.typo34Bold,
                    color = MaterialTheme.colors.textColor
                )

//                Spacer(Modifier.width(8.DP))
//
//                Icon(
//                    painter = painterResource(id = R.drawable.arrow_down),
//                    contentDescription = null,
//                    tint = FF191D30,
//                    modifier = Modifier.size(18.DP)
//                )
            }
            
        }

        Icon(
            painter = painterResource(id = R.drawable.menu_item),
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier.size(24.DP)
        )
        
    }
}