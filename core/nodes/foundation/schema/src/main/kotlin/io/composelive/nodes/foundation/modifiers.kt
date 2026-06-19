package io.composelive.nodes.foundation

import app.cash.redwood.schema.Modifier
import app.cash.redwood.ui.Dp
import io.composelive.nodes.foundation.common.Alignment
import io.composelive.nodes.foundation.common.Brush
import io.composelive.nodes.foundation.common.Color
import io.composelive.nodes.foundation.common.Indication
import io.composelive.nodes.foundation.common.IntrinsicSize
import io.composelive.nodes.foundation.common.PaddingValues
import io.composelive.nodes.foundation.common.Shape

/**
 * Add additional space around the item.
 */
@Modifier(1)
data class Padding(
    val values: PaddingValues,
)

/**
 * Set the alignment for an item along the horizontal axis.
 */
@Modifier(2, ColumnScope::class, FlowColumnScope::class)
data class AlignHorizontally(
    val alignment: Alignment.Horizontal,
)

/**
 * Set the alignment for an item along the vertical axis.
 */
@Modifier(3, RowScope::class, FlowRowScope::class)
data class AlignVertically(
    val alignment: Alignment.Vertical,
)

@Modifier(4, BoxScope::class, AsyncBoxWithConstraintsScope::class)
data class Align(
    val alignment: Alignment,
)

/**
 * Set a required width for an item.
 */
@Modifier(5)
data class Width(
    val width: Dp,
)

/**
 * Set a required height for an item.
 */
@Modifier(6)
data class Height(
    val height: Dp,
)

@Modifier(7, RowScope::class, FlowRowScope::class, ColumnScope::class, FlowColumnScope::class)
data class Weight(
    val value: Double,
)

@Modifier(8)
data object FillMaxWidth

@Modifier(9)
data object FillMaxHeight

@Modifier(10)
data class AspectRatio(
    val ratio: Double,
)

@Modifier(11)
data class Background(
    val color: Color = Color.Unspecified,
    val shape: Shape = Shape(rectangle = Shape.Rectangle),
)

@Modifier(12)
data class Clip(
    val shape: Shape,
)

@Modifier(13, LazyGridItemScope::class)
data object StickyHeader

@Modifier(14)
data object Shimmer

@Modifier(15)
data class Alpha(val value: Double)

@Modifier(16)
data class DefaultMinSize(
    val minWidth: Dp = Dp(Int.MAX_VALUE.toDouble()), // Dp.Unspecified
    val minHeight: Dp = Dp(Int.MAX_VALUE.toDouble()), // Dp.Unspecified
)

@Modifier(17)
data class WrapContentHeight(
    val align: Alignment.Vertical = Alignment.CenterVertically,
    val unbounded: Boolean = false,
)

@Modifier(18)
data class LayoutId(
    val id: String,
)

@Modifier(19)
data class HorizontalScroll(
    val initial: Int,
)

@Modifier(20)
data class BrushBackground(
    val brush: Brush,
    val shape: Shape = Shape(rectangle = Shape.Rectangle),
)

@Modifier(21)
data class Clickable(
    val actionId: Int,
    val enabled: Boolean,
    val indication: Indication,
)

@Modifier(22)
data class IntrinsicHeight(
    val size: IntrinsicSize
)

@Modifier(23)
data class IntrinsicWidth(
    val size: IntrinsicSize
)

@Modifier(-4_543_827) // Reserved tag
data object Reuse
