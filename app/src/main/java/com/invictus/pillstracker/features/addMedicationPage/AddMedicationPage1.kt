package com.invictus.pillstracker.features.addMedicationPage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.invictus.pillstracker.R
import com.invictus.pillstracker.utils.composeUtils.commonUi.PillsTrackerTextInput
import com.invictus.pillstracker.utils.composeUtils.theme.FF8C8E97
import com.invictus.pillstracker.utils.composeUtils.theme.FFC4CACF
import com.invictus.pillstracker.utils.composeUtils.theme.FFF2F6F7
import com.invictus.pillstracker.utils.composeUtils.theme.appBackgroundColor
import com.invictus.pillstracker.utils.composeUtils.theme.textColor
import com.invictus.pillstracker.utils.composeUtils.theme.typo20Bold
import com.invictus.pillstracker.utils.composeUtils.theme.typo30Bold
import com.invictus.pillstracker.utils.composeUtils.theme.typo34Bold
import com.invictus.pillstracker.utils.displayUnitConverter.UnitConverter.DP

@Preview
@Composable
fun AddMedicationPage1() {

    val selectedPage = "1 of 2"
    val medicineList = remember{ listOf(R.drawable.tablet,R.drawable.capsule,R.drawable.bottle,R.drawable.inhaler) }
    val textFieldItem = remember { mutableStateOf(TextFieldValue("")) }
    val mealTimeList = remember { listOf("Nevermind","Before meals","After meals","With food") }
    val mealTimeSelected = remember { mutableStateListOf<Int>() }
    val medicineTypeSelected = remember { mutableStateListOf<Int>() }


    Surface(
        color = MaterialTheme.colors.appBackgroundColor,
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            backgroundColor = Color.Transparent,
            topBar = {
                TopAppBarCustom({},{})
            },
            bottomBar = {
                PrimaryButtonCustom("Next",true,){}
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(24.DP, 0.DP)
                .padding(bottom = 40.DP)
            ,
        ) { pad ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(pad)
            ) {
                item{
                    Spacer(Modifier.height(12.DP))

                    Text(
                        text = selectedPage,
                        style = MaterialTheme.typography.typo20Bold,
                        color = FF8C8E97
                    )

                    Spacer(Modifier.height(12.DP))

                    Text(
                        text = "Add Medication",
                        style = MaterialTheme.typography.typo34Bold,
                        color = MaterialTheme.colors.textColor
                    )

                    Spacer(Modifier.height(40.DP))
                }

                item{
                    LazyRow(
                        modifier = Modifier.fillMaxWidth()
                    ){
                        itemsIndexed(medicineList){ index,item->

                            PillsSelectionComp(index,medicineTypeSelected,index)

                            if(index != medicineList.size-1) {
                                Spacer(Modifier.width(24.DP))
                            }
                        }
                    }
                }

                item{
                    Spacer(Modifier.height(45.DP))

                    PillsTrackerTextInput(hintText = "Name", enteredText = textFieldItem)
                }

                item{
                    Spacer(Modifier.height(45.DP))

                    PillsTrackerTextInput(hintText = "Single dose, e.g. 1 tablet", enteredText = textFieldItem)
                }

                item{
                    Spacer(Modifier.height(45.DP))

                    LazyRow(
                        modifier = Modifier.fillMaxWidth()
                    ){
                        itemsIndexed(mealTimeList){ index,item->
                            val isSelected = mealTimeSelected.contains(index)
                            Text(
                                text = item,
                                style = MaterialTheme.typography.typo30Bold,
                                color = if(isSelected) MaterialTheme.colors.textColor else FFC4CACF,
                                modifier = Modifier
                                    .background(if (isSelected) FFF2F6F7 else Color.Transparent, RoundedCornerShape(100.DP))
                                    .padding(
                                        if (isSelected) 20.DP else 0.DP,
                                        if (isSelected) 8.DP else 0.DP,
                                    )
                            )

                            if(index != mealTimeList.size-1) {
                                Spacer(Modifier.width(24.DP))
                            }
                        }
                    }
                }

            }
        }
    }
}