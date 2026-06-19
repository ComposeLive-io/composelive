package io.composelive.nodes.foundation.common

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@[Immutable JvmInline Serializable]
public value class TextDecoration private constructor(public val mask: Int) {

    override fun toString(): String {
        if (mask == 0) {
            return "TextDecoration.None"
        }

        val values: MutableList<String> = mutableListOf()
        if ((mask and Underline.mask) != 0) {
            values.add("Underline")
        }
        if ((mask and LineThrough.mask) != 0) {
            values.add("LineThrough")
        }
        if ((values.size == 1)) {
            return "TextDecoration.${values[0]}"
        }
        return "TextDecoration[${values.joinToString(separator = ", ")}]"
    }

    public operator fun plus(decoration: TextDecoration): TextDecoration {
        return TextDecoration(this.mask or decoration.mask)
    }

    public companion object {
        public val LineThrough: TextDecoration = TextDecoration(Ids.LINE_THROUGH)
        public val None: TextDecoration = TextDecoration(Ids.NONE)
        public val Underline: TextDecoration = TextDecoration(Ids.UNDERLINE)
    }

    private object Ids {
        const val NONE: Int = 0x0
        const val LINE_THROUGH: Int = 0x1
        const val UNDERLINE: Int = 0x2
    }
}
