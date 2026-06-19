package io.composelive.nodes.foundation.compose

import androidx.compose.runtime.Composable
import app.cash.redwood.LayoutScopeMarker
import app.cash.redwood.Modifier
import io.composelive.nodes.foundation.common.Alignment
import io.composelive.nodes.foundation.common.Arrangement
import io.composelive.nodes.foundation.common.PaddingValues
import io.composelive.nodes.foundation.common.ScrollProgress
import io.composelive.nodes.foundation.compose.lazylayout.list.LazyList

/**
 * Receiver scope which is used by [LazyColumn] and [LazyRow].
 */
@LayoutScopeMarker
public interface LazyListScope {
    /**
     * Adds a single item.
     *
     * @param content The content of the item.
     */
    public fun item(
        content: @Composable () -> Unit,
    )

    /**
     * Adds a [count] of items.
     *
     * @param count The items count.
     * @param itemContent The content displayed by a single item.
     */
    public fun items(
        count: Int,
        placeholder: @Composable () -> Unit = {},
        itemContent: @Composable (index: Int) -> Unit,
    )
}

/**
 * Adds a list of items.
 *
 * @param items The data list.
 * @param itemContent The content displayed by a single item.
 */
public inline fun <T> LazyListScope.items(
    items: List<T>,
    noinline placeholder: @Composable () -> Unit = {},
    crossinline itemContent: @Composable (item: T) -> Unit,
): Unit = items(
    items.size,
    placeholder = placeholder,
    itemContent = {
        itemContent(items[it])
    }
)

/**
 * Adds a list of items where the content of an item is aware of its index.
 *
 * @param items The data list.
 * @param itemContent The content displayed by a single item.
 */
public inline fun <T> LazyListScope.itemsIndexed(
    items: List<T>,
    noinline placeholder: @Composable () -> Unit = {},
    crossinline itemContent: @Composable (index: Int, item: T) -> Unit,
): Unit = items(
    items.size,
    placeholder = placeholder,
    itemContent = {
        itemContent(it, items[it])
    },
)

/**
 * Adds an array of items.
 *
 * @param items The data array.
 * @param itemContent The content displayed by a single item.
 */
public inline fun <T> LazyListScope.items(
    items: Array<T>,
    noinline placeholder: @Composable () -> Unit = {},
    crossinline itemContent: @Composable (item: T) -> Unit,
): Unit = items(
    items.size,
    placeholder = placeholder,
    itemContent = {
        itemContent(items[it])
    },
)

/**
 * Adds an array of items where the content of an item is aware of its index.
 *
 * @param items The data array.
 * @param itemContent The content displayed by a single item.
 */
public inline fun <T> LazyListScope.itemsIndexed(
    items: Array<T>,
    noinline placeholder: @Composable () -> Unit = {},
    crossinline itemContent: @Composable (index: Int, item: T) -> Unit,
): Unit = items(
    items.size,
    placeholder = placeholder,
    itemContent = {
        itemContent(it, items[it])
    },
)

/**
 * The horizontally scrolling list that only composes and lays out the currently visible items.
 * The [content] block defines a DSL which allows you to emit items of different types. For
 * example, you can use [LazyListScope.item] to add a single item and [LazyListScope.items] to add
 * a list of items.
 *
 * @param state The state object to be used to control or observe the list's state.
 * @param modifier The modifier to apply to this layout.
 * @param content A block which describes the content. Inside this block you can use methods like
 * [LazyListScope.item] to add a single item or [LazyListScope.items] to add a list of items.
 */
@Composable
public fun LazyRow(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues? = null,
    reverseLayout: Boolean = false,
    horizontalArrangement: Arrangement.Horizontal = if (!reverseLayout) Arrangement.Start else Arrangement.End,
    verticalAlignment: Alignment.Vertical = Alignment.Top,
    boundScrollProgress: ScrollProgress? = null,
    content: LazyListScope.() -> Unit,
) {
    LazyList(
        isVertical = false,
        state = state,
        contentPadding = contentPadding,
        reverseLayout = reverseLayout,
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = verticalAlignment,
        boundScrollProgress = boundScrollProgress,
        modifier = modifier,
        content = content,
    )
}

/**
 * The vertically scrolling list that only composes and lays out the currently visible items.
 * The [content] block defines a DSL which allows you to emit items of different types. For
 * example, you can use [LazyListScope.item] to add a single item and [LazyListScope.items] to add
 * a list of items.
 *
 * @param state The state object to be used to control or observe the list's state.
 * @param modifier The modifier to apply to this layout.
 * @param content A block which describes the content. Inside this block you can use methods like
 * [LazyListScope.item] to add a single item or [LazyListScope.items] to add a list of items.
 */
@Composable
public fun LazyColumn(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues? = null,
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical =
        if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    boundScrollProgress: ScrollProgress? = null,
    content: LazyListScope.() -> Unit,
) {
    LazyList(
        isVertical = true,
        state = state,
        contentPadding = contentPadding,
        reverseLayout = reverseLayout,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        boundScrollProgress = boundScrollProgress,
        modifier = modifier,
        content = content,
    )
}
