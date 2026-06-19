package io.composelive.nodes.foundation.common

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@[Immutable JvmInline Serializable]
public value class Hyphens private constructor(private val ordinal: Int) {

    override fun toString(): String = when (ordinal) {
        Ids.AUTO -> "Auto"
        Ids.NONE -> "None"
        Ids.UNSPECIFIED -> "Unspecified"
        else -> throw AssertionError()
    }

    public companion object {
        public val Auto: Hyphens = Hyphens(Ids.AUTO)
        public val None: Hyphens = Hyphens(Ids.NONE)
        public val Unspecified: Hyphens = Hyphens(Ids.UNSPECIFIED)
    }

    private object Ids {
        const val AUTO: Int = 0
        const val NONE: Int = 1
        const val UNSPECIFIED: Int = 2
    }
}
