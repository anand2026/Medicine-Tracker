package com.invictus.pillstracker.utils.composeUtils.commonUi

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.invictus.pillstracker.R
import com.invictus.pillstracker.utils.composeUtils.theme.FFC4CACF
import com.invictus.pillstracker.utils.composeUtils.theme.typo20Bold
import com.invictus.pillstracker.utils.displayUnitConverter.UnitConverter.DP
import com.invictus.pillstracker.utils.displayUnitConverter.UnitConverter.SP

@Composable
fun PillsTrackerTextInput(
    hintText: String,
    enteredText: MutableState<TextFieldValue>,
    keyboardType: KeyboardType = KeyboardType.Text,
    fontSize: TextUnit = 20.SP,
    textAlign: TextAlign = TextAlign.Start,
    maxChar: Int = 0,
    isMoreSpace: Boolean = false,
    boxRoundRadius: Dp = 5.DP,
    borderColor: Color = colorResource(id = R.color.inner_page_status_bar),
    backgroundColor: Color = colorResource(id = R.color.white),
    isMaxWidth: Boolean = true,
) {

    runCatching {

        var modifierBox = if (isMoreSpace) {
            Modifier.heightIn(min = 150.DP)
        } else {
            Modifier.wrapContentHeight()
        }
        modifierBox = if (isMaxWidth) {
            modifierBox.fillMaxWidth()
        } else {
            Modifier.widthIn(1.DP, 100.DP)
        }

        val contAlignment = if (isMoreSpace) Alignment.TopStart else Alignment.CenterStart

        Box(
            contentAlignment = contAlignment,
            modifier = modifierBox
//                .background(backgroundColor, shape = RoundedCornerShape(boxRoundRadius))
//                .border(width = 2.DP, color = borderColor, RoundedCornerShape(boxRoundRadius))
//                .padding(8.DP, 0.DP)


        ) {

            TextField(
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
                value = enteredText.value,
                modifier = modifierBox,
                placeholder = {
                    Text(
                        modifier = if (isMaxWidth) Modifier.fillMaxWidth() else Modifier,
                        textAlign = textAlign,
                        text = hintText,
                        fontSize = fontSize,
                        color = FFC4CACF,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Bold
                    )
                },
                textStyle = MaterialTheme.typography.typo20Bold,
                onValueChange = { newText ->
                    if (maxChar == 0) {
                        enteredText.value = newText
                    } else {
                        if (newText.text.length <= maxChar) enteredText.value = newText
                    }
                },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    backgroundColor = backgroundColor,
                    cursorColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )
        }
    }


}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PillsTrackerBasicTextInput(
    hintText: String,
    enteredText: MutableState<TextFieldValue>,
    keyboardType: KeyboardType = KeyboardType.Text,
    fontSize: TextUnit = 20.SP,
    textAlign: TextAlign = TextAlign.Start,
    maxChar: Int = 0,
    isMoreSpace: Boolean = false,
    boxRoundRadius: Dp = 5.DP,
    borderColor: Color = colorResource(id = R.color.inner_page_status_bar),
    backgroundColor: Color = colorResource(id = R.color.white),
    isMaxWidth: Boolean = true,
    callback: ((TextFieldValue) -> Unit) ?= null
) {

    runCatching {

        var modifierBox = if (isMoreSpace) {
            Modifier.heightIn(min = 150.DP)
        } else {
            Modifier.wrapContentHeight()
        }
        modifierBox = if (isMaxWidth) {
            modifierBox.fillMaxWidth()
        } else {
            Modifier.widthIn(1.DP, 100.DP)
        }

        val contAlignment = if (isMoreSpace) Alignment.TopStart else Alignment.CenterStart

        BasicTextField(
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
            value = enteredText.value,
            modifier = modifierBox,
            textStyle = MaterialTheme.typography.typo20Bold,
            onValueChange = { newText ->
                if (maxChar == 0) {
                    callback?.invoke(newText)?: run { enteredText.value = newText }
                } else {
                    if (newText.text.length <= maxChar){
                        callback?.invoke(newText)?: run { enteredText.value = newText }
                    }
                }
            },
            decorationBox = { innerTextField ->
                if (enteredText.value.text.isEmpty()) {
                    Text(
                        modifier = if (isMaxWidth) Modifier.fillMaxWidth() else Modifier,
                        textAlign = textAlign,
                        text = hintText,
                        fontSize = fontSize,
                        color = FFC4CACF,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Bold
                    )
                }else{
                    TextFieldDefaults.TextFieldDecorationBox(
                        value = enteredText.value.text,
                        visualTransformation = VisualTransformation.None,
                        innerTextField = innerTextField,
                        singleLine = true,
                        enabled = true,
                        interactionSource = remember { MutableInteractionSource() },
                        contentPadding = PaddingValues(0.dp), // this is how you can remove the padding
                    )
                }
            }
        )
    }


}