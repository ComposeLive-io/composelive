package io.composelive.nodes.foundation.compose

import androidx.compose.runtime.Composable
import app.cash.redwood.LayoutScopeMarker
import app.cash.redwood.Modifier
import io.composelive.nodes.foundation.common.Arrangement
import io.composelive.nodes.foundation.common.ScrollProgress
import io.composelive.nodes.foundation.common.lazylayout.grid.GridItemSpan
import io.composelive.nodes.foundation.compose.lazylayout.grid.LazyGrid

/**
 * Receiver scope which is used by [LazyVerticalGrid] and [LazyHorizontalGrid].
 */
@LayoutScopeMarker
public interface LazyGridScope {
    /**
     * Adds a single item.
     *
     * @param content The content of the item.
     */
    public fun item(
        span: () -> GridItemSpan? = { null },
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
public inline fun <T> LazyGridScope.items(
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
public inline fun <T> LazyGridScope.itemsIndexed(
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
public inline fun <T> LazyGridScope.items(
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
public inline fun <T> LazyGridScope.itemsIndexed(
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
 * example, you can use [LazyGridScope.item] to add a single item and [LazyGridScope.items] to add
 * a list of items.
 *
 * @param state The state object to be used to control or observe the list's state.
 * @param modifier The modifier to apply to this layout.
 * @param content A block which describes the content. Inside this block you can use methods like
 * [LazyGridScope.item] to add a single item or [LazyGridScope.items] to add a list of items.
 */
@Composable
public fun LazyHorizontalGrid(
    modifier: Modifier = Modifier,
    state: LazyGridState = rememberLazyGridState(),
    rows: Int = 1,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    boundScrollProgress: ScrollProgress? = null,
    content: LazyGridScope.() -> Unit,
) {
    LazyGrid(
        isVertical = false,
        state = state,
        chunks = rows,
        horizontalArrangement = horizontalArrangement,
        verticalArrangement = verticalArrangement,
        boundScrollProgress = boundScrollProgress,
        modifier = modifier,
        content = content,
    )
}

/**
 * The vertically scrolling list that only composes and lays out the currently visible items.
 * The [content] block defines a DSL which allows you to emit items of different types. For
 * example, you can use [LazyGridScope.item] to add a single item and [LazyGridScope.items] to add
 * a list of items.
 *
 * @param state The state object to be used to control or observe the list's state.
 * @param modifier The modifier to apply to this layout.
 * @param content A block which describes the content. Inside this block you can use methods like
 * [LazyGridScope.item] to add a single item or [LazyGridScope.items] to add a list of items.
 */
@Composable
public fun LazyVerticalGrid(
    modifier: Modifier = Modifier,
    state: LazyGridState = rememberLazyGridState(),
    columns: Int = 1,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    boundScrollProgress: ScrollProgress? = null,
    content: LazyGridScope.() -> Unit,
) {
    LazyGrid(
        isVertical = true,
        state = state,
        chunks = columns,
        horizontalArrangement = horizontalArrangement,
        verticalArrangement = verticalArrangement,
        boundScrollProgress = boundScrollProgress,
        modifier = modifier,
        content = content,
    )
}
