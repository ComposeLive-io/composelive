package io.composelive.nodes.foundation.common

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@[Immutable Serializable]
public data class Placeholder(
    val width: TextUnit,
    val height: TextUnit,
    val placeholderVerticalAlign: PlaceholderVerticalAlign,
)
