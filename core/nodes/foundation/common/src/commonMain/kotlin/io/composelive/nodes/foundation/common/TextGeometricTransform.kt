package io.composelive.nodes.foundation.common

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@[Immutable Serializable]
public data class TextGeometricTransform(
    val scaleX: Float = 1.0f,
    val skewX: Float = 0.0f,
)
