package io.composelive.designsystem.motion.api

import androidx.compose.runtime.Immutable
import app.cash.redwood.ui.Dp
import kotlinx.serialization.Serializable

@[Serializable Immutable]
public sealed class Dimension {

    @[Serializable Immutable]
    public data object FillToConstraints : Dimension()

    @[Serializable Immutable]
    public data class Exact(public val value: Dp) : Dimension()

    public companion object {
        public val fillToConstraints: FillToConstraints = FillToConstraints

        public fun value(dp: Dp): Exact = Exact(value = dp)
    }
}
