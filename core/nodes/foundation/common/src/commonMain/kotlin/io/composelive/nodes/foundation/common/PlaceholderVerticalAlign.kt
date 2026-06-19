package io.composelive.nodes.foundation.common

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@[Immutable JvmInline Serializable]
public value class PlaceholderVerticalAlign private constructor(private val ordinal: Int) {

    override fun toString(): String = when (ordinal) {
        Ids.ABOVE_BASELINE -> "AboveBaseline"
        Ids.BOTTOM -> "Bottom"
        Ids.CENTER -> "Center"
        Ids.TEXT_BOTTOM -> "TextBottom"
        Ids.TEXT_CENTER -> "TExtCenter"
        Ids.TEXT_TOP -> "TextTop"
        Ids.TOP -> "Top"
        else -> throw AssertionError()
    }

    public companion object {
        public val AboveBaseline: PlaceholderVerticalAlign = PlaceholderVerticalAlign(Ids.ABOVE_BASELINE)
        public val Bottom: PlaceholderVerticalAlign = PlaceholderVerticalAlign(Ids.BOTTOM)
        public val Center: PlaceholderVerticalAlign = PlaceholderVerticalAlign(Ids.CENTER)
        public val TextBottom: PlaceholderVerticalAlign = PlaceholderVerticalAlign(Ids.TEXT_BOTTOM)
        public val TextCenter: PlaceholderVerticalAlign = PlaceholderVerticalAlign(Ids.TEXT_CENTER)
        public val TextTop: PlaceholderVerticalAlign = PlaceholderVerticalAlign(Ids.TEXT_TOP)
        public val Top: PlaceholderVerticalAlign = PlaceholderVerticalAlign(Ids.TOP)
    }

    private object Ids {
        const val ABOVE_BASELINE: Int = 0
        const val BOTTOM: Int = 1
        const val CENTER: Int = 2
        const val TEXT_BOTTOM: Int = 3
        const val TEXT_CENTER: Int = 4
        const val TEXT_TOP: Int = 5
        const val TOP: Int = 6
    }
}
