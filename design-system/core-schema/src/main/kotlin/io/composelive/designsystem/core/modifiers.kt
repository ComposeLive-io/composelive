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
package io.composelive.designsystem.core

import app.cash.redwood.schema.Modifier
import app.cash.redwood.ui.Dp
import io.composelive.designsystem.core.api.Alignment
import io.composelive.designsystem.core.api.Color
import io.composelive.designsystem.core.api.PaddingValues
import io.composelive.designsystem.core.api.Shape

@Modifier(1)
public data class Padding(
    val values: PaddingValues,
)

@Modifier(2, ColumnScope::class)
public data class AlignHorizontally(
    val alignment: Alignment.Horizontal,
)

@Modifier(3, RowScope::class)
public data class AlignVertically(
    val alignment: Alignment.Vertical,
)

@Modifier(4, BoxScope::class)
public data class Align(
    val alignment: Alignment,
)

@Modifier(5)
public data class Width(
    val width: Dp,
)

@Modifier(6)
public data class Height(
    val height: Dp,
)

@Modifier(7, RowScope::class, ColumnScope::class)
public data class Weight(
    val value: Double,
)

@Modifier(8)
public data object FillMaxWidth

@Modifier(9)
public data object FillMaxHeight

@Modifier(10)
public data class AspectRatio(
    val ratio: Double,
)

@Modifier(11)
public data class Background(
    val color: Color = Color.Unspecified,
    val shape: Shape = Shape.Rectangle,
)

@Modifier(12)
public data class Clip(
    val shape: Shape,
)

@Modifier(13, LazyGridItemScope::class)
public data object StickyHeader

@Modifier(14)
public data object Shimmer

@Modifier(15)
public data class Alpha(val value: Double)

@Modifier(16)
public data class DefaultMinSize(
    val minWidth: Dp = Dp(Int.MAX_VALUE.toDouble()), // Dp.Unspecified
    val minHeight: Dp = Dp(Int.MAX_VALUE.toDouble()), // Dp.Unspecified
)

@Modifier(17)
public data class WrapContentHeight(
    val align: Alignment.Vertical = Alignment.CenterVertically,
    val unbounded: Boolean = false,
)

@Modifier(18)
public data class LayoutId(
    val id: String,
)
