package io.composelive.nodes.foundation.common

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@[Immutable JvmInline Serializable]
public value class TextDirection private constructor(private val ordinal: Int) {

    override fun toString(): String = when (ordinal) {
        Ids.CONTENT -> "Content"
        Ids.CONTENT_OR_LTR -> "ContentOrLtr"
        Ids.CONTENT_OR_RTL -> "ContentOrRtl"
        Ids.LTR -> "Ltr"
        Ids.RTL -> "Rtl"
        Ids.UNSPECIFIED -> "Unspecified"
        else -> throw AssertionError()
    }

    public companion object {
        public val Content: TextDirection = TextDirection(Ids.CONTENT)
        public val ContentOrLtr: TextDirection = TextDirection(Ids.CONTENT_OR_LTR)
        public val ContentOrRtl: TextDirection = TextDirection(Ids.CONTENT_OR_RTL)
        public val Ltr: TextDirection = TextDirection(Ids.LTR)
        public val Rtl: TextDirection = TextDirection(Ids.RTL)
        public val Unspecified: TextDirection = TextDirection(Ids.UNSPECIFIED)
    }

    private object Ids {
        const val CONTENT: Int = 0
        const val CONTENT_OR_LTR: Int = 1
        const val CONTENT_OR_RTL: Int = 2
        const val LTR: Int = 3
        const val RTL: Int = 4
        const val UNSPECIFIED: Int = 5
    }
}
