package io.composelive.nodes.foundation.common

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@[Immutable Serializable]
public sealed class TextFieldLineLimits {
    public companion object {
        public val DefaultTextFieldLineLimits: TextFieldLineLimits = MultiLine()
    }
}

@[Immutable Serializable]
public object SingleLine : TextFieldLineLimits() {
    override fun toString(): String {
        return "TextFieldLineLimits.SingleLine"
    }
}

@[Immutable Serializable]
public data class MultiLine(
    val minHeightInLines: Int = 1,
    val maxHeightInLines: Int = Int.MAX_VALUE
): TextFieldLineLimits() {
    override fun toString(): String =
        "TextFieldLineLimits.MultiLine(minHeightInLines=$minHeightInLines, maxHeightInLines=$maxHeightInLines)"
}

