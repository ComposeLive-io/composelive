package io.composelive.nodes.foundation.compose

import androidx.compose.runtime.Composable
import app.cash.redwood.Modifier
import io.composelive.nodes.foundation.common.Color
import io.composelive.nodes.foundation.common.DefaultTextStyle
import io.composelive.nodes.foundation.common.FontWeight
import io.composelive.nodes.foundation.common.TextStyle
import io.composelive.nodes.foundation.common.TextUnit

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
