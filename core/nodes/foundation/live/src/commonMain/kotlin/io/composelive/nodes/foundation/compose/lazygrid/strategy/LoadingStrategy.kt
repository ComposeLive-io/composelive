package io.composelive.nodes.foundation.compose.lazygrid.strategy

import io.composelive.nodes.foundation.compose.lazygrid.LazyListInterval
import io.composelive.nodes.foundation.compose.lazygrid.layout.IntervalList

public interface LoadingStrategy {
    /**
     * Returns the index of the first item that is visible on screen. The item may be partially
     * visible.
     *
     * This is used to save the scroll position when the view is unloaded.
     *
     * This may temporarily be larger than the total number of items in the model. This will occur if
     * the number of items in the model shrinks.
     */
    public val firstVisibleIndex: Int

    /**
     * Returns the index of the last item that is visible on screen. The item may be partially
     * visible.
     *
     * This may temporarily be larger than the total number of items in the model. This will occur if
     * the number of items in the model shrinks.
     */
    public val lastVisibleIndex: Int

    /** Perform a programmatic scroll to [firstVisibleIndex]. */
    public fun scrollTo(firstVisibleIndex: Int)

    /** React to a user-initiated scroll to the target range. */
    public fun onUserScroll(firstVisibleIndex: Int, lastVisibleIndex: Int)

    /**
     * Returns the range of items to render into the view tree. This should be a slice of
     * `0..(itemCount - 1)`. It should cover the most-recently scrolled to `firstIndex..lastIndex`
     * range, plus any adjacent indexes to preload.
     */
    public fun loadRanges(
        intervals: IntervalList<LazyListInterval>,
        writeFirstsTo: IntArray,
        writeLastsTo: IntArray,
    )

    public fun dataUpdated() {}
}