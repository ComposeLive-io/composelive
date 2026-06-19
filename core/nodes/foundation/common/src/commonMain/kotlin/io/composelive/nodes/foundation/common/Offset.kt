package io.composelive.nodes.foundation.common

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@[Immutable Serializable]
public data class Offset(
    public val x: Float,
    public val y: Float,
) {

    public companion object {
        public val Zero: Offset = Offset(.0f, .0f)
        public val Infinite: Offset = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
        public val Unspecified: Offset = Offset(Float.NaN, Float.NaN)
    }
}
