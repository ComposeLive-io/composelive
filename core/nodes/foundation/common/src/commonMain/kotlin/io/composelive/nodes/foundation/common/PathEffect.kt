package io.composelive.nodes.foundation.common

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@[Immutable Serializable]
public sealed class PathEffect

@[Immutable Serializable]
public data class CornerPathEffect  (
    public val radius: Float,
): PathEffect()

@[Immutable Serializable]
public data class DashPathEffect  (
    public val intervals: FloatArray,
    public val phase: Float = 0f,
): PathEffect() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as DashPathEffect

        if (phase != other.phase) return false
        if (!intervals.contentEquals(other.intervals)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = phase.hashCode()
        result = 31 * result + intervals.contentHashCode()
        return result
    }
}

@[Immutable Serializable]
public data class ChainPathEffect  (
    public val outer: PathEffect,
    public val inner: PathEffect,
): PathEffect()
