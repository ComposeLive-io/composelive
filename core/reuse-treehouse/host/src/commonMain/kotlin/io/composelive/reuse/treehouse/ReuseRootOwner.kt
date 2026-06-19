package io.composelive.reuse.treehouse

import io.composelive.nodes.foundation.host.composeui.FoundationReuseRoot

public abstract class ReuseRootOwner {
    internal abstract fun reuseRootInserted(root: FoundationReuseRoot)
}
