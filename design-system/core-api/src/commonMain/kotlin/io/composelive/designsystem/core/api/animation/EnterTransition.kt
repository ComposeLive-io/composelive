package io.composelive.designsystem.core.api.animation

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@[JvmInline Serializable Immutable]
public value class EnterTransition(public val value: Int) {

    public operator fun plus(other: EnterTransition): EnterTransition =
        EnterTransition(value or other.value)

    public operator fun contains(transition: EnterTransition): Boolean =
        (value and transition.value) != 0

    public companion object {
        // @formatter:off
        public val fadeIn: EnterTransition              = EnterTransition(0b1)
        public val scaleIn: EnterTransition             = EnterTransition(0b10)
        public val expandIn: EnterTransition            = EnterTransition(0b100)
        public val slideInHorizontally: EnterTransition = EnterTransition(0b1000)
        public val slideInVertically: EnterTransition   = EnterTransition(0b10000)
        // @formatter:on
    }
}

public fun fadeIn(): EnterTransition = EnterTransition.fadeIn
public fun scaleIn(): EnterTransition = EnterTransition.scaleIn
public fun expandIn(): EnterTransition = EnterTransition.expandIn
public fun slideInHorizontally(): EnterTransition = EnterTransition.slideInHorizontally
public fun slideInVertically(): EnterTransition = EnterTransition.slideInVertically
