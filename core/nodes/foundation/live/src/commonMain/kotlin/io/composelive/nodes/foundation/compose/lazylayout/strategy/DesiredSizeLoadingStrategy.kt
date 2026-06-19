package io.composelive.nodes.foundation.compose.lazylayout.strategy

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import io.composelive.nodes.foundation.common.lazylayout.ScrollRequest
import io.composelive.nodes.foundation.compose.lazylayout.IntervalList
import io.composelive.nodes.foundation.compose.lazylayout.LazyLayoutIntervalContent

private const val DEFAULT_DESIRED_SIZE = 20

private const val DEFAULT_SCROLL_INDEX = -1

/**
 * A loading strategy that preloads items above and below the visible range.
 *
 * When scrolling, this loads more items in the direction the user is scrolling to.
 *
 * The size of the loading window is kept small while scrolling. It grows when scrolling stops.
 *
 * This will retain already-loaded items that it wouldn't load otherwise.
 */
public class StaticSizeLoadingStrategy(
    private val desiredSize: Int = DEFAULT_DESIRED_SIZE,
) : LoadingStrategy {

    private var firstIndexFromPrevious1: Int by mutableIntStateOf(DEFAULT_SCROLL_INDEX)

    /**
     * Update this to trigger a programmatic scroll. This may be updated multiple times, including
     * when the previous scroll state is restored.
     */
    public var programmaticScrollIndex: ScrollRequest by mutableStateOf(
        ScrollRequest(id = 0, index = 0, animated = false),
    )
        private set

    /** Bounds of what the user is looking at. Everything else is placeholders! */
    public override var firstVisibleIndex: Int by mutableIntStateOf(0)
        private set
    public override var lastVisibleIndex: Int by mutableIntStateOf(0)
        private set

    override fun scrollTo(firstVisibleIndex: Int) {
        require(firstVisibleIndex >= 0)

        val delta = (lastVisibleIndex - this.firstVisibleIndex)
        this.firstVisibleIndex = firstVisibleIndex
        this.lastVisibleIndex = firstVisibleIndex + delta
    }

    override fun onUserScroll(firstVisibleIndex: Int, lastVisibleIndex: Int) {
        this.firstVisibleIndex = firstVisibleIndex
        this.lastVisibleIndex = lastVisibleIndex
    }

    public override fun loadRanges(
        intervals: IntervalList<LazyLayoutIntervalContent.Interval>,
        writeFirstsTo: IntArray,
        writeLastsTo: IntArray,
    ) {
        val isScrollingDown = firstIndexFromPrevious1 != DEFAULT_SCROLL_INDEX &&
                firstIndexFromPrevious1 < firstVisibleIndex
        val isScrollingUp = firstIndexFromPrevious1 != DEFAULT_SCROLL_INDEX &&
                firstIndexFromPrevious1 > firstVisibleIndex

        var localFirstVisibleIndex = firstVisibleIndex
        var localLastVisibleIndex = lastVisibleIndex
        for (intervalIndex in 0 until intervals.intervalsCount) {
            val interval = intervals.getInterval(intervalIndex)
            val itemCount = interval.size

            val middle = localFirstVisibleIndex +
                    ((localLastVisibleIndex - localFirstVisibleIndex) / 2)

            var remainedItems = desiredSize - 1
            val upOffset = when {
                isScrollingDown -> remainedItems / 4
                isScrollingUp -> (remainedItems / 4) * 3
                else -> remainedItems / 2
            }

            var begin: Int
            if (middle - upOffset < 0) {
                remainedItems -= middle
                begin = 0
            } else {
                remainedItems -= upOffset
                begin = middle - upOffset
            }

            var end: Int
            if (middle + remainedItems >= itemCount) {
                val diff = (middle + remainedItems) - itemCount
                end = itemCount - 1
                begin = (begin - diff).coerceAtLeast(0)
            } else {
                end = middle + remainedItems
            }

            writeFirstsTo[intervalIndex] = begin
            writeLastsTo[intervalIndex] = end

            localLastVisibleIndex = (localLastVisibleIndex - itemCount).coerceAtLeast(0)
            localFirstVisibleIndex = (localFirstVisibleIndex - itemCount).coerceAtLeast(0)
        }

        this.firstIndexFromPrevious1 = firstVisibleIndex
    }
}
