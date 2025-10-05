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
package io.composelive.designsystem.core.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import app.cash.redwood.Modifier
import io.composelive.designsystem.core.api.PaddingValues
import io.composelive.designsystem.core.api.lazygrid.ScrollItemIndex

@Composable
public fun HorizontalPager(
    state: PagerState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    pageContent: @Composable (index: Int) -> Unit,
) {
    val itemsCount by derivedStateOf { state.pageCount }
    Pager(
        modifier = modifier,
        isVertical = false,
        contentPadding = contentPadding,
        pageChanged = { index -> state.currentPage = index },
        scrollInProgressChanged = { inProgress -> state.scrollInProgress = inProgress },
        programmaticScrollIndex = state.programmaticScrollIndex,
        items = { (0 until itemsCount).map { pageContent(it) } },
    )
}

@Composable
public fun VerticalPager(
    state: PagerState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    pageContent: @Composable (index: Int) -> Unit,
) {
    val itemsCount = state.pageCount
    Pager(
        modifier = modifier,
        isVertical = true,
        contentPadding = contentPadding,
        pageChanged = { index -> state.currentPage = index },
        scrollInProgressChanged = { inProgress -> state.scrollInProgress = inProgress },
        programmaticScrollIndex = state.programmaticScrollIndex,
        items = { (0 until itemsCount).map { pageContent(it) } },
    )
}

@Stable
public class PagerState(pageCount: () -> Int) {
    private val pageCountState by mutableStateOf(pageCount)
    public val pageCount: Int
        get() = pageCountState()

    public var currentPage: Int by mutableIntStateOf(0)
        internal set

    public var scrollInProgress: Boolean by mutableStateOf(false)
        internal set

    internal var programmaticScrollIndex: ScrollItemIndex
            by mutableStateOf(ScrollItemIndex(id = 0, index = 0, animated = false))
        private set

    public fun animateScrollToPage(index: Int) {
        updateScrollIndex(index, animated = true)
    }

    public fun scrollToPage(index: Int) {
        updateScrollIndex(index, animated = false)
    }

    private fun updateScrollIndex(index: Int, animated: Boolean) {
        programmaticScrollIndex = ScrollItemIndex(
            id = programmaticScrollIndex.id + 1,
            index,
            animated,
        )
    }
}

@Composable
public fun rememberPagerState(pageCount: () -> Int): PagerState {
    return remember(pageCount) { PagerState(pageCount) }
}
