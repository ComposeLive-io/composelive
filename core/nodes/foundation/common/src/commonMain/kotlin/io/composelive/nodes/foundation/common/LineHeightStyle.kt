package io.composelive.nodes.foundation.common

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@[Immutable Serializable]
public data class LineHeightStyle(
    val alignment: Alignment = Alignment.Proportional,
    val trim: Trim = Trim.Both,
    val mode: Mode = Mode.Fixed,
) {
    override fun toString(): String =
        "LineHeightStyle(alignment=$alignment, trim=$trim,mode=$mode)"

    @[JvmInline Immutable Serializable]
    public value class Alignment(public val topRatio: Float)  {
        override fun toString(): String = when (topRatio) {
            Ratios.BOTTOM -> "LineHeightStyle.Alignment.Bottom"
            Ratios.CENTER -> "LineHeightStyle.Alignment.Center"
            Ratios.PROPORTIONAL -> "LineHeightStyle.Alignment.Proportional"
            Ratios.TOP -> "LineHeightStyle.Alignment.Top"
            else -> "LineHeightStyle.Alignment(topPercentage = $topRatio)"
        }

        public companion object {
            public val Bottom: Alignment = Alignment(Ratios.BOTTOM)
            public val Center: Alignment = Alignment(Ratios.CENTER)
            public val Proportional: Alignment = Alignment(Ratios.PROPORTIONAL)
            public val TOP: Alignment = Alignment(Ratios.TOP)
        }

        public object Ratios {
            public const val BOTTOM: Float = 0f
            public const val CENTER: Float = 0.5f
            public const val PROPORTIONAL: Float = -1f
            public const val TOP: Float = 1f
        }
    }

    @[Immutable JvmInline Serializable]
    public value class Mode private constructor(private val ordinal: Int) {

        override fun toString(): String = when (ordinal) {
            Ids.FIXED -> "LineHeightStyle.Mode.Fixed"
            Ids.MINIMUM -> "LineHeightStyle.Mode.Minimum"
            else -> throw AssertionError()
        }

        public companion object {
            public val Fixed: Mode = Mode(Ids.FIXED)
            public val Minimum: Mode = Mode(Ids.MINIMUM)
        }

        private object Ids {
            const val FIXED: Int = 0
            const val MINIMUM: Int = 1
        }
    }

    @[Immutable JvmInline Serializable]
    public value class Trim private constructor(private val ordinal: Int) {

        override fun toString(): String = when (ordinal) {
            Ids.NONE -> "LineHeightStyle.Trim.None"
            Ids.FIRST_LINE_TOP -> "LineHeightStyle.Trim.FirstLineTop"
            Ids.LAST_LINE_BOTTOM -> "LineHeightStyle.Trim.LastLineBottom"
            Ids.BOTH -> "LineHeightStyle.Trim.Both"
            else -> throw AssertionError()
        }

        public companion object {
            public val None: Trim = Trim(Ids.NONE)
            public val FirstLineTop: Trim = Trim(Ids.FIRST_LINE_TOP)
            public val LastLineBottom: Trim = Trim(Ids.LAST_LINE_BOTTOM)
            public val Both: Trim = Trim(Ids.BOTH)
        }

        private object Ids {
            const val NONE: Int = 0
            const val FIRST_LINE_TOP: Int = 1
            const val LAST_LINE_BOTTOM: Int = 2
            const val BOTH: Int = 3
        }
    }
}
