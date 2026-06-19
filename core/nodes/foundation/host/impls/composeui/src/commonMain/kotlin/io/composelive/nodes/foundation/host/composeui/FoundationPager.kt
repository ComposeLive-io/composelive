package io.composelive.nodes.foundation.host.composeui

import androidx.compose.animation.core.tween
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import io.composelive.nodes.foundation.common.lazylayout.ScrollRequest
import io.composelive.nodes.foundation.common.PaddingValues as RedwoodPaddingValues

@Composable
public fun FoundationPager(
    isVertical: Boolean,
    contentPadding: RedwoodPaddingValues,
    pageChanged: (Int) -> Unit,
    scrollInProgressChanged: (Boolean) -> Unit,
    programmaticScrollIndex: ScrollRequest?,
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
    var lastProgrammaticallyScrolledId: Int? by remember { mutableStateOf(null) }
    LaunchedEffect(programmaticScrollIndex) {
        if (programmaticScrollIndex != null &&
            lastProgrammaticallyScrolledId != programmaticScrollIndex.id
        ) {
            lastProgrammaticallyScrolledId = programmaticScrollIndex.id
            if (programmaticScrollIndex.animated) {
                state.animateScrollToPage(
                    page = programmaticScrollIndex.index,
                    animationSpec = tween(),
                )
            } else {
                state.scrollToPage(programmaticScrollIndex.index)
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
