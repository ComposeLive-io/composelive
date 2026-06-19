package io.composelive.nodes.foundation.common

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@[Immutable JvmInline Serializable]
public value class BaselineShift public constructor(public val multiplier: Float) {

    override fun toString(): String = when (multiplier) {
        Multipliers.NONE -> "None"
        Multipliers.SUBSCRIPT -> "Subscript"
        Multipliers.SUPERSCRIPT -> "Superscript"
        Multipliers.UNSPECIFIED -> "Unspecified"
        else -> multiplier.toString()
    }

    public companion object {
        public val None: BaselineShift = BaselineShift(Multipliers.NONE)
        public val Subscript: BaselineShift = BaselineShift(Multipliers.SUBSCRIPT)
        public val Superscript: BaselineShift = BaselineShift(Multipliers.SUPERSCRIPT)
        public val Unspecified: BaselineShift = BaselineShift(Multipliers.UNSPECIFIED)
    }

    public object Multipliers {
        public const val NONE: Float = 0.0f
        public const val SUBSCRIPT: Float = -0.5f
        public const val SUPERSCRIPT: Float = 0.5f
        public const val UNSPECIFIED: Float = Float.NaN
    }
}
