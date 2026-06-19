package io.composelive.nodes.foundation.common

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@[Serializable Immutable]
public data class LayoutMetadata(
    public val name: String,
    public val moduleId: String,
    public val screenName: String,
    public val screenId: String,
) {
    val screenKey: String = "$screenName-$screenId"
}
