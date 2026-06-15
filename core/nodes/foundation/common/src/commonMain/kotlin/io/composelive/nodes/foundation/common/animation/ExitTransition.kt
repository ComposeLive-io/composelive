package io.composelive.nodes.foundation.common.animation

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@[JvmInline Serializable Immutable]
public value class ExitTransition(public val value: Int) {

    public operator fun plus(other: ExitTransition): ExitTransition =
        ExitTransition(value or other.value)

    public operator fun contains(transition: ExitTransition): Boolean =
        (value and transition.value) != 0

    public companion object {
        public val fadeOut: ExitTransition = ExitTransition(Ids.FADE_OUT)
        public val scaleOut: ExitTransition = ExitTransition(Ids.SCALE_OUT)
        public val shrinkOut: ExitTransition = ExitTransition(Ids.SHRINK_OUT)
        public val slideOutHorizontally: ExitTransition = ExitTransition(Ids.SLIDE_OUT_HORIZONTALLY)
        public val slideOutVertically: ExitTransition = ExitTransition(Ids.SLIDE_OUT_VERTICALLY)
    }

    public object Ids {
        // @formatter:off
        public const val FADE_OUT: Int               =     0b1
        public const val SCALE_OUT: Int              =    0b10
        public const val SHRINK_OUT: Int             =   0b100
        public const val SLIDE_OUT_HORIZONTALLY: Int =  0b1000
        public const val SLIDE_OUT_VERTICALLY: Int   = 0b10000
        // @formatter:on
    }
}

public fun fadeOut(): ExitTransition = ExitTransition.fadeOut
public fun scaleOut(): ExitTransition = ExitTransition.scaleOut
public fun shrinkOut(): ExitTransition = ExitTransition.shrinkOut
public fun slideOutHorizontally(): ExitTransition = ExitTransition.slideOutHorizontally
public fun slideOutVertically(): ExitTransition = ExitTransition.slideOutVertically
