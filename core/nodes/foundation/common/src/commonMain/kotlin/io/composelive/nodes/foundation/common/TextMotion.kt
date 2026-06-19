package io.composelive.nodes.foundation.common

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@[Immutable JvmInline Serializable]
public value class TextMotion private constructor(private val ordinal: Int) {

    override fun toString(): String = when (ordinal) {
        Ids.ANIMATED -> "Animated"
        Ids.STATIC -> "Static"
        else -> throw AssertionError()
    }

    public companion object {
        public val Animated: TextMotion = TextMotion(Ids.ANIMATED)
        public val Static: TextMotion = TextMotion(Ids.STATIC)
    }

    private object Ids {
        const val ANIMATED: Int = 0
        const val STATIC: Int = 1
    }
}
