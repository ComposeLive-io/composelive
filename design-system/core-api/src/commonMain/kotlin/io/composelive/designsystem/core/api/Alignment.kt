package io.composelive.designsystem.core.api

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@[Immutable JvmInline Serializable]
public value class Alignment(public val ordinal: Int) {

    override fun toString(): String = when (ordinal) {
        Ids.TOP_START -> "TopStart"
        Ids.TOP_CENTER -> "TopCenter"
        Ids.TOP_END -> "TopEnd"
        Ids.CENTER_START -> "CenterStart"
        Ids.CENTER -> "Center"
        Ids.CENTER_END -> "CenterEnd"
        Ids.BOTTOM_START -> "BottomStart"
        Ids.BOTTOM_CENTER -> "BottomCenter"
        Ids.BOTTOM_END -> "BottomEnd"
        else -> throw AssertionError()
    }

    public companion object {
        public val TopStart: Alignment = Alignment(Ids.TOP_START)
        public val TopCenter: Alignment = Alignment(Ids.TOP_CENTER)
        public val TopEnd: Alignment = Alignment(Ids.TOP_END)
        public val CenterStart: Alignment = Alignment(Ids.CENTER_START)
        public val Center: Alignment = Alignment(Ids.CENTER)
        public val CenterEnd: Alignment = Alignment(Ids.CENTER_END)
        public val BottomStart: Alignment = Alignment(Ids.BOTTOM_START)
        public val BottomCenter: Alignment = Alignment(Ids.BOTTOM_CENTER)
        public val BottomEnd: Alignment = Alignment(Ids.BOTTOM_END)

        public val Top: Vertical = Vertical(Ids.TOP)
        public val CenterVertically: Vertical = Vertical(Ids.CENTER_VERTICALLY)
        public val Bottom: Vertical = Vertical(Ids.BOTTOM)

        public val Start: Horizontal = Horizontal(Ids.START)
        public val CenterHorizontally: Horizontal = Horizontal(Ids.CENTER_HORIZONTALLY)
        public val End: Horizontal = Horizontal(Ids.END)
    }

    @[Immutable JvmInline Serializable]
    public value class Vertical(public val ordinal: Int) {

        override fun toString(): String = when (ordinal) {
            Ids.TOP -> "Top"
            Ids.CENTER_VERTICALLY -> "CenterVertically"
            Ids.BOTTOM -> "Bottom"
            else -> throw AssertionError()
        }
    }

    @[Immutable JvmInline Serializable]
    public value class Horizontal(public val ordinal: Int) {

        override fun toString(): String = when (ordinal) {
            Ids.START -> "Start"
            Ids.CENTER_HORIZONTALLY -> "CenterHorizontally"
            Ids.END -> "End"
            else -> throw AssertionError()
        }
    }

    public object Ids {
        public const val TOP_START: Int = 0
        public const val TOP_CENTER: Int = 1
        public const val TOP_END: Int = 2
        public const val CENTER_START: Int = 3
        public const val CENTER: Int = 4
        public const val CENTER_END: Int = 5
        public const val BOTTOM_START: Int = 6
        public const val BOTTOM_CENTER: Int = 7
        public const val BOTTOM_END: Int = 8

        public const val TOP: Int = 0
        public const val CENTER_VERTICALLY: Int = 1
        public const val BOTTOM: Int = 2

        public const val START: Int = 0
        public const val CENTER_HORIZONTALLY: Int = 1
        public const val END: Int = 2
    }
}
