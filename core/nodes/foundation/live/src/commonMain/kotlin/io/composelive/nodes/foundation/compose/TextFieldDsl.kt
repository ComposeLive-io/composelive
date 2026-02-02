package io.composelive.nodes.foundation.compose

import androidx.compose.runtime.Composable
import app.cash.redwood.Modifier
import io.composelive.nodes.foundation.common.Color
import io.composelive.nodes.foundation.common.FontWeight
import io.composelive.nodes.foundation.common.TextFieldValue
import io.composelive.nodes.foundation.common.TextStyle
import io.composelive.nodes.foundation.common.TextUnit

@Composable
public fun TextField(
    fieldState: TextFieldValue = TextFieldValue(),
    hint: String = "",
    color: Color = Color.Unspecified,
    hintColor: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontWeight: FontWeight = FontWeight.Normal,
    style: TextStyle = TextStyle(
        color = color,
        fontSize = fontSize,
        fontWeight = fontWeight,
    ),
    hintStyle: TextStyle = style.copy(
        color = hintColor,
    ),
    onChange: ((TextFieldValue) -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    TextField(
        state = fieldState,
        hint = hint,
        style = style,
        hintStyle = hintStyle,
        onChange = onChange,
        modifier = modifier,
    )
}
