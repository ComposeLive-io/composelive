package io.composelive.nodes.foundation.common

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@[Immutable Serializable]
public data class ButtonColors(
    val containerColor: Color = Color.Unspecified,
    val contentColor: Color = Color.Unspecified,
    val disabledContainerColor: Color = Color.Unspecified,
    val disabledContentColor: Color = Color.Unspecified,
)
