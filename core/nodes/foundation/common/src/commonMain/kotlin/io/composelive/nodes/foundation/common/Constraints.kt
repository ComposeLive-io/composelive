package io.composelive.nodes.foundation.common

import androidx.compose.runtime.Immutable
import app.cash.redwood.ui.Dp
import kotlinx.serialization.Serializable

@[Immutable Serializable]
public data class Constraints(
    public val minWidth: Dp,
    public val maxWidth: Dp,
    public val minHeight: Dp,
    public val maxHeight: Dp,
)
