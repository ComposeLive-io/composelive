package io.composelive.nodes.foundation.compose.lazylayout.grid

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import app.cash.redwood.Modifier
import app.cash.redwood.RedwoodCodegenApi
import io.composelive.nodes.foundation.common.Arrangement
import io.composelive.nodes.foundation.common.ScrollProgress
import io.composelive.nodes.foundation.compose.LazyGrid
import io.composelive.nodes.foundation.compose.LazyGridItems
import io.composelive.nodes.foundation.compose.LazyGridScope
import io.composelive.nodes.foundation.compose.LazyGridState
import io.composelive.nodes.foundation.compose.lazylayout.LazyLayoutIntervals

@OptIn(RedwoodCodegenApi::class)
@Composable
internal fun LazyGrid(
    isVertical: Boolean,
    state: LazyGridState,
    chunks: Int = 1,
    horizontalArrangement: Arrangement.Horizontal,
    verticalArrangement: Arrangement.Vertical,
    boundScrollProgress: ScrollProgress? = null,
    modifier: Modifier = Modifier,
    content: LazyGridScope.() -> Unit,
) {
    var lastReceivedVisibleItemsChangedId by remember { mutableIntStateOf(-1) }
    LazyGrid(
        isVertical = isVertical,
        visibleItemsChanged = { firstIndex, lastIndex, changeId ->
            state.onUserScroll(firstIndex, lastIndex)
            lastReceivedVisibleItemsChangedId = changeId
        },
        lastReceivedVisibleItemsChangedId = lastReceivedVisibleItemsChangedId,
        chunks = chunks,
        horizontalArrangement = horizontalArrangement,
        verticalArrangement = verticalArrangement,
        boundScrollProgress = boundScrollProgress,
        modifier = modifier,
        programmaticScrollRequest = state.programmaticScrollIndex,
        items = {
            val itemProvider = rememberLazyGridItemProvider(content)
            LazyLayoutIntervals(
                state,
                itemProvider
            ) { interval, itemsBefore, itemsAfter, placeholder, items ->
                LazyGridItems(
                    itemsBefore = itemsBefore,
                    itemsAfter = itemsAfter,
                    span = interval.span,
                    placeholder = placeholder,
                    items = items,
                )
            }
        },
    )
}
