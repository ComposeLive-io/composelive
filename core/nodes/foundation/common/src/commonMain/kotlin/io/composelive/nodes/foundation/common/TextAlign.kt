package io.composelive.nodes.foundation.common

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@[Immutable JvmInline Serializable]
public value class TextAlign private constructor(private val ordinal: Int) {

    override fun toString(): String = when (ordinal) {
        Ids.CENTER -> "Center"
        Ids.END -> "End"
        Ids.JUSTIFY -> "Justify"
        Ids.LEFT -> "Left"
        Ids.RIGHT -> "Right"
        Ids.START -> "Start"
        Ids.UNSPECIFIED -> "Unspecified"
        else -> throw AssertionError()
    }

    public companion object {
        public val Center: TextAlign = TextAlign(Ids.CENTER)
        public val End: TextAlign = TextAlign(Ids.END)
        public val Justify: TextAlign = TextAlign(Ids.JUSTIFY)
        public val Left: TextAlign = TextAlign(Ids.LEFT)
        public val Right: TextAlign = TextAlign(Ids.RIGHT)
        public val Start: TextAlign = TextAlign(Ids.START)
        public val Unspecified: TextAlign = TextAlign(Ids.UNSPECIFIED)
    }

    private object Ids {
        const val CENTER: Int = 0
        const val END: Int = 1
        const val JUSTIFY: Int = 2
        const val LEFT: Int = 3
        const val RIGHT: Int = 4
        const val START: Int = 5
        const val UNSPECIFIED: Int = 6
    }
}
