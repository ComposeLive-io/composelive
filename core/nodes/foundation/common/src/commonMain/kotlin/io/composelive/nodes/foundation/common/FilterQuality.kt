package io.composelive.nodes.foundation.common

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@[Immutable JvmInline Serializable]
public value class FilterQuality private constructor(private val ordinal: Int) {

    override fun toString(): String = when (ordinal) {
        Ids.NONE -> "None"
        Ids.LOW -> "Low"
        Ids.MEDIUM -> "Medium"
        Ids.HIGH -> "High"
        else -> throw AssertionError()
    }

    public companion object {
        public val None: FilterQuality = FilterQuality(Ids.NONE)
        public val Low: FilterQuality = FilterQuality(Ids.LOW)
        public val Medium: FilterQuality = FilterQuality(Ids.MEDIUM)
        public val High: FilterQuality = FilterQuality(Ids.HIGH)
    }

    private object Ids {
        const val NONE: Int = 0
        const val LOW: Int = 1
        const val MEDIUM: Int = 2
        const val HIGH: Int = 2
    }
}
