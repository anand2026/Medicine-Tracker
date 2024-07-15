package com.invictus.pillstracker.features.medicineListHome

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.invictus.pillstracker.R
import com.invictus.pillstracker.utils.composeUtils.theme.FF1892FA
import com.invictus.pillstracker.utils.composeUtils.theme.appBackgroundColor
import com.invictus.pillstracker.utils.displayUnitConverter.UnitConverter.DP

@Composable
fun MedicineListHome() {
    Surface(
        color = MaterialTheme.colors.appBackgroundColor,
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            backgroundColor = Color.Transparent,
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {  },
                    backgroundColor = FF1892FA
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = colorResource(id = R.color.white),
                        modifier = Modifier.size(24.DP)
                    )
                }
            },
            topBar = {
                MedicineListTopAppBar("Hey, Sasha!","Thursday")
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(0.DP, 0.DP),
        ) { pad ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(pad)
                    .padding(0.DP, 0.DP)
            ) {

            }
        }
    }
}