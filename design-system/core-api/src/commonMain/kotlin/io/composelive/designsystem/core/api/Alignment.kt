package io.composelive.designsystem.core.api

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@[Immutable JvmInline Serializable]
public value class Alignment(public val ordinal: Int) {

    override fun toString(): String = when (ordinal) {
        0 -> "TopStart"
        1 -> "TopCenter"
        2 -> "TopEnd"
        3 -> "CenterStart"
        4 -> "Center"
        5 -> "CenterEnd"
        6 -> "BottomStart"
        7 -> "BottomCenter"
        8 -> "BottomEnd"
        else -> throw AssertionError()
    }

    public companion object {
        public val TopStart: Alignment = Alignment(0)
        public val TopCenter: Alignment = Alignment(1)
        public val TopEnd: Alignment = Alignment(2)
        public val CenterStart: Alignment = Alignment(3)
        public val Center: Alignment = Alignment(4)
        public val CenterEnd: Alignment = Alignment(5)
        public val BottomStart: Alignment = Alignment(6)
        public val BottomCenter: Alignment = Alignment(7)
        public val BottomEnd: Alignment = Alignment(8)

        public val Top: Vertical = Vertical(0)
        public val CenterVertically: Vertical = Vertical(1)
        public val Bottom: Vertical = Vertical(2)

        public val Start: Horizontal = Horizontal(0)
        public val CenterHorizontally: Horizontal = Horizontal(1)
        public val End: Horizontal = Horizontal(2)
    }

    @[Immutable JvmInline Serializable]
    public value class Vertical(public val ordinal: Int) {

        override fun toString(): String = when (ordinal) {
            0 -> "Top"
            1 -> "CenterVertically"
            2 -> "Bottom"
            else -> throw AssertionError()
        }
    }

    @[Immutable JvmInline Serializable]
    public value class Horizontal(public val ordinal: Int) {

        override fun toString(): String = when (ordinal) {
            0 -> "Start"
            1 -> "CenterHorizontally"
            2 -> "End"
            else -> throw AssertionError()
        }
    }
}