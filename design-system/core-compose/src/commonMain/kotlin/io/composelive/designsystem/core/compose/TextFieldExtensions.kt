package io.composelive.designsystem.core.compose

import androidx.compose.runtime.Composable
import app.cash.redwood.Modifier
import io.composelive.designsystem.core.api.Color
import io.composelive.designsystem.core.api.FontWeight
import io.composelive.designsystem.core.api.TextFieldValue
import io.composelive.designsystem.core.api.TextStyle
import io.composelive.designsystem.core.api.TextUnit

@Composable
public fun TextField(
    state: TextFieldValue = TextFieldValue(),
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
        state = state,
        hint = hint,
        style = style,
        hintStyle = hintStyle,
        onChange = onChange,
        modifier = modifier,
    )
}
