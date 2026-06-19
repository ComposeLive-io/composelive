package io.composelive.nodes.foundation.common

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@[Immutable JvmInline Serializable]
public value class FontFamily private constructor(private val ordinal: Int) {

    override fun toString(): String = when (ordinal) {
        Ids.CURSIVE -> "Cursive"
        Ids.DEFAULT -> "Default"
        Ids.MONOSPACE -> "Monospace"
        Ids.SANS_SERIF -> "SandSerif"
        Ids.SERIF -> "Serif"
        else -> throw AssertionError()
    }

    public companion object {
        public val Cursive: FontFamily = FontFamily(Ids.CURSIVE)
        public val Default: FontFamily = FontFamily(Ids.DEFAULT)
        public val Monospace: FontFamily = FontFamily(Ids.MONOSPACE)
        public val SansSerif: FontFamily = FontFamily(Ids.SANS_SERIF)
        public val Serif: FontFamily = FontFamily(Ids.SERIF)
    }

    private object Ids {
        const val CURSIVE: Int = 0
        const val DEFAULT: Int = 1
        const val MONOSPACE: Int = 2
        const val SANS_SERIF: Int = 3
        const val SERIF: Int = 4
    }
}
