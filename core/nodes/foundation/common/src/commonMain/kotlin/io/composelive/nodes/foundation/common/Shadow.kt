package io.composelive.nodes.foundation.common

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@[Immutable Serializable]
public data class Shadow(
    val blurRadius: Float = 0.0f,
    val color: Color = Color(0xFF000000),
    val offset: Offset = Offset.Zero,
)
