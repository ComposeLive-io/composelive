package io.composelive.nodes.foundation.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import app.cash.redwood.Modifier
import io.composelive.nodes.foundation.common.PaddingValues
import io.composelive.nodes.foundation.common.lazylayout.ScrollRequest

@Composable
public fun HorizontalPager(
    state: PagerState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    pageContent: @Composable (index: Int) -> Unit,
) {
    Pager(
        isVertical = false,
        state = state,
        modifier = modifier,
        contentPadding = contentPadding,
        pageContent = pageContent,
    )
}

@Composable
public fun VerticalPager(
    state: PagerState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    pageContent: @Composable (index: Int) -> Unit,
) {
    Pager(
        isVertical = true,
        state = state,
        modifier = modifier,
        contentPadding = contentPadding,
        pageContent = pageContent,
    )
}

@Composable
private inline fun Pager(
    isVertical: Boolean,
    state: PagerState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    crossinline pageContent: @Composable (index: Int) -> Unit,
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
            for (index in 0 until pageCount) {
                ShallowWrapper {
                    pageContent(index)
                }
            }
        },
    )
}

@Stable
public class PagerState(
    pageCount: () -> Int,
    initialPage: Int = 0,
) {
    private val pageCountState by mutableStateOf(pageCount)
    public val pageCount: Int
        get() = pageCountState()

    public var currentPage: Int by mutableIntStateOf(initialPage)
        internal set

    public var scrollInProgress: Boolean by mutableStateOf(false)
        internal set

    internal var programmaticScrollRequest: ScrollRequest
            by mutableStateOf(ScrollRequest(id = 0, index = initialPage, animated = false))
        private set

    public fun animateScrollToPage(index: Int) {
        updateScrollIndex(index, animated = true)
    }

    public fun scrollToPage(index: Int) {
        updateScrollIndex(index, animated = false)
    }

    private fun updateScrollIndex(index: Int, animated: Boolean) {
        programmaticScrollRequest = ScrollRequest(
            id = programmaticScrollRequest.id + 1,
            index,
            animated,
        )
    }
}

@Composable
public fun rememberPagerState(pageCount: () -> Int, initialPage: Int, ): PagerState {
    return remember(pageCount(), initialPage) {
        PagerState(pageCount, initialPage)
    }
}

@Composable
public fun rememberPagerState(pageCount: () -> Int): PagerState {
    return rememberPagerState(pageCount = pageCount, initialPage = 0)
}
