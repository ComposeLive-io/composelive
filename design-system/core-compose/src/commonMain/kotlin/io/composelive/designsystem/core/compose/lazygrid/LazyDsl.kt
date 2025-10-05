/*
 * Copyright (C) 2023 Square, Inc.
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
package io.composelive.designsystem.core.compose.lazygrid

import androidx.compose.runtime.Composable
import app.cash.redwood.LayoutScopeMarker
import app.cash.redwood.Modifier
import io.composelive.designsystem.core.api.Arrangement
import io.composelive.designsystem.core.api.MotionProgress
import io.composelive.designsystem.core.api.lazygrid.GridItemSpan
import io.composelive.designsystem.core.compose.Box
import io.composelive.designsystem.core.compose.LazyGrid
import io.composelive.designsystem.core.compose.LazyGridItemScopeImpl.stickyHeader

/**
 * Receiver scope which is used by [LazyVerticalGrid] and [LazyHorizontalGrid].
 */
@LayoutScopeMarker
public interface LazyGridScope {
    /**
     * Adds a single item.
     *
     * @param content The content of the item.
     */
    public fun item(
        span: ((Int) -> GridItemSpan)? = null,
        content: @Composable () -> Unit,
    )

    /**
     * Adds a [count] of items.
     *
     * @param count The items count.
     * @param itemContent The content displayed by a single item.
     */
    public fun items(
        count: Int,
        span: ((Int) -> GridItemSpan)? = null,
        itemContent: @Composable (index: Int) -> Unit,
    )
}

public inline fun LazyGridScope.stickyHeader(
    crossinline itemContent: @Composable () -> Unit,
): Unit = item {
    Box(modifier = Modifier.stickyHeader()) {
        itemContent()
    }
}

/**
 * Adds a list of items.
 *
 * @param items The data list.
 * @param itemContent The content displayed by a single item.
 */
public inline fun <T> LazyGridScope.items(
    items: List<T>,
    crossinline itemContent: @Composable (item: T) -> Unit,
): Unit = items(items.size) {
    itemContent(items[it])
}

/**
 * Adds a list of items where the content of an item is aware of its index.
 *
 * @param items The data list.
 * @param itemContent The content displayed by a single item.
 */
public inline fun <T> LazyGridScope.itemsIndexed(
    items: List<T>,
    noinline span: ((Int) -> GridItemSpan)? = null,
    crossinline itemContent: @Composable (index: Int, item: T) -> Unit,
): Unit = items(
    items.size,
    span,
) {
    itemContent(it, items[it])
}

/**
 * Adds an array of items.
 *
 * @param items The data array.
 * @param itemContent The content displayed by a single item.
 */
public inline fun <T> LazyGridScope.items(
    items: Array<T>,
    crossinline itemContent: @Composable (item: T) -> Unit,
): Unit = items(
    items.size,
) {
    itemContent(items[it])
}

/**
 * Adds an array of items where the content of an item is aware of its index.
 *
 * @param items The data array.
 * @param itemContent The content displayed by a single item.
 */
public inline fun <T> LazyGridScope.itemsIndexed(
    items: Array<T>,
    crossinline itemContent: @Composable (index: Int, item: T) -> Unit,
): Unit = items(
    items.size,
) {
    itemContent(it, items[it])
}

@RequiresOptIn("This Redwood LazyLayout API is experimental and may change in the future.")
public annotation class ExperimentalRedwoodLazyLayoutApi

/**
 * The horizontally scrolling list that only composes and lays out the currently visible items.
 * The [content] block defines a DSL which allows you to emit items of different types. For
 * example you can use [LazyGridScope.item] to add a single item and [LazyGridScope.items] to add
 * a list of items.
 *
 * The purpose of [placeholder] is to define the temporary content of an on-screen item while the
 * content of that item (as described by the [content] block) is being retrieved. When the content
 * of that item has been retrieved, the [placeholder] is replaced with that of the content.
 *
 * @param state The state object to be used to control or observe the list's state.
 * @param width Sets whether the row's width will wrap its contents ([Constraint.Wrap]) or match the
 * width of its parent ([Constraint.Fill]).
 * @param height Sets whether the row's height will wrap its contents ([Constraint.Wrap]) or match
 * the height of its parent ([Constraint.Fill]).
 * @param margin Applies margin (space) around the list. This can also be applied to an individual
 * item using `Modifier.margin`.
 * @param verticalAlignment the vertical alignment applied to the items.
 * @param modifier The modifier to apply to this layout.
 * @param content A block which describes the content. Inside this block you can use methods like
 * [LazyGridScope.item] to add a single item or [LazyGridScope.items] to add a list of items.
 */
@Composable
public fun LazyHorizontalGrid(
    modifier: Modifier = Modifier,
    state: LazyGridState = rememberLazyGridState(),
    rows: Int,
    horizontalArrangement: Arrangement? = null,
    verticalArrangement: Arrangement? = null,
    boundMotionProgress: MotionProgress? = null,
    content: LazyGridScope.() -> Unit,
) {
    LazyGrid(
        isVertical = false,
        state = state,
        chunks = rows,
        horizontalArrangement = horizontalArrangement,
        verticalArrangement = verticalArrangement,
        boundMotionProgress = boundMotionProgress,
        modifier = modifier,
        content = content,
    )
}

/**
 * The vertically scrolling list that only composes and lays out the currently visible items.
 * The [content] block defines a DSL which allows you to emit items of different types. For
 * example you can use [LazyGridScope.item] to add a single item and [LazyGridScope.items] to add
 * a list of items.
 *
 * The purpose of [placeholder] is to define the temporary content of an on-screen item while the
 * content of that item (as described by the [content] block) is being retrieved. When the content
 * of that item has been retrieved, the [placeholder] is replaced with that of the content.
 *
 * @param state The state object to be used to control or observe the list's state.
 * @param width Sets whether the row's width will wrap its contents ([Constraint.Wrap]) or match the
 * width of its parent ([Constraint.Fill]).
 * @param height Sets whether the row's height will wrap its contents ([Constraint.Wrap]) or match
 * the height of its parent ([Constraint.Fill]).
 * @param margin Applies margin (space) around the list. This can also be applied to an individual
 * item using `Modifier.margin`.
 * @param horizontalAlignment The horizontal alignment applied to the items.
 * @param modifier The modifier to apply to this layout.
 * @param content A block which describes the content. Inside this block you can use methods like
 * [LazyGridScope.item] to add a single item or [LazyGridScope.items] to add a list of items.
 */
@Composable
public fun LazyVerticalGrid(
    modifier: Modifier = Modifier,
    state: LazyGridState = rememberLazyGridState(),
    columns: Int,
    horizontalArrangement: Arrangement? = null,
    verticalArrangement: Arrangement? = null,
    boundMotionProgress: MotionProgress? = null,
    content: LazyGridScope.() -> Unit,
) {
    LazyGrid(
        isVertical = true,
        state = state,
        chunks = columns,
        horizontalArrangement = horizontalArrangement,
        verticalArrangement = verticalArrangement,
        boundMotionProgress = boundMotionProgress,
        modifier = modifier,
        content = content,
    )
}
