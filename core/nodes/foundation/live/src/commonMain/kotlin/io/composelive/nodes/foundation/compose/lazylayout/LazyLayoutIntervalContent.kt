package io.composelive.nodes.foundation.compose.lazylayout

import androidx.compose.runtime.Composable

/**
 * Common parts backing the interval-based content of lazy layout defined through `item` DSL.
 */
public abstract class LazyLayoutIntervalContent<Interval : LazyLayoutIntervalContent.Interval> {
    public abstract val intervals: IntervalList<Interval>

    /**
     * The total amount of items in all the intervals.
     */
    public val itemCount: Int get() = intervals.size

    /**
     * Runs a [block] on the content of the interval associated with the provided [globalIndex]
     * with providing a local index in the given interval.
     */
    public inline fun <T> withInterval(
        globalIndex: Int,
        block: (localIntervalIndex: Int, content: Interval) -> T
    ): T {
        val interval = intervals[globalIndex]
        val localIntervalIndex = globalIndex - interval.startIndex
        return block(localIntervalIndex, interval.value)
    }

    /**
     * Common content of individual intervals in `item` DSL of lazy layouts.
     */
    public interface Interval {
        /**
         * The purpose of [placeholder] is to define the temporary content of an on-screen item while the
         * content of that item (as described by the content block) is being retrieved. When the content
         * of that item has been retrieved, the [placeholder] is replaced with that of the content.
         */
        public val placeholder: @Composable () -> Unit
        public val item: @Composable (index: Int) -> Unit
    }
}
