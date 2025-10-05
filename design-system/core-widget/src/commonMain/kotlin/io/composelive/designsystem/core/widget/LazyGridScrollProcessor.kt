package io.composelive.designsystem.core.widget

import io.composelive.designsystem.core.api.lazygrid.ScrollItemIndex

public abstract class LazyGridScrollProcessor {
    /** To notify guest code of user scrolls. */
    private var onViewportChanged: ((Int, Int) -> Unit)? = null

    /** We can't scroll to this index until we have enough data for it to display! */
    private var deferredProgrammaticScrollIndex: Int = -1
    private var deferredProgrammaticScrollAnimated: Boolean = false

    /** De-duplicate calls to [onViewportChanged]. */
    private var mostRecentFirstIndex = -1
    private var mostRecentLastIndex = -1

    public fun onViewportChanged(onViewportChanged: (Int, Int) -> Unit) {
        this.onViewportChanged = onViewportChanged
    }

    public fun scrollItemIndex(scrollItemIndex: ScrollItemIndex) {
        // Defer until we have data in onEndChanges().
        deferredProgrammaticScrollIndex = scrollItemIndex.index
        deferredProgrammaticScrollAnimated = scrollItemIndex.animated
    }

    public fun onEndChanges() {
        // Do nothing: we don't have deferred scrolls.
        if (deferredProgrammaticScrollIndex == -1) return

        // Do nothing: we can't scroll to this item because it hasn't loaded yet!
        if (contentSize() <= deferredProgrammaticScrollIndex) return

        // Do a programmatic scroll!
        programmaticScroll(deferredProgrammaticScrollIndex, deferredProgrammaticScrollAnimated)
        deferredProgrammaticScrollIndex = -1
        deferredProgrammaticScrollAnimated = false
    }

    /**
     * React to a user-initiated scroll. Callers should not call this function for programmatic
     * scrolls.
     */
    public fun onUserScroll(firstIndex: Int, lastIndex: Int) {
        if (firstIndex == mostRecentFirstIndex && lastIndex == mostRecentLastIndex) return

        this.mostRecentFirstIndex = firstIndex
        this.mostRecentLastIndex = lastIndex

        onViewportChanged?.invoke(firstIndex, lastIndex)
    }

    /**
     * Reacts to a scroll-to-top being initiated.
     */
    public fun onScrollToTop() {
        if (this.mostRecentFirstIndex == 0) return // Already at the top.
        if (this.mostRecentLastIndex == -1) return // Never scrolled. Ignore.

        // This assumes that (mostRecentLastIndex - mostRecentFirstIndex + 1) rows
        // will be visible after the scroll-to-top completes.
        onUserScroll(0, mostRecentLastIndex - mostRecentFirstIndex)
    }

    /** Returns the number of items we're scrolling over. */
    public abstract fun contentSize(): Int

    /** Perform a programmatic scroll. */
    public abstract fun programmaticScroll(firstIndex: Int, animated: Boolean)
}
