package io.composelive.nodes.foundation.host.composeui

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
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import app.cash.redwood.widget.compose.ComposeWidgetChildren
import io.composelive.nodes.foundation.common.ScrollProgress
import io.composelive.nodes.foundation.common.lazylayout.ScrollRequest
import io.composelive.nodes.foundation.host.composeui.lazylayout.VisibleItemsChangeThrottler
import io.composelive.nodes.foundation.host.composeui.lazylayout.rememberCurrentOffset
import io.composelive.nodes.foundation.host.composeui.local.rememberScrollProgressState
import io.composelive.nodes.foundation.widget.LazyGrid
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import app.cash.redwood.Modifier as RedwoodModifier
import io.composelive.nodes.foundation.common.Arrangement as RedwoodArrangement

internal class FoundationLazyGrid : LazyGrid<@Composable (Modifier) -> Unit> {
    private var isVertical by mutableStateOf(false)

    private var cells: GridCells by mutableStateOf(GridCells.Fixed(1))
    private var horizontalArrangement: RedwoodArrangement.Horizontal
            by mutableStateOf(RedwoodArrangement.Start)
    private var verticalArrangement: RedwoodArrangement.Vertical
            by mutableStateOf(RedwoodArrangement.Top)
    private var boundScrollProgress: ScrollProgress? by mutableStateOf(null)

    private var programmaticScrollRequest by mutableStateOf<ScrollRequest?>(null)

    private var lastProgrammaticallyScrolledId: Int? = null

    override var modifier: RedwoodModifier = RedwoodModifier

    override val items = ComposeWidgetChildren()
    private val throttler = VisibleItemsChangeThrottler()

    override fun isVertical(isVertical: Boolean) {
        this.isVertical = isVertical
    }

    override fun visibleItemsChanged(
        visibleItemsChanged: (firstIndex: Int, lastIndex: Int, changeId: Int) -> Unit,
    ) {
        throttler.send = visibleItemsChanged
    }

    override fun lastReceivedVisibleItemsChangedId(lastReceivedVisibleItemsChangedId: Int) {
        throttler.receivedVisibleItemsChangedId(lastReceivedVisibleItemsChangedId)
    }

    override fun programmaticScrollRequest(programmaticScrollRequest: ScrollRequest?) {
        this.programmaticScrollRequest = programmaticScrollRequest
    }

    override fun chunks(chunks: Int) {
        cells = GridCells.Fixed(chunks)
    }

    override fun horizontalArrangement(horizontalArrangement: RedwoodArrangement.Horizontal) {
        this.horizontalArrangement = horizontalArrangement
    }

    override fun verticalArrangement(verticalArrangement: RedwoodArrangement.Vertical) {
        this.verticalArrangement = verticalArrangement
    }

    override fun boundScrollProgress(boundScrollProgress: ScrollProgress?) {
        this.boundScrollProgress = boundScrollProgress
    }

    override val value: @Composable (Modifier) -> Unit = { modifier ->
        val lazyItems by remember {
            derivedStateOf { items.widgets.filterIsInstance<FoundationLazyGridItems>() }
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
        LaunchedEffect(state) {
            snapshotFlow { state.layoutInfo.visibleItemsInfo }.collectLatest { info ->
                throttler.requestSend(
                    firstIndex = info.firstOrNull()?.index ?: 0,
                    lastIndex = info.lastOrNull()?.index ?: 0,
                )
            }
        }
        LaunchedEffect(Unit) {
            snapshotFlow { programmaticScrollRequest }.filterNotNull().collectLatest { itemIndex ->
                if (itemIndex.id != lastProgrammaticallyScrolledId) {
                    lastProgrammaticallyScrolledId = itemIndex.id
                    if (itemIndex.animated) {
                        state.animateScrollToItem(index = itemIndex.index)
                    } else {
                        state.scrollToItem(index = itemIndex.index)
                    }
                }
            }
        }
        val boundMotionProgress = boundScrollProgress
        if (boundMotionProgress != null) {
            val offset = rememberCurrentOffset(
                position = remember { derivedStateOf { state.firstVisibleItemIndex } },
                itemOffset = remember { derivedStateOf { state.firstVisibleItemScrollOffset } },
            )
            val scrollProgressState = rememberScrollProgressState(boundMotionProgress.id)
            LaunchedEffect(offset, scrollProgressState) {
                snapshotFlow { offset.value }.collectLatest { offset ->
                    scrollProgressState?.offsetChanged(offset)
                }
            }
        }
        if (isVertical) {
            LazyVerticalGrid(
                modifier = modifier,
                state = state,
                columns = cells,
                horizontalArrangement = horizontalArrangement.toHorizontalArrangement(),
                verticalArrangement = verticalArrangement.toVerticalArrangement(),
                content = content,
            )
        } else {
            LazyHorizontalGrid(
                modifier = modifier,
                rows = cells,
                horizontalArrangement = horizontalArrangement.toHorizontalArrangement(),
                verticalArrangement = verticalArrangement.toVerticalArrangement(),
                state = state,
                content = content,
            )
        }
    }
}
