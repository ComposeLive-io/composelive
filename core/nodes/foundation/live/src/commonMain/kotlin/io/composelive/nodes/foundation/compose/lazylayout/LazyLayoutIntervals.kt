package io.composelive.nodes.foundation.compose.lazylayout

import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import app.cash.redwood.Modifier
import io.composelive.nodes.foundation.compose.ShallowWrapper
import io.composelive.nodes.foundation.compose.reuse

/**
 * A helper function for creating lazy containers in the Clive framework.
 *
 * This function manages the rendering of intervals in a lazy layout by maintaining
 * a "window" of preloaded elements. The preloading window is essential because:
 *
 * 1. The bridge between live code and host code takes significant time to communicate.
 * Preloading elements minimizes placeholder display during host rendering
 *
 * 2. The window has a static size to maximize reuse of the live composition
 * Static sizing prevents unnecessary transfer of changes through the bridge.
 *
 * The function iterates through all intervals provided by the [itemProvider] and:
 *
 * 1. Calculates the load range (visible + preloaded items) for each interval
 * 2. Manages key recycling to enable efficient item composition reuse
 * 3. Wraps each item in a [ShallowWrapper] with reuse modifier for optimal composition
 *
 * [TInterval] The type of interval content, must extend [LazyLayoutIntervalContent.Interval]
 * [state] The lazy layout state that tracks scroll position and calculates load ranges
 * [itemProvider] Provides the intervals and their content for the lazy layout
 * [items] A composable lambda that renders an interval with the following parameters:
 *
 * [items]: A composable that renders the actual items within the load range
 * [items].placeholder: A composable that renders a placeholder for items outside the load range
 * [items].interval: The interval content containing items to render
 * [items].itemsBefore: Number of items before the rendered range (for placeholder spacing)
 * [items].itemsAfter: Number of items after the rendered range (for placeholder spacing)
 */
@Composable
public fun <TInterval : LazyLayoutIntervalContent.Interval> LazyLayoutIntervals(
    state: LazyLayoutState,
    itemProvider: LazyLayoutItemProvider<TInterval>,
    items: @Composable (
        interval: TInterval,
        itemsBefore: Int,
        itemsAfter: Int,
        placeholder: @Composable () -> Unit,
        items: @Composable () -> Unit,
    ) -> Unit
) {
    val intervals = itemProvider.intervals
    val intervalsCount = intervals.intervalsCount
    val loadRangeFirsts = remember(intervalsCount) { IntArray(intervalsCount) }
    val loadRangeLasts = remember(intervalsCount) { IntArray(intervalsCount) }
    state.loadRanges(
        intervals,
        writeFirstsTo = loadRangeFirsts,
        writeLastsTo = loadRangeLasts,
    )
    for (intervalIndex in 0 until intervalsCount) {
        val interval = intervals.getInterval(intervalIndex)
        val loadRangeFirst = loadRangeFirsts[intervalIndex]
        val loadRangeLast = loadRangeLasts[intervalIndex]
        val loadRangeSize = (loadRangeLast - loadRangeFirst) + 1
        val keyRecycler = remember(intervalIndex) { LazyLayoutKeyRecycler() }
        key(intervalIndex) {
            items(
                interval.value,
                loadRangeFirst,
                (interval.size - loadRangeSize - loadRangeFirst)
                    .coerceIn(0, interval.size),
                interval.value.placeholder
            ) {
                keyRecycler.loadRangeUpdated(loadRangeFirst, loadRangeLast)
                for (virtualIndex in loadRangeFirst..loadRangeLast) {
                    val key = keyRecycler.keyFor(virtualIndex)
                    key(key) {
                        ShallowWrapper(modifier = Modifier.reuse()) {
                            interval.value.item(virtualIndex)
                        }
                    }
                }
            }
        }
    }
}
