package io.composelive.nodes.foundation.common

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@[Immutable JvmInline Serializable]
public value class TileMode private constructor(private val value: Int) {
    override fun toString(): String = when (this) {
        Clamp -> "Clamp"
        Repeated -> "Repeated"
        Mirror -> "Mirror"
        Decal -> "Decal"
        else -> "Unknown"
    }

    public companion object {
        public val Clamp: TileMode = TileMode(0)
        public val Repeated: TileMode = TileMode(1)
        public val Mirror: TileMode = TileMode(2)
        public val Decal: TileMode = TileMode(3)
    }
}
