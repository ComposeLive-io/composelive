package io.composelive.nodes.foundation.common

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@[Immutable Serializable]
public data class TextLayoutResult (
    val text: String,
    val width: Int,
    val height: Int,
)
