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
package io.composelive.designsystem.core.composeui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import app.cash.redwood.widget.Widget
import io.composelive.designsystem.core.api.MotionProgress
import io.composelive.designsystem.core.api.lazygrid.ScrollItemIndex
import io.composelive.designsystem.core.composeui.lazygrid.rememberCurrentOffset
import io.composelive.designsystem.core.composeui.local.findMotionProgressState
import io.composelive.designsystem.core.modifier.StickyHeader
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import io.composelive.designsystem.core.api.Arrangement as RedwoodArrangement
import io.composelive.designsystem.core.api.lazygrid.GridItemSpan as RedwoodGridItemSpan

@Composable
public fun CoreLazyGrid(
    isVertical: Boolean,
    onViewportChanged: (Int, Int) -> Unit,
    programmaticScrollIndex: ScrollItemIndex?,
    chunks: Int,
    horizontalArrangement: RedwoodArrangement?,
    verticalArrangement: RedwoodArrangement?,
    boundMotionProgress: MotionProgress?,
    modifier: Modifier,
    content: LazyGridScope.() -> Unit,
) {
    val state = rememberLazyGridState()
    val lastVisibleItemIndex by remember {
        derivedStateOf { state.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
    }
    LaunchedEffect(lastVisibleItemIndex) {
        lastVisibleItemIndex?.let { lastVisibleItemIndex ->
            onViewportChanged(state.firstVisibleItemIndex, lastVisibleItemIndex)
        }
    }
    LaunchedEffect(programmaticScrollIndex) {
        programmaticScrollIndex?.let { index ->
            if (index.animated) {
                state.animateScrollToItem(index = index.index)
            } else {
                state.scrollToItem(index = index.index)
            }
        }
    }
    val boundMotionProgress = boundMotionProgress
    if (boundMotionProgress != null) {
        val offset by rememberCurrentOffset(state)
        val motionProgressState = findMotionProgressState(boundMotionProgress.id)
        motionProgressState?.offsetChanged(offset)
    }
    if (isVertical) {
        LazyVerticalGrid(
            modifier = modifier,
            state = state,
            columns = GridCells.Fixed(chunks),
            horizontalArrangement = horizontalArrangement
                ?.toHorizontalArrangement()
                ?: Arrangement.Start,
            verticalArrangement = verticalArrangement
                ?.toVerticalArrangement()
                ?: Arrangement.Top,
            content = content,
        )
    } else {
        LazyHorizontalGrid(
            modifier = modifier,
            state = state,
            horizontalArrangement = horizontalArrangement
                ?.toHorizontalArrangement()
                ?: Arrangement.Start,
            verticalArrangement = verticalArrangement
                ?.toVerticalArrangement()
                ?: Arrangement.Top,
            rows = GridCells.Fixed(chunks),
            content = content,
        )
    }
}

public inline fun lazyGridItems(
    itemCount: Int,
    spans: List<RedwoodGridItemSpan>,
    crossinline isStickyHeader: (index: Int) -> Boolean,
    crossinline item: @Composable (index: Int) -> Unit,
): LazyGridScope.() -> Unit = {
    val itemChunkIndexes = mutableListOf<Int>()
    var itemsOffset = 0
    repeat(itemCount) { index ->
        if (isStickyHeader(index)) {
            addItems(
                indexOffset = itemsOffset,
                spans = spans,
                itemIndexes = itemChunkIndexes.toImmutableList(),
                item = item,
            )
            itemsOffset += itemChunkIndexes.size
            itemChunkIndexes.clear()
            stickyHeader {
                item(index)
            }
            itemsOffset++
        } else {
            itemChunkIndexes.add(index)
        }
    }
    addItems(
        indexOffset = itemsOffset,
        spans = spans,
        itemIndexes = itemChunkIndexes.toImmutableList(),
        item = item,
    )
}

@PublishedApi
internal inline fun LazyGridScope.addItems(
    indexOffset: Int,
    spans: List<RedwoodGridItemSpan>,
    itemIndexes: ImmutableList<Int>,
    crossinline item: @Composable (index: Int) -> Unit,
) {
    itemsIndexed(
        itemIndexes,
        span = { index, _ ->
            val span = spans.getOrNull(indexOffset + index) ?: RedwoodGridItemSpan.SINGLE
            GridItemSpan(span.value)
        }
    ) { _, itemIndex ->
        item(itemIndex)
    }
}

internal fun isStickyHeader(widget: Widget<@Composable (Modifier) -> Unit>): Boolean {
    var found = false
    widget.modifier.forEachScoped { element ->
        if (element is StickyHeader) found = true
    }
    return found
}
