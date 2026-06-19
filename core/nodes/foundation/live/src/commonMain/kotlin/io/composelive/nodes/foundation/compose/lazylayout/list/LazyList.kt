package io.composelive.nodes.foundation.compose.lazylayout.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import app.cash.redwood.Modifier
import app.cash.redwood.RedwoodCodegenApi
import io.composelive.nodes.foundation.common.Alignment
import io.composelive.nodes.foundation.common.Arrangement
import io.composelive.nodes.foundation.common.PaddingValues
import io.composelive.nodes.foundation.common.ScrollProgress
import io.composelive.nodes.foundation.compose.LazyList
import io.composelive.nodes.foundation.compose.LazyListItems
import io.composelive.nodes.foundation.compose.LazyListScope
import io.composelive.nodes.foundation.compose.LazyListState
import io.composelive.nodes.foundation.compose.lazylayout.LazyLayoutIntervals

@OptIn(RedwoodCodegenApi::class)
@Composable
internal fun LazyList(
    isVertical: Boolean,
    state: LazyListState,
    contentPadding: PaddingValues?,
    reverseLayout: Boolean,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    verticalAlignment: Alignment.Vertical = Alignment.Top,
    boundScrollProgress: ScrollProgress? = null,
    modifier: Modifier = Modifier,
    content: LazyListScope.() -> Unit,
) {
    var lastReceivedVisibleItemsChangedId by remember { mutableIntStateOf(-1) }
    LazyList(
        isVertical = isVertical,
        visibleItemsChanged = { firstIndex, lastIndex, changeId ->
            state.onUserScroll(firstIndex, lastIndex)
            lastReceivedVisibleItemsChangedId = changeId
        },
        lastReceivedVisibleItemsChangedId = lastReceivedVisibleItemsChangedId,
        contentPadding = contentPadding,
        reverseLayout = reverseLayout,
        horizontalArrangement = horizontalArrangement,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        verticalAlignment = verticalAlignment,
        boundScrollProgress = boundScrollProgress,
        modifier = modifier,
        programmaticScrollRequest = state.programmaticScrollIndex,
        items = {
            val itemProvider = rememberLazyListItemProvider(content)
            LazyLayoutIntervals(
                state,
                itemProvider
            ) { _, itemsBefore, itemsAfter, placeholder, items ->
                LazyListItems(
                    itemsBefore = itemsBefore,
                    itemsAfter = itemsAfter,
                    placeholder = placeholder,
                    items = items,
                )
            }
        },
    )
}
