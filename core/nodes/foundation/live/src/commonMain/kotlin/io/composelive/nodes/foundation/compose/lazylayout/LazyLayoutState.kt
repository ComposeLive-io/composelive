package io.composelive.nodes.foundation.compose.lazylayout

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import io.composelive.nodes.foundation.common.lazylayout.ScrollRequest
import io.composelive.nodes.foundation.compose.lazylayout.strategy.LoadingStrategy

public open class LazyLayoutState(
    public val strategy: LoadingStrategy,
) {
    public fun loadRanges(
        intervals: IntervalList<LazyLayoutIntervalContent.Interval>,
        writeFirstsTo: IntArray,
        writeLastsTo: IntArray,
    ) {
        strategy.loadRanges(intervals, writeFirstsTo, writeLastsTo)
    }

    /** React to a user-initiated scroll. */
    public open fun onUserScroll(firstIndex: Int, lastIndex: Int) {
        strategy.onUserScroll(firstIndex, lastIndex)
    }
}

public open class ProgrammaticScrollableLazyLayoutState(
    strategy: LoadingStrategy,
) : LazyLayoutState(strategy) {
    /**
     * Update this to trigger a programmatic scroll. This may be updated multiple times, including
     * when the previous scroll state is restored.
     */
    public var programmaticScrollIndex: ScrollRequest? by mutableStateOf(null)
        private set

    /** Once we receive a user scroll, we limit which programmatic scrolls we apply. */
    private var userScrolled = false

    /** Perform a programmatic scroll. */
    public fun programmaticScroll(
        firstIndex: Int,
        animated: Boolean,
        clobberUserScroll: Boolean = true,
    ) {
        require(firstIndex >= 0)
        if (!clobberUserScroll && userScrolled) return

        strategy.scrollTo(firstIndex)

        val previous = programmaticScrollIndex
        this.programmaticScrollIndex = ScrollRequest(
            id = (previous?.id ?: 0) + 1,
            index = firstIndex,
            animated = animated,
        )
    }

    /** React to a user-initiated scroll. */
    override fun onUserScroll(firstIndex: Int, lastIndex: Int) {
        if (firstIndex > 0) {
            userScrolled = true
        }

        super.onUserScroll(firstIndex, lastIndex)
    }
}
