package io.composelive.designsystem.core.api

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@[Immutable Serializable]
public data class TextStyle(
    val fontSize: TextUnit = TextUnit.Unspecified,
    val fontWeight: FontWeight = FontWeight.Normal,
    val color: Color = Color.Unspecified,
    val lineThrough: Boolean = false,
    val includeFontPadding: Boolean = true,
)

public val DefaultTextStyle: TextStyle = TextStyle()
