package com.invictus.pillstracker.features.addMedicationPage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import com.invictus.pillstracker.R
import com.invictus.pillstracker.utils.composeUtils.commonUi.PillsTrackerBasicTextInput
import com.invictus.pillstracker.utils.composeUtils.theme.FF1892FA
import com.invictus.pillstracker.utils.composeUtils.theme.FF8C8E97
import com.invictus.pillstracker.utils.composeUtils.theme.FFC4CACF
import com.invictus.pillstracker.utils.composeUtils.theme.FFF2F6F7
import com.invictus.pillstracker.utils.composeUtils.theme.FFF4F4F5
import com.invictus.pillstracker.utils.composeUtils.theme.extensions.noRippleClickable
import com.invictus.pillstracker.utils.composeUtils.theme.textColor
import com.invictus.pillstracker.utils.composeUtils.theme.typo16Normal
import com.invictus.pillstracker.utils.composeUtils.theme.typo16SemiBold
import com.invictus.pillstracker.utils.composeUtils.theme.typo20Bold
import com.invictus.pillstracker.utils.composeUtils.theme.typo20SemiBold
import com.invictus.pillstracker.utils.composeUtils.theme.typo30Bold
import com.invictus.pillstracker.utils.displayUnitConverter.UnitConverter.DP

@Composable
fun TopAppBarCustom(back: () -> Unit, close: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.DP)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.back),
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier
                .size(24.DP)
                .noRippleClickable { back() }
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_close_24),
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier
                .size(24.DP)
                .noRippleClickable { close() }
        )
    }
}

@Composable
fun PrimaryButtonCustom(text: String, isActive: Boolean, callback: () -> Unit) {
    Text(
        text = text,
        style = MaterialTheme.typography.typo16SemiBold,
        color = if (isActive) Color.White else FFC4CACF,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .background(if (isActive) FF1892FA else FFF4F4F5, RoundedCornerShape(16.DP))
            .noRippleClickable { callback() }
            .padding(17.DP)
    )
}

@Composable
fun PillsSelectionComp(image: Int, medicineTypeSelected: SnapshotStateList<Int>, index: Int) {

    val (height, width) = when (image) {
        R.drawable.tablet -> Pair(30.DP, 30.DP)
        R.drawable.capsule -> Pair(18.DP, 40.DP)
        R.drawable.bottle -> Pair(30.DP, 35.DP)
        R.drawable.inhaler -> Pair(21.DP, 40.DP)
        else -> Pair(30.DP, 35.DP)
    }

    Box(
        modifier = Modifier.noRippleClickable {
            if (medicineTypeSelected.contains(index)) {
                medicineTypeSelected.remove(index)
            } else {
                medicineTypeSelected.add(index)
            }
        }
    ) {
        Image(
            painter = painterResource(id = R.drawable.ellipse),
            contentDescription = null,
            modifier = Modifier
                .size(64.DP)
                .align(Alignment.Center)
        )
        Image(
            painter = painterResource(id = image),
            contentDescription = null,
            modifier = Modifier
                .height(height)
                .width(width)
                .align(Alignment.Center)
        )
        if (medicineTypeSelected.contains(index)) {
            Image(
                painter = painterResource(id = R.drawable.tick_mark),
                contentDescription = null,
                modifier = Modifier
                    .size(18.DP)
                    .align(Alignment.TopEnd)
            )
        }
    }
}

@Composable
fun AddDoseComp(callback: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.noRippleClickable { callback() }
    ) {
        Image(
            painter = painterResource(id = R.drawable.ellipse),
            contentDescription = null,
            modifier = Modifier
                .size(48.DP)
        )
        Image(
            painter = painterResource(id = R.drawable.baseline_add_24),
            contentDescription = null,
            modifier = Modifier
                .size(24.DP)
        )
    }
}

@Composable
fun DoseComp() {

    val textFieldItem = remember { mutableStateOf(TextFieldValue("")) }

    LaunchedEffect(key1 = textFieldItem.value){

    }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Dose 1",
            style = MaterialTheme.typography.typo30Bold,
            color = MaterialTheme.colors.textColor
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            PillsTrackerBasicTextInput(
                hintText = "00:00",
                enteredText = textFieldItem,
                isMaxWidth = false,
                textAlign = TextAlign.End,
                maxChar = 5,
                keyboardType = KeyboardType.Number,
                callback = {

                    if(textFieldItem.value.text.length == 1 && it.text.length == 2){
                        textFieldItem.value = TextFieldValue(it.text+":", TextRange(it.text.length+1))
                    }else if(textFieldItem.value.text.length == 3 && it.text.length == 2){
                        textFieldItem.value = TextFieldValue(textFieldItem.value.text.substring(0,1), TextRange(1))
                    }else{
                        textFieldItem.value = it
                    }
                }
            )
        }
    }
}

@Composable
fun RemainderComp() {

    val isSwitchChecked = remember { mutableStateOf(false) }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Reminders",
            style = MaterialTheme.typography.typo20SemiBold,
            color = MaterialTheme.colors.textColor
        )

        Switch(
            checked = isSwitchChecked.value,
            onCheckedChange = { isSwitchChecked.value = it },
            colors = SwitchDefaults.colors(
                checkedThumbColor = FF1892FA,
                checkedTrackColor = FFF2F6F7,
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = FFF2F6F7,
            )
        )

    }
}

@Composable
fun SelectedTabletDetailComp(image:Int,text:String,tabletDesc:String,tabletTime:String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(64.DP)
    ) {

        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .width(5.DP)
                .background(FFF2F6F7, RoundedCornerShape(5.DP))
        )

        Image(
            painter = painterResource(id = image),
            contentDescription = null,
            modifier = Modifier
                .size(62.DP)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.typo20Bold,
                color = MaterialTheme.colors.textColor,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.DP))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = tabletDesc,
                    style = MaterialTheme.typography.typo16Normal,
                    color = FF8C8E97
                )

                Text(
                    text = tabletTime,
                    style = MaterialTheme.typography.typo16Normal,
                    color = FF8C8E97
                )
            }

        }

    }
}