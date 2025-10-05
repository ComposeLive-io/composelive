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

import androidx.compose.runtime.Composable
import app.cash.redwood.schema.Children
import app.cash.redwood.schema.Property
import app.cash.redwood.schema.Widget
import app.cash.redwood.ui.Margin
import io.composelive.designsystem.core.api.Arrangement
import io.composelive.designsystem.core.api.ButtonColors
import io.composelive.designsystem.core.api.Color
import io.composelive.designsystem.core.api.MotionProgress
import io.composelive.designsystem.core.api.PaddingValues
import io.composelive.designsystem.core.api.Shape
import io.composelive.designsystem.core.api.TextFieldValue
import io.composelive.designsystem.core.api.TextStyle
import io.composelive.designsystem.core.api.animation.EnterTransition
import io.composelive.designsystem.core.api.animation.ExitTransition
import io.composelive.designsystem.core.api.lazygrid.GridItemSpan
import io.composelive.designsystem.core.api.lazygrid.ScrollItemIndex

@Widget(1)
public data class Row(
    @Children(1) val content: RowScope.() -> Unit,
)

public object RowScope

@Widget(2)
public data class Column(
    @Children(1) val content: ColumnScope.() -> Unit,
)

public object ColumnScope

@Widget(3)
public data object Spacer

@Widget(4)
public data class Box(
    @Property(1) val onClick: (() -> Unit)? = null,
    @Children(1) val content: BoxScope.() -> Unit = {},
)

public object BoxScope

@Widget(5)
public data class LazyGrid(
    @Property(1) val isVertical: Boolean = true,
    @Property(2) val onViewportChanged: (firstVisibleItemIndex: Int, lastVisibleItemIndex: Int) -> Unit,
    @Property(3) val programmaticScrollIndex: ScrollItemIndex? = null,
    @Property(4) val chunks: Int = 1,
    @Property(5) val horizontalArrangement: Arrangement? = null,
    @Property(6) val verticalArrangement: Arrangement? = null,
    @Property(7) val spans: List<GridItemSpan> = emptyList(),
    @Property(8) val boundMotionProgress: MotionProgress? = null,
    @Children(1) val items: LazyGridItemScope.() -> Unit,
)

public object LazyGridItemScope

@Widget(6)
public data class Pager(
    @Property(1) val isVertical: Boolean = false,
    @Property(2) val contentPadding: PaddingValues = Margin(),
    @Property(3) val pageChanged: (index: Int) -> Unit,
    @Property(4) val scrollInProgressChanged: (Boolean) -> Unit,
    @Property(5) val programmaticScrollIndex: ScrollItemIndex? = null,
    @Children(1) val items: () -> Unit,
)

@Widget(7)
public data class PullToRefreshBox(
    @Property(1) val isRefreshing: Boolean = false,
    @Property(2) val onRefresh: () -> Unit,
    @Children(1) val content: BoxScope.() -> Unit,
)

@Widget(8)
public data class Scaffold(
    @Property(1) val paddingValuesChanged: (PaddingValues) -> Unit,
    @Children(1) val topBar: @Composable () -> Unit = {},
    @Children(2) val bottomBar: @Composable () -> Unit = {},
    @Children(3) val floatingActionButton: @Composable () -> Unit = {},
    @Children(4) val content: @Composable () -> Unit,
)

@Widget(9)
public data class AnimatedVisibility(
    @Property(1) val visible: Boolean = true,
    @Property(2) val enter: EnterTransition = EnterTransition.fadeIn + EnterTransition.expandIn,
    @Property(3) val exit: ExitTransition = ExitTransition.shrinkOut + ExitTransition.fadeOut,
    @Children(1) val content: @Composable () -> Unit,
)

@Widget(10)
public data class Root(
    @Children(1) val content: @Composable () -> Unit,
)

@Widget(11)
public data class TextField(
    @Property(1)
    val state: TextFieldValue = TextFieldValue(),
    @Property(2)
    val hint: String = "",
    @Property(3)
    val style: TextStyle = TextStyle(),
    @Property(6)
    val hintStyle: TextStyle? = null,
    @Property(7)
    val onChange: ((TextFieldValue) -> Unit)? = null,
)

@Widget(12)
public data class Text(
    @Property(1) val text: String = "Text",
    @Property(2) val style: TextStyle = TextStyle(),
)

@Widget(13)
public data class AsyncImage(
    @Property(1) val model: String = "",
)

@Widget(14)
public data class Button(
    @Property(1) val enabled: Boolean = true,
    @Property(2) val shape: Shape? = null,
    @Property(3) val colors: ButtonColors = ButtonColors(),
    @Property(4) val onClick: (() -> Unit)? = null,
    @Children(1) val content: @Composable () -> Unit,
)

@Widget(15)
public data class FloatingActionButton(
    @Property(1) val onClick: (() -> Unit)? = null,
    @Property(2) val shape: Shape? = null,
    @Property(3) val containerColor: Color? = null,
    @Property(4) val contentColor: Color? = null,
    @Children(1) val content: @Composable () -> Unit,
)

@Widget(16)
public data class MotionProgressHolder(
    @Property(1) val progress: MotionProgress? = null,
    @Property(2) val divideScrollBy: Double = 1.0,
)
