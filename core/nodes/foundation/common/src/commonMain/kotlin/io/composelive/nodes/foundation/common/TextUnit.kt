package io.composelive.nodes.foundation.common

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@[JvmInline Immutable Serializable]
public value class TextUnit(public val value: Double) {
    public constructor(value: Float) : this(value.toDouble())

    public companion object {
        public val Unspecified: TextUnit = TextUnit(TEXT_UNIT_UNSPECIFIED)

        public const val TEXT_UNIT_UNSPECIFIED: Double = -1.0
    }
}

public val Float.sp: TextUnit get() = TextUnit(this)
public val Double.sp: TextUnit get() = TextUnit(this)
public val Int.sp: TextUnit get() = TextUnit(this.toDouble())
