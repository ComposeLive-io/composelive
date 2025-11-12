package io.composelive.designsystem.core.api

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@[Immutable JvmInline Serializable]
public value class Color(public val value: Long) {

    public companion object {
        public val Unspecified: Color = Color(COLOR_UNSPECIFIED)

        public const val COLOR_UNSPECIFIED: Long = Int.MAX_VALUE.toLong()
    }
}
