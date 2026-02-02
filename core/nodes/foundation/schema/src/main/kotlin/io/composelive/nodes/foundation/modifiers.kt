/*
 * Copyright (C) 2022 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.composelive.nodes.foundation

import app.cash.redwood.schema.Modifier
import app.cash.redwood.ui.Dp
import io.composelive.nodes.foundation.common.Alignment
import io.composelive.nodes.foundation.common.Color
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
@Modifier(2, ColumnScope::class)
data class AlignHorizontally(
    val alignment: Alignment.Horizontal,
)

/**
 * Set the alignment for an item along the vertical axis.
 */
@Modifier(3, RowScope::class)
data class AlignVertically(
    val alignment: Alignment.Vertical,
)

@Modifier(4, BoxScope::class)
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

@Modifier(7, RowScope::class, ColumnScope::class)
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

@Modifier(-4_543_827) // Reserved tag
data object Reuse
