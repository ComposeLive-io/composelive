package io.composelive.nodes.foundation.common

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@[Immutable JvmInline Serializable]
public value class FontWeight private constructor(public val ordinal: Int) {

    override fun toString(): String = when (ordinal) {
        Ids.THIN -> "Thin"
        Ids.EXTRA_LIGHT -> "ExtraLight"
        Ids.LIGHT -> "Light"
        Ids.NORMAL -> "Normal"
        Ids.MEDIUM -> "Medium"
        Ids.SEMI_BOLD -> "SemiBold"
        Ids.BOLD -> "Bold"
        Ids.EXTRA_BOLD -> "ExtraBold"
        Ids.BLACK -> "Black"
        else -> throw AssertionError()
    }

    public companion object {
        public val Thin: FontWeight = FontWeight(Ids.THIN) // W100
        public val ExtraLight: FontWeight = FontWeight(Ids.EXTRA_LIGHT) // W200
        public val Light: FontWeight = FontWeight(Ids.LIGHT) // W300
        public val Normal: FontWeight = FontWeight(Ids.NORMAL) // W400
        public val Medium: FontWeight = FontWeight(Ids.MEDIUM) // W500
        public val SemiBold: FontWeight = FontWeight(Ids.SEMI_BOLD) // W600
        public val Bold: FontWeight = FontWeight(Ids.BOLD) // W700
        public val ExtraBold: FontWeight = FontWeight(Ids.EXTRA_BOLD) // W800
        public val Black: FontWeight = FontWeight(Ids.BLACK) // W900
    }

    public object Ids {
        public const val THIN: Int = 0
        public const val EXTRA_LIGHT: Int = 1
        public const val LIGHT: Int = 2
        public const val NORMAL: Int = 3
        public const val MEDIUM: Int = 4
        public const val SEMI_BOLD: Int = 5
        public const val BOLD: Int = 6
        public const val EXTRA_BOLD: Int = 7
        public const val BLACK: Int = 8
    }
}
