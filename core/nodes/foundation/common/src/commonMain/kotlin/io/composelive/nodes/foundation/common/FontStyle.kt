package io.composelive.nodes.foundation.common

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@[Immutable JvmInline Serializable]
public value class FontStyle private constructor(private val ordinal: Int) {

    override fun toString(): String = when (ordinal) {
        Ids.NORMAL -> "Normal"
        Ids.ITALIC -> "Italic"
        else -> throw AssertionError()
    }

    public companion object {
        public val Italic: FontStyle = FontStyle(Ids.ITALIC)
        public val Normal: FontStyle = FontStyle(Ids.NORMAL)
    }

    private object Ids {
        const val NORMAL: Int = 0
        const val ITALIC: Int = 1
    }
}
