package io.composelive.nodes.foundation.common.animation

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
        public val fadeIn: EnterTransition = EnterTransition(Ids.FADE_IN)
        public val scaleIn: EnterTransition = EnterTransition(Ids.SCALE_IN)
        public val expandIn: EnterTransition = EnterTransition(Ids.EXPAND_IN)
        public val slideInHorizontally: EnterTransition = EnterTransition(Ids.SLIDE_IN_HORIZONTALLY)
        public val slideInVertically: EnterTransition = EnterTransition(Ids.SLIDE_IN_VERTICALLY)
    }

    public object Ids {
        // @formatter:off
        public const val FADE_IN: Int               = 0b1
        public const val SCALE_IN: Int              = 0b10
        public const val EXPAND_IN: Int             = 0b100
        public const val SLIDE_IN_HORIZONTALLY: Int = 0b1000
        public const val SLIDE_IN_VERTICALLY: Int   = 0b10000
        // @formatter:on
    }
}

public fun fadeIn(): EnterTransition = EnterTransition.fadeIn
public fun scaleIn(): EnterTransition = EnterTransition.scaleIn
public fun expandIn(): EnterTransition = EnterTransition.expandIn
public fun slideInHorizontally(): EnterTransition = EnterTransition.slideInHorizontally
public fun slideInVertically(): EnterTransition = EnterTransition.slideInVertically
