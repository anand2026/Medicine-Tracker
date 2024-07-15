package com.invictus.pillstracker.utils.composeUtils.commonUi

import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import com.invictus.pillstracker.R

@Composable
fun HtmlText(modifier: Modifier = Modifier, htmlText: String, colorRes: Int = R.color.white, textSize: Float = 12f) {
    AndroidView(
        modifier = modifier,
        factory = { context -> TextView(context) },
        update = {
            it.text = HtmlCompat.fromHtml(htmlText, HtmlCompat.FROM_HTML_MODE_COMPACT)
            it.textSize = textSize
            it.setTextColor(it.context.resources.getColor(colorRes))
        }
    )
}