package io.composelive.nodes.foundation.compose

import androidx.compose.runtime.Composable
import app.cash.redwood.Modifier
import io.composelive.nodes.foundation.common.Color
import io.composelive.nodes.foundation.common.DefaultTextStyle
import io.composelive.nodes.foundation.common.FontWeight
import io.composelive.nodes.foundation.common.KeyboardActions
import io.composelive.nodes.foundation.common.KeyboardOptions
import io.composelive.nodes.foundation.common.TextFieldLineLimits
import io.composelive.nodes.foundation.common.TextFieldValue
import io.composelive.nodes.foundation.common.TextStyle
import io.composelive.nodes.foundation.common.TextUnit

@Composable
public fun TextField(
    fieldState: TextFieldValue = TextFieldValue(),
    modifier: Modifier = Modifier,
    onChange: ((TextFieldValue) -> Unit)? = null,
    textColor: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontWeight: FontWeight = FontWeight.Normal,
    textStyle: TextStyle = DefaultTextStyle,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    keyboardActions: KeyboardActions = KeyboardActions(),
    hint: String = "",
    hintColor: Color = Color.Unspecified,
    hintStyle: TextStyle? = null,
    lineLimits: TextFieldLineLimits = TextFieldLineLimits.DefaultTextFieldLineLimits,
    decorationBox:  @Composable () -> Unit = {},
) {
    val mergedTextStyle = textStyle.merge(
        color = textColor,
        fontSize = fontSize,
        fontWeight = fontWeight,
    )
    TextField(
        state = fieldState,
        modifier = modifier,
        onChange = onChange,
        textStyle = mergedTextStyle,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        lineLimits = lineLimits,
        decorationBox = {
            decorationBox()
            if (hint.isNotEmpty() && fieldState.text.isEmpty()) {
                Text(
                    text = hint,
                    style = hintStyle?.merge(color = hintColor) ?: mergedTextStyle.merge(color = hintColor),
                )
            }
        },
    )
}
