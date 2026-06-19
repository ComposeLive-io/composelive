package io.composelive.nodes.foundation.common

import androidx.compose.runtime.Immutable
import app.cash.redwood.ui.Dp
import io.composelive.nodes.foundation.common.Arrangement.HorizontalOrVertical
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable
public sealed interface Arrangement {
    @Serializable
    public sealed interface Horizontal
    @Serializable
    public sealed interface Vertical
    @Serializable
    public sealed interface HorizontalOrVertical: Horizontal, Vertical

    public companion object {
        public val Top: Vertical = AdjustmentArrangement(Ids.TOP)
        public val Bottom: Vertical = AdjustmentArrangement(Ids.BOTTOM)
        public val Center: HorizontalOrVertical = AdjustmentArrangement(Ids.CENTER)
        public val Start: Horizontal = AdjustmentArrangement(Ids.START)
        public val End: Horizontal = AdjustmentArrangement(Ids.END)
        public val SpaceBetween: HorizontalOrVertical = AdjustmentArrangement(Ids.SPACE_BETWEEN)
        public val SpaceEvenly: HorizontalOrVertical = AdjustmentArrangement(Ids.SPACE_EVENLY)
        public val SpaceAround: HorizontalOrVertical = AdjustmentArrangement(Ids.SPACE_AROUND)

        public fun spacedBy(space: Dp): HorizontalOrVertical = SpaceArrangement(space)
    }

    public object Ids {
        public const val TOP: Int = 0
        public const val BOTTOM: Int = 1
        public const val CENTER: Int = 2
        public const val START: Int = 3
        public const val END: Int = 4
        public const val SPACE_BETWEEN: Int = 5
        public const val SPACE_EVENLY: Int = 6
        public const val SPACE_AROUND: Int = 7
    }
}

@[Immutable Serializable JvmInline]
public value class AdjustmentArrangement(public val ordinal: Int) : HorizontalOrVertical {
    override fun toString(): String =  when (ordinal) {
        Arrangement.Ids.TOP -> "Arrangement#Top"
        Arrangement.Ids.BOTTOM -> "Arrangement#Bottom"
        Arrangement.Ids.CENTER -> "Arrangement#Center"
        Arrangement.Ids.START -> "Arrangement#Start"
        Arrangement.Ids.END -> "Arrangement#End"
        Arrangement.Ids.SPACE_BETWEEN -> "Arrangement#SpaceBetween"
        Arrangement.Ids.SPACE_EVENLY -> "Arrangement#SpaceEvenly"
        Arrangement.Ids.SPACE_AROUND -> "Arrangement#SpaceAround"
        else -> throw AssertionError()
    }
}

@[Immutable Serializable JvmInline]
public value class SpaceArrangement(public val spacing: Dp): HorizontalOrVertical

