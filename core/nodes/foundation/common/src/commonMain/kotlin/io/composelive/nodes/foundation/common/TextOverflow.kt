package io.composelive.nodes.foundation.common

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@[Immutable JvmInline Serializable]
public value class TextOverflow private constructor(private val ordinal: Int) {

    override fun toString(): String = when (ordinal) {
        Ids.CLIP -> "Clip"
        Ids.ELLIPSIS -> "Ellipsis"
        Ids.START_ELLIPSIS -> "StartEllipsis"
        Ids.MIDDLE_ELLIPSIS -> "MiddleEllipsis"
        Ids.VISIBLE -> "Visible"
        else -> throw AssertionError()
    }

    public companion object {
        public val Clip: TextOverflow = TextOverflow(Ids.CLIP)
        public val Ellipsis: TextOverflow = TextOverflow(Ids.ELLIPSIS)
        public val MiddleEllipsis: TextOverflow = TextOverflow(Ids.MIDDLE_ELLIPSIS)
        public val StartEllipsis: TextOverflow = TextOverflow(Ids.START_ELLIPSIS)
        public val Visible: TextOverflow = TextOverflow(Ids.VISIBLE)
    }

    public object Ids {
        public const val CLIP: Int = 0
        public const val ELLIPSIS: Int = 1
        public const val MIDDLE_ELLIPSIS: Int = 2
        public const val START_ELLIPSIS: Int = 3
        public const val VISIBLE: Int = 4
    }
}
