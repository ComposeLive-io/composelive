package io.composelive.designsystem.core.api

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@[Immutable JvmInline Serializable]
public value class FontWeight private constructor(public val ordinal: Int) {

    override fun toString(): String = when (ordinal) {
        0 -> "Thin"
        1 -> "ExtraLight"
        2 -> "Light"
        3 -> "Normal"
        4 -> "Medium"
        5 -> "SemiBold"
        6 -> "Bold"
        7 -> "ExtraBold"
        8 -> "Black"
        else -> throw AssertionError()
    }

    public companion object {
        public val Thin: FontWeight = FontWeight(0) // W100
        public val ExtraLight: FontWeight = FontWeight(1) // W200
        public val Light: FontWeight = FontWeight(2) // W300
        public val Normal: FontWeight = FontWeight(3) // W400
        public val Medium: FontWeight = FontWeight(4) // W500
        public val SemiBold: FontWeight = FontWeight(5) // W600
        public val Bold: FontWeight = FontWeight(6) // W700
        public val ExtraBold: FontWeight = FontWeight(7) // W800
        public val Black: FontWeight = FontWeight(8) // W900
    }
}
