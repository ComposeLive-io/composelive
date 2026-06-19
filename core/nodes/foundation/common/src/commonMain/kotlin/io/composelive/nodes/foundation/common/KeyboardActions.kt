package io.composelive.nodes.foundation.common

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@[Immutable Serializable]
public data class KeyboardActions(
    val onDone: (() -> Unit)? = null,
    val onGo: (() -> Unit)? = null,
    val onNext: (() -> Unit)? = null,
    val onPrevious: (() -> Unit)? = null,
    val onSearch: (() -> Unit)? = null,
    val onSend: (() -> Unit)? = null,
)
