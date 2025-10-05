package io.composelive.designsystem.core.api.animation

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
        // @formatter:off
        public val fadeOut: ExitTransition              = ExitTransition(0b1)
        public val scaleOut: ExitTransition             = ExitTransition(0b10)
        public val shrinkOut: ExitTransition            = ExitTransition(0b100)
        public val slideOutHorizontally: ExitTransition = ExitTransition(0b1000)
        public val slideOutVertically: ExitTransition   = ExitTransition(0b10000)
        // @formatter:on
    }
}

public fun fadeOut(): ExitTransition = ExitTransition.fadeOut
public fun scaleOut(): ExitTransition = ExitTransition.scaleOut
public fun shrinkOut(): ExitTransition = ExitTransition.shrinkOut
public fun slideOutHorizontally(): ExitTransition = ExitTransition.slideOutHorizontally
public fun slideOutVertically(): ExitTransition = ExitTransition.slideOutVertically
