package io.composelive.nodes.foundation.common

import androidx.compose.runtime.Immutable
import app.cash.redwood.ui.Dp
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@[Immutable Serializable JvmInline]
public value class Arrangement private constructor(public val space: Dp) {

    public companion object {
        public fun spacedBy(space: Dp): Arrangement = Arrangement(space)
    }
}
