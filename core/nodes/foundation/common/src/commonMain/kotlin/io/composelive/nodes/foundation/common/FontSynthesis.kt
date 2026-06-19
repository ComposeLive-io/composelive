package io.composelive.nodes.foundation.common

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@[Immutable JvmInline Serializable]
public value class FontSynthesis private constructor(private val ordinal: Int) {

    override fun toString(): String = when (ordinal) {
        Ids.ALL -> "All"
        Ids.NONE -> "None"
        Ids.STYLE -> "Style"
        Ids.WEIGHT -> "Weight"
        else -> throw AssertionError()
    }

    public companion object {
        public val All: FontSynthesis = FontSynthesis(Ids.ALL)
        public val None: FontSynthesis = FontSynthesis(Ids.NONE)
        public val Style: FontSynthesis = FontSynthesis(Ids.STYLE)
        public val Weight: FontSynthesis = FontSynthesis(Ids.WEIGHT)
    }

    private object Ids {
        const val NONE: Int = 0
        const val WEIGHT: Int = 1
        const val STYLE: Int = 2
        const val ALL: Int = 3
    }
}
