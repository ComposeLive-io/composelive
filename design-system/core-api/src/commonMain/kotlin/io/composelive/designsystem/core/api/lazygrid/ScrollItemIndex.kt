package io.composelive.designsystem.core.api.lazygrid

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

/**
 * @param id Should only be used to trigger recompositions.
 */
@[Immutable Serializable]
public data class ScrollItemIndex(
    public val id: Int,
    public val index: Int,
    /** True to smoothly scroll to the new position. */
    public val animated: Boolean = false,
)
