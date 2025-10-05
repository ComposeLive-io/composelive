package io.composelive.designsystem.core.compose

import androidx.compose.runtime.Composable
import app.cash.redwood.Modifier
import io.composelive.designsystem.core.api.Color
import io.composelive.designsystem.core.api.DefaultTextStyle
import io.composelive.designsystem.core.api.FontWeight
import io.composelive.designsystem.core.api.TextStyle
import io.composelive.designsystem.core.api.TextUnit

@Composable
public fun Text(
    text: String,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontWeight: FontWeight = FontWeight.Normal,
    color: Color = Color.Unspecified,
    lineThrough: Boolean = false,
    includeFontPadding: Boolean = true,
    style: TextStyle = DefaultTextStyle.copy(
        fontSize = fontSize,
        fontWeight = fontWeight,
        color = color,
        lineThrough = lineThrough,
        includeFontPadding = includeFontPadding,
    ),
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        style = style,
        modifier = modifier,
    )
}
