package io.composelive.nodes.foundation.common

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@[Serializable]
public sealed interface AsyncImageState {

    @[Immutable Serializable]
    public object Empty: AsyncImageState

    @[Immutable Serializable]
    public object Loading: AsyncImageState

    @[Immutable Serializable]
    public object Success: AsyncImageState

    @[Immutable Serializable]
    public data class Error(val message: String?): AsyncImageState
}
