package com.invictus.pillstracker.features.homePage.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import com.invictus.pillstracker.R
import com.invictus.pillstracker.features.homePage.data.PeriodOvulationData
import com.invictus.pillstracker.utils.composeUtils.calender.clickable
import com.invictus.pillstracker.utils.composeUtils.theme.FF9B78F3
import com.invictus.pillstracker.utils.composeUtils.theme.FFEEE8FF
import com.invictus.pillstracker.utils.composeUtils.theme.FFFFE299
import com.invictus.pillstracker.utils.composeUtils.theme.allCorner10
import com.invictus.pillstracker.utils.composeUtils.theme.allCorner100
import com.invictus.pillstracker.utils.composeUtils.theme.allCorner22
import com.invictus.pillstracker.utils.composeUtils.theme.notificationItemBackground
import com.invictus.pillstracker.utils.composeUtils.theme.primaryColor
import com.invictus.pillstracker.utils.composeUtils.theme.secondaryColor
import com.invictus.pillstracker.utils.composeUtils.theme.textColor
import com.invictus.pillstracker.utils.composeUtils.theme.typo12SemiBold
import com.invictus.pillstracker.utils.composeUtils.theme.typo13Normal
import com.invictus.pillstracker.utils.composeUtils.theme.typo14Bold
import com.invictus.pillstracker.utils.composeUtils.theme.typo14SemiBold
import com.invictus.pillstracker.utils.composeUtils.theme.typo18Bold
import com.invictus.pillstracker.utils.composeUtils.theme.typo22SemiBold
import com.invictus.pillstracker.utils.composeUtils.theme.typo40ExtraBold
import com.invictus.pillstracker.utils.composeUtils.theme.typo7SemiBold
import com.invictus.pillstracker.utils.displayUnitConverter.UnitConverter.DP
import com.invictus.pillstracker.utils.displayUnitConverter.UnitConverter.SP

@Composable
fun AppBarNameComponent(name: String) {
    Column {
        Text(
            text = stringResource(R.string.welcome),
            style = MaterialTheme.typography.typo14SemiBold,
            textAlign = TextAlign.Start,
            color = FF9B78F3
        )
        Text(
            text = name,
            style = MaterialTheme.typography.typo18Bold,
            color = MaterialTheme.colors.textColor
        )
    }
}

@Composable
fun AppBarNotificationComponent(count: Int) {
    Box(
        modifier = Modifier
            .background(
                MaterialTheme.colors.notificationItemBackground,
                MaterialTheme.shapes.allCorner10
            )
            .padding(10.DP)
    ) {
        Image(
            painter = painterResource(id = R.drawable.notification_icon),
            contentDescription = "notification Icon",
            modifier = Modifier
                .size(30.DP)
        )
        Box(
            modifier = Modifier
                .background(
                    secondaryColor,
                    MaterialTheme.shapes.allCorner100
                )
                .padding(vertical = 2.DP, horizontal = 4.DP)
        ) {
            Text(
                text = count.toString(),
                style = MaterialTheme.typography.typo7SemiBold,
                color = MaterialTheme.colors.notificationItemBackground
            )
        }
    }
}

@Composable
fun CalendarDateShowHomeComp(text: String, modifier: Modifier = Modifier, callback: () -> Unit) {
    Row(
        modifier = Modifier
            .background(FFEEE8FF, MaterialTheme.shapes.allCorner22)
            .clickable { callback() }.semantics { this.contentDescription = "Date Show Comp" }
            .then(modifier)
            .padding(6.DP),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.akar_icons_calendar),
            contentDescription = "Calendar Icon",
            modifier = Modifier.size(16.DP),
            tint = MaterialTheme.colors.primary
        )

        Spacer(Modifier.width(10.DP))

        Text(
            text = text,
            style = MaterialTheme.typography.typo13Normal,
            color = MaterialTheme.colors.primary
        )

    }
}

@Composable
fun MoodSelectionComponent(text: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.add_symobol_for_emoji),
            contentDescription = "Mood Swing Selector",
            modifier = Modifier.size(84.DP),
        )

        Spacer(Modifier.height(7.DP))

        Text(
            text = text,
            style = MaterialTheme.typography.typo12SemiBold,
            color = MaterialTheme.colors.textColor
        )
    }
}

@Composable
fun EmojiSelectionComponent(text: String, image: Int?, color: Color, isSelected: Boolean, callback: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .background(
                    color,
                    MaterialTheme.shapes.allCorner10
                )
                .border(if (isSelected) 3.DP else 0.DP, if (isSelected) primaryColor else Color.Transparent, MaterialTheme.shapes.allCorner10)
                .size(84.DP)
                .clickable { callback() }.semantics { this.contentDescription = "Emoji Selection" }
        ) {
            Image(
                painter = painterResource(id = image ?: R.drawable.add_symobol_for_emoji),
                contentDescription = "Add Symbol",
                modifier = Modifier
                    .size(55.DP)
                    .align(Alignment.Center),
            )
        }

        Spacer(Modifier.height(7.DP))

        Text(
            text = text,
            style = MaterialTheme.typography.typo12SemiBold,
            color = MaterialTheme.colors.textColor
        )
    }
}

@Composable
fun HeartWithOvulationChances(periodOvulationData: PeriodOvulationData, modifier: Modifier = Modifier) {

    Box(
        modifier = Modifier
            .then(modifier)
    ) {
        Image(
            painter = painterResource(periodOvulationData.image),
            contentDescription = "Heart With Oval",
            modifier = Modifier
                .width(260.DP)
                .height(234.DP)
        )

        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = periodOvulationData.txt1,
                style = MaterialTheme.typography.typo22SemiBold,
                color = Color.White,
                textAlign = TextAlign.Center,
            )
            Text(
                text = periodOvulationData.txt2,
                style = MaterialTheme.typography.typo22SemiBold,
                color = Color.White,
                fontWeight = MaterialTheme.typography.typo40ExtraBold.fontWeight,
                fontSize = MaterialTheme.typography.typo40ExtraBold.fontSize,
                textAlign = TextAlign.Center,
            )

            Spacer(Modifier.height(10.DP))

            Text(
                text = periodOvulationData.txt3,
                style = MaterialTheme.typography.typo22SemiBold.copy(lineHeight = 10.SP),
                fontWeight = MaterialTheme.typography.typo14Bold.fontWeight,
                fontSize = MaterialTheme.typography.typo14Bold.fontSize,
                color = FFFFE299,
                textAlign = TextAlign.Center,
            )
        }

    }

}

@Composable
fun HomePageOvulationComp(
    periodOvulationData: PeriodOvulationData,
    calenderText: String,
    calenderCallback: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 0.DP, horizontal = 12.DP)
    ) {

        HeartWithOvulationChances(periodOvulationData, Modifier.align(Alignment.Center))

        Image(
            painter = painterResource(id = R.drawable.pink_heart),
            contentDescription = "Pink Heart",
            modifier = Modifier
                .size(22.DP)
                .align(Alignment.TopStart)
                .rotate(-30f)
        )
        Image(
            painter = painterResource(id = R.drawable.pink_heart),
            contentDescription = "Pink Heart",
            modifier = Modifier
                .size(22.DP)
                .align(Alignment.BottomEnd)
                .rotate(30f)
        )

        CalendarDateShowHomeComp(calenderText, Modifier.align(Alignment.BottomStart), calenderCallback)
    }
}

