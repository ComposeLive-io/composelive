package io.composelive.nodes.foundation.common

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@[Immutable JvmInline Serializable]
public value class LineBreak private constructor(private val ordinal: Int) {

    override fun toString(): String = when (ordinal) {
        Ids.HEADING -> "Heading"
        Ids.PARAGRAPH -> "Paragraph"
        Ids.SIMPLE -> "Simple"
        Ids.UNSPECIFIED -> "Unspecified"
        else -> throw AssertionError()
    }

    public companion object {
        public val Heading: LineBreak = LineBreak(Ids.HEADING)
        public val Paragraph: LineBreak = LineBreak(Ids.PARAGRAPH)
        public val Simple: LineBreak = LineBreak(Ids.SIMPLE)
        public val Unspecified: LineBreak = LineBreak(Ids.UNSPECIFIED)
    }

    private object Ids {
        const val HEADING: Int = 0
        const val PARAGRAPH: Int = 1
        const val SIMPLE: Int = 2
        const val UNSPECIFIED: Int = 3
    }
}
