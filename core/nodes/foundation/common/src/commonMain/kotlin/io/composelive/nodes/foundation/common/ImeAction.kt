package io.composelive.nodes.foundation.common

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@[Immutable JvmInline Serializable]
public value class ImeAction private constructor(private val ordinal: Int) {

    override fun toString(): String = when (ordinal) {
        Ids.DEFAULT -> "Default"
        Ids.DONE -> "Done"
        Ids.GO -> "Go"
        Ids.NEXT -> "Next"
        Ids.NONE -> "None"
        Ids.PREVIOUS -> "Previous"
        Ids.SEARCH -> "Search"
        Ids.SEND -> "Send"
        Ids.UNSPECIFIED -> "Unspecified"
        else -> throw AssertionError()
    }

    public companion object {
        public val Default: ImeAction = ImeAction(Ids.DEFAULT)
        public val Done: ImeAction = ImeAction(Ids.DONE)
        public val Go: ImeAction = ImeAction(Ids.GO)
        public val Next: ImeAction = ImeAction(Ids.NEXT)
        public val None: ImeAction = ImeAction(Ids.NONE)
        public val Previous: ImeAction = ImeAction(Ids.PREVIOUS)
        public val Search: ImeAction = ImeAction(Ids.SEARCH)
        public val Send: ImeAction = ImeAction(Ids.SEND)
        public val Unspecified: ImeAction = ImeAction(Ids.UNSPECIFIED)
    }

    private object Ids {
        const val DEFAULT: Int = 0
        const val DONE: Int = 1
        const val GO: Int = 2
        const val NEXT: Int = 3
        const val NONE: Int = 4
        const val PREVIOUS: Int = 5
        const val SEARCH: Int = 6
        const val SEND: Int = 7
        const val UNSPECIFIED: Int = 8
    }
}
