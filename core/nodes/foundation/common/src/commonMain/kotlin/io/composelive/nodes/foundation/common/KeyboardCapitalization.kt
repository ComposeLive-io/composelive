package io.composelive.nodes.foundation.common

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@[Immutable JvmInline Serializable]
public value class KeyboardCapitalization private constructor(private val ordinal: Int) {

    override fun toString(): String = when (ordinal) {
        Ids.CHARACTERS -> "Characters"
        Ids.NONE -> "None"
        Ids.SENTENCES -> "Sentences"
        Ids.WORDS -> "Words"
        Ids.UNSPECIFIED -> "Unspecified"
        else -> throw AssertionError()
    }

    public companion object {
        public val Characters: KeyboardCapitalization = KeyboardCapitalization(Ids.CHARACTERS)
        public val None: KeyboardCapitalization = KeyboardCapitalization(Ids.NONE)
        public val Sentences: KeyboardCapitalization = KeyboardCapitalization(Ids.SENTENCES)
        public val Words: KeyboardCapitalization = KeyboardCapitalization(Ids.WORDS)
        public val Unspecified: KeyboardCapitalization = KeyboardCapitalization(Ids.NONE)

    }

    private object Ids {
        const val CHARACTERS: Int = 0
        const val NONE: Int = 1
        const val SENTENCES: Int = 2
        const val WORDS: Int = 3
        const val UNSPECIFIED: Int = 4
    }
}
