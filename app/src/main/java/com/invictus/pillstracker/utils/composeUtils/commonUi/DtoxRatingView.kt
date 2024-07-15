package com.invictus.pillstracker.utils.composeUtils.commonUi

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.invictus.pillstracker.utils.displayUnitConverter.UnitConverter.DP
import com.invictus.pillstracker.R

@Composable
fun DtoxRatingView(
    modifier: Modifier,
    defaultValue: Int = 0,
    onStarClick: ((Int) -> Unit)
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {

        for (i in 1..5) {

            val starValue = if (i <= defaultValue) {
                R.drawable.ic_rating_star_fill
            } else {
                R.drawable.ic_rating_star_no_fill
            }

            Image(
                painter = painterResource(id = starValue),
                modifier = Modifier
                    .size(40.DP)
                    .clickable {
                        onStarClick.invoke(i)
                    },
                contentDescription = null
            )
        }
    }

}