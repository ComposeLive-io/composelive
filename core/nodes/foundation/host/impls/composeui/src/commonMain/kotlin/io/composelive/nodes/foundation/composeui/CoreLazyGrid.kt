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
package io.composelive.nodes.foundation.composeui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import io.composelive.nodes.foundation.common.MotionProgress
import io.composelive.nodes.foundation.common.lazygrid.ScrollItemIndex
import io.composelive.nodes.foundation.composeui.children.Children
import io.composelive.nodes.foundation.composeui.lazygrid.rememberCurrentOffset
import io.composelive.nodes.foundation.composeui.local.rememberMotionProgressState
import io.composelive.nodes.foundation.widget.LazyGrid
import app.cash.redwood.Modifier as RedwoodModifier
import io.composelive.nodes.foundation.common.Arrangement as RedwoodArrangement

internal class CoreLazyGrid : LazyGrid<@Composable (Modifier) -> Unit> {
    private var isVertical by mutableStateOf(false)

    private var nextViewportChangeId = 0
    private var isSendingViewportChange = false
    private var delayedViewportChange = DelayedViewportChange()

    private var cells: GridCells by mutableStateOf(GridCells.Fixed(1))
    private var horizontalArrangement: RedwoodArrangement? by mutableStateOf(null)
    private var verticalArrangement: RedwoodArrangement? by mutableStateOf(null)
    private var boundMotionProgress: MotionProgress? by mutableStateOf(null)

    private var onViewportChanged:
            ((firstVisibleItemIndex: Int, lastVisibleItemIndex: Int, id: Int) -> Unit)?
            by mutableStateOf(null)

    private var scrollItemIndex by mutableStateOf<ScrollItemIndex?>(null)

    override var modifier: RedwoodModifier = RedwoodModifier

    override val items = Children()

    override fun isVertical(isVertical: Boolean) {
        this.isVertical = isVertical
    }

    override fun onViewportChanged(onViewportChanged: (firstVisibleItemIndex: Int, lastVisibleItemIndex: Int, id: Int) -> Unit) {
        this.onViewportChanged = onViewportChanged
    }

    override fun lastReceivedViewportChangedId(lastReceivedViewportChangedId: Int) {
        if (lastReceivedViewportChangedId != -1) {
            if (delayedViewportChange.isSet()) {
                isSendingViewportChange = true
                val viewportChangeId = nextViewportChangeId.also { nextViewportChangeId++ }
                onViewportChanged!!(
                    delayedViewportChange.firstVisibleItemIndex,
                    delayedViewportChange.lastVisibleItemIndex,
                    viewportChangeId
                )
                delayedViewportChange.clear()
            } else {
                isSendingViewportChange = false
            }
        }
    }

    override fun scrollItemIndex(scrollItemIndex: ScrollItemIndex?) {
        this.scrollItemIndex = scrollItemIndex
    }

    override fun chunks(chunks: Int) {
        cells = GridCells.Fixed(chunks)
    }

    override fun horizontalArrangement(horizontalArrangement: RedwoodArrangement?) {
        this.horizontalArrangement = horizontalArrangement
    }

    override fun verticalArrangement(verticalArrangement: RedwoodArrangement?) {
        this.verticalArrangement = verticalArrangement
    }

    override fun boundMotionProgress(boundMotionProgress: MotionProgress?) {
        this.boundMotionProgress = boundMotionProgress
    }

    override val value: @Composable (Modifier) -> Unit = { modifier ->
        val lazyItems by remember {
            derivedStateOf { items.widgets.filterIsInstance<CoreLazyItems>() }
        }
        val content: LazyGridScope.() -> Unit = {
            lazyItems.forEach { items ->
                val span = items.span
                if (span != null) {
                    items(
                        count = items.items.totalItemsCount,
                        span = {
                            GridItemSpan(span.value)
                        }
                    ) { itemIndex ->
                        items.items.Render(virtualIndex = itemIndex)
                    }
                } else {
                    items(
                        count = items.items.totalItemsCount,
                    ) { itemIndex ->
                        items.items.Render(virtualIndex = itemIndex)
                    }
                }
            }
        }

        val state = rememberLazyGridState()
        val lastVisibleItemIndex by remember {
            derivedStateOf { state.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
        }
        LaunchedEffect(lastVisibleItemIndex) {
            lastVisibleItemIndex?.let { lastVisibleItemIndex ->
                if (!isSendingViewportChange) {
                    isSendingViewportChange = true
                    val viewportChangeId = nextViewportChangeId.also { nextViewportChangeId++ }
                    onViewportChanged!!(
                        state.firstVisibleItemIndex,
                        lastVisibleItemIndex,
                        viewportChangeId
                    )
                } else {
                    delayedViewportChange.firstVisibleItemIndex = state.firstVisibleItemIndex
                    delayedViewportChange.lastVisibleItemIndex = lastVisibleItemIndex
                }
            }
        }
        LaunchedEffect(scrollItemIndex) {
            scrollItemIndex?.let { itemIndex ->
                if (itemIndex.animated) {
                    state.animateScrollToItem(index = itemIndex.index)
                } else {
                    state.scrollToItem(index = itemIndex.index)
                }
            }
        }
        val boundMotionProgress = boundMotionProgress
        if (boundMotionProgress != null) {
            val offset by rememberCurrentOffset(state)
            val motionProgressState = rememberMotionProgressState(boundMotionProgress.id)
            LaunchedEffect(offset, motionProgressState) {
                motionProgressState?.offsetChanged(offset)
            }
        }
        if (isVertical) {
            LazyVerticalGrid(
                modifier = modifier,
                state = state,
                columns = cells,
                horizontalArrangement = remember(horizontalArrangement) {
                    horizontalArrangement
                        ?.toHorizontalArrangement()
                        ?: Arrangement.Start
                },
                verticalArrangement = remember(verticalArrangement) {
                    verticalArrangement
                        ?.toVerticalArrangement()
                        ?: Arrangement.Top
                },
                content = content,
            )
        } else {
            LazyHorizontalGrid(
                modifier = modifier,
                rows = cells,
                horizontalArrangement = remember(horizontalArrangement) {
                    horizontalArrangement
                        ?.toHorizontalArrangement()
                        ?: Arrangement.Start
                },
                verticalArrangement = remember(verticalArrangement) {
                    verticalArrangement
                        ?.toVerticalArrangement()
                        ?: Arrangement.Top
                },
                state = state,
                content = content,
            )
        }
    }

    private class DelayedViewportChange {
        var firstVisibleItemIndex: Int = -1
        var lastVisibleItemIndex: Int = -1

        fun isSet() = firstVisibleItemIndex != -1 && lastVisibleItemIndex != -1

        fun clear() {
            firstVisibleItemIndex = -1
            lastVisibleItemIndex = -1
        }
    }
}
