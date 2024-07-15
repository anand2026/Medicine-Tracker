package com.invictus.pillstracker.utils.composeUtils.commonUi

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.invictus.pillstracker.R
import com.invictus.pillstracker.utils.composeUtils.theme.extensions.rippleClickable
import com.invictus.pillstracker.utils.displayUnitConverter.UnitConverter.DP
import com.invictus.pillstracker.utils.displayUnitConverter.UnitConverter.SP

@Composable
fun PageTitleBar(
    pageTitle: String,
    textButton: String = "",
    onTextButtonClick: (() -> Unit)? = null,
    onImageButtonClick: (() -> Unit)? = null,
    backButtonImageId: Int = R.drawable.ic_back_with_bg,
    imageButton: Int?= null,
    backgroundColor: Color = colorResource(id = R.color.inner_page_status_bar),
    titleTextColor: Color = colorResource(id = R.color.progress_page_section_tag),
    onBackClick: (() -> Unit)
) {
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .fillMaxWidth()
            .height(56.DP)
            .background(backgroundColor)
            .padding(16.DP)
    ) {

        Image(
            painter = painterResource(id = backButtonImageId),
            modifier = Modifier
                .size(40.DP)
                .padding(0.DP)
                .clickable {
                    onBackClick.invoke()
                }.semantics { this.contentDescription = "Back" },
            contentDescription = "Back Image"
        )

        Text(
            text = pageTitle,
            style = MaterialTheme.typography.h5.copy(
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 16.SP,
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = titleTextColor,
            modifier = Modifier.weight(1f)
        )


        if (textButton.isNotEmpty()) {
            Text(
                text = textButton,
                style = MaterialTheme.typography.h5.copy(
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.SP,
                ),
                color = colorResource(id = R.color.nav_item_active),
                modifier = Modifier.clickable {
                    onTextButtonClick?.invoke()
                }.semantics { this.contentDescription = "TextButton" }
            )
        } else if(imageButton != null){
            Icon(
                painter = painterResource(id = R.drawable.delete_icon),
                contentDescription = "Delete",
                tint = Color.Red,
                modifier = Modifier.size(24.DP)
                    .rippleClickable { onImageButtonClick?.invoke() }
            )
        } else {
            Box(modifier = Modifier.size(40.DP))
        }
    }
}

