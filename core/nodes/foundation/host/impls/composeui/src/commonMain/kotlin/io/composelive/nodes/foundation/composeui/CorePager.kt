package io.composelive.nodes.foundation.composeui

import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import io.composelive.nodes.foundation.common.lazygrid.ScrollItemIndex
import io.composelive.nodes.foundation.common.PaddingValues as RedwoodPaddingValues

@Composable
public fun CorePager(
    isVertical: Boolean,
    contentPadding: RedwoodPaddingValues,
    pageChanged: (Int) -> Unit,
    scrollInProgressChanged: (Boolean) -> Unit,
    programmaticScrollIndex: ScrollItemIndex?,
    pageCount: Int,
    item: @Composable (Int) -> Unit,
    modifier: Modifier,
) {
    val contentPadding = remember(contentPadding) { contentPadding.toPaddingValues() }
    val state = rememberPagerState { pageCount }
    LaunchedEffect(state.currentPage) {
        pageChanged(state.currentPage)
    }
    LaunchedEffect(state.isScrollInProgress) {
        scrollInProgressChanged(state.isScrollInProgress)
    }
    LaunchedEffect(programmaticScrollIndex) {
        val index = programmaticScrollIndex
        if (index != null) {
            if (index.animated) {
                state.animateScrollToPage(index.index)
            } else {
                state.scrollToPage(index.index)
            }
        }
    }
    if (isVertical) {
        VerticalPager(
            modifier = modifier,
            state = state,
            contentPadding = contentPadding,
        ) { index ->
            item(index)
        }
    } else {
        HorizontalPager(
            modifier = modifier,
            state = state,
            contentPadding = contentPadding,
        ) { index ->
            item(index)
        }
    }
}
