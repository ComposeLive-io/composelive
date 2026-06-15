package io.composelive.nodes.foundation.common.lazygrid

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@[Immutable JvmInline Serializable]
public value class GridItemSpan(public val value: Int) {
    public companion object {
        public val SINGLE: GridItemSpan = GridItemSpan(1)
    }
}
