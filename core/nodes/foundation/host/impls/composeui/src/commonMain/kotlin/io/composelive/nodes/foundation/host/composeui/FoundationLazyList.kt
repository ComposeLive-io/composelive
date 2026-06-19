package io.composelive.nodes.foundation.host.composeui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import app.cash.redwood.ui.Margin
import app.cash.redwood.widget.Widget
import app.cash.redwood.widget.compose.ComposeWidgetChildren
import io.composelive.nodes.foundation.common.PaddingValues
import io.composelive.nodes.foundation.common.ScrollProgress
import io.composelive.nodes.foundation.common.lazylayout.ScrollRequest
import io.composelive.nodes.foundation.host.composeui.lazylayout.VisibleItemsChangeThrottler
import io.composelive.nodes.foundation.host.composeui.lazylayout.rememberCurrentOffset
import io.composelive.nodes.foundation.host.composeui.local.rememberScrollProgressState
import io.composelive.nodes.foundation.widget.LazyList
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import app.cash.redwood.Modifier as RedwoodModifier
import io.composelive.nodes.foundation.common.Alignment as RedwoodAlignment
import io.composelive.nodes.foundation.common.Arrangement as RedwoodArrangement
import io.composelive.nodes.foundation.common.PaddingValues as RedwoodPaddingValues

internal class FoundationLazyList : LazyList<@Composable (Modifier) -> Unit> {
    override var modifier: RedwoodModifier = RedwoodModifier

    private var isVertical by mutableStateOf(false)

    private var contentPadding: RedwoodPaddingValues by mutableStateOf(PaddingValues())

    private var reverseLayout by mutableStateOf(false)

    private var horizontalArrangement: RedwoodArrangement.Horizontal
            by mutableStateOf(RedwoodArrangement.Start)
    private var verticalArrangement: RedwoodArrangement.Vertical
            by mutableStateOf(RedwoodArrangement.Top)

    private var horizontalAlignment: RedwoodAlignment.Horizontal
            by mutableStateOf(RedwoodAlignment.Start)
    private var verticalAlignment: RedwoodAlignment.Vertical
            by mutableStateOf(RedwoodAlignment.Top)

    private var boundScrollProgress: ScrollProgress? by mutableStateOf(null)
    private var programmaticScrollRequest by mutableStateOf<ScrollRequest?>(null)

    private val throttler = VisibleItemsChangeThrottler()

    private var userScrollEnabled by mutableStateOf(true)

    private var lastProgrammaticallyScrolledId: Int? = null

    override val items: Widget.Children<@Composable ((Modifier) -> Unit)>
        get() = _items

    override fun isVertical(isVertical: Boolean) {
        this.isVertical = isVertical
    }

    private val _items = ComposeWidgetChildren()

    override val value: @Composable (Modifier) -> Unit = { modifier ->
        val state = rememberLazyListState()
        val lazyItems by remember {
            derivedStateOf { items.widgets.filterIsInstance<FoundationLazyListItems>() }
        }
        val content: LazyListScope.() -> Unit = {
            lazyItems.forEach { items ->
                items(
                    count = items.items.totalItemsCount,
                ) { itemIndex ->
                    items.items.Render(virtualIndex = itemIndex)
                }
            }
        }
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
                    if (itemIndex.animated) {
                        state.animateScrollToItem(index = itemIndex.index)
                    } else {
                        state.scrollToItem(index = itemIndex.index)
                    }
                    lastProgrammaticallyScrolledId = itemIndex.id
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
            LazyColumn(
                modifier = modifier,
                state = state,
                reverseLayout = reverseLayout,
                contentPadding = remember(contentPadding) {
                    contentPadding.toPaddingValues()
                },
                verticalArrangement = verticalArrangement.toVerticalArrangement(),
                horizontalAlignment = horizontalAlignment.toAlignment(),
                userScrollEnabled = userScrollEnabled,
                content = content
            )
        } else {
            LazyRow(
                modifier = modifier,
                state = state,
                reverseLayout = reverseLayout,
                contentPadding = remember(contentPadding) {
                    contentPadding.toPaddingValues()
                },
                horizontalArrangement = horizontalArrangement.toHorizontalArrangement(),
                verticalAlignment = verticalAlignment.toAlignment(),
                userScrollEnabled = userScrollEnabled,
                content = content
            )
        }
    }

    override fun visibleItemsChanged(
        visibleItemsChanged: (firstIndex: Int, lastIndex: Int, changeId: Int) -> Unit,
    ) {
        throttler.send = visibleItemsChanged
    }

    override fun lastReceivedVisibleItemsChangedId(lastReceivedVisibleItemsChangedId: Int) {
        throttler.receivedVisibleItemsChangedId(lastReceivedVisibleItemsChangedId)
    }

    override fun boundScrollProgress(boundScrollProgress: ScrollProgress?) {
        this.boundScrollProgress = boundScrollProgress
    }

    override fun programmaticScrollRequest(programmaticScrollRequest: ScrollRequest?) {
        this.programmaticScrollRequest = programmaticScrollRequest
    }

    override fun contentPadding(contentPadding: Margin?) {
        this.contentPadding = contentPadding ?: PaddingValues()
    }

    override fun reverseLayout(reverseLayout: Boolean) {
        this.reverseLayout = reverseLayout
    }

    override fun horizontalArrangement(horizontalArrangement: RedwoodArrangement.Horizontal) {
        this.horizontalArrangement = horizontalArrangement
    }

    override fun verticalArrangement(verticalArrangement: RedwoodArrangement.Vertical) {
        this.verticalArrangement = verticalArrangement
    }

    override fun horizontalAlignment(horizontalAlignment: RedwoodAlignment.Horizontal) {
        this.horizontalAlignment = horizontalAlignment
    }

    override fun verticalAlignment(verticalAlignment: RedwoodAlignment.Vertical) {
        this.verticalAlignment = verticalAlignment
    }

    override fun userScrollEnabled(userScrollEnabled: Boolean) {
        this.userScrollEnabled = userScrollEnabled
    }
}
