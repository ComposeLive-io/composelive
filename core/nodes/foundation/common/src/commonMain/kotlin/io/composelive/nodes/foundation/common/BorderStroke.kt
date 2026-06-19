package io.composelive.nodes.foundation.common

import androidx.compose.runtime.Immutable
import app.cash.redwood.ui.Dp
import kotlinx.serialization.Serializable

@[Immutable Serializable]
public data class BorderStroke(
    val width: Dp,
    val color: Color,
)
