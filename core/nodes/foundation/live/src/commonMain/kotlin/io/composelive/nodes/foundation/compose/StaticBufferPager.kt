package io.composelive.nodes.foundation.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import app.cash.redwood.Modifier
import io.composelive.nodes.foundation.common.PaddingValues

@Composable
public fun StaticBufferHorizontalPager(
    state: PagerState,
    bufferSize: Int,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    pageContent: @Composable (index: Int, inRange: Boolean) -> Unit,
) {
    StaticBufferPager(
        isVertical = false,
        state = state,
        bufferSize = bufferSize,
        modifier = modifier,
        contentPadding = contentPadding,
        pageContent = pageContent,
    )
}

@Composable
public fun StaticBufferVerticalPager(
    state: PagerState,
    bufferSize: Int,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    pageContent: @Composable (index: Int, inRange: Boolean) -> Unit,
) {
    StaticBufferPager(
        isVertical = true,
        state = state,
        bufferSize = bufferSize,
        modifier = modifier,
        contentPadding = contentPadding,
        pageContent = pageContent,
    )
}

@Composable
private inline fun StaticBufferPager(
    isVertical: Boolean,
    state: PagerState,
    bufferSize: Int,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    crossinline pageContent: @Composable (index: Int, inRange: Boolean) -> Unit,
) {
    val pageCount by derivedStateOf { state.pageCount }
    Pager(
        modifier = modifier,
        isVertical = isVertical,
        contentPadding = contentPadding,
        pageChanged = { index -> state.currentPage = index },
        scrollInProgressChanged = { inProgress -> state.scrollInProgress = inProgress },
        programmaticScrollRequest = state.programmaticScrollRequest,
        pageCount = pageCount,
        items = {
            StaticBuffer(
                count = { pageCount },
                bufferSize = bufferSize,
            ) { index, inRange ->
                ShallowWrapper {
                    pageContent(index, inRange)
                }
            }
        },
    )
}
