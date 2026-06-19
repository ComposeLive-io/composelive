package io.composelive.reuse.treehouse

import io.composelive.nodes.foundation.common.LayoutMetadata
import io.composelive.nodes.foundation.host.composeui.FoundationReuseRoot
import io.composelive.nodes.foundation.host.composeui.ReuseNodeContent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first

public class ReuseRootHolder : ReuseRootOwner() {
    private val root = MutableStateFlow<FoundationReuseRoot?>(null)

    public suspend fun load(
        id: String,
        metadata: LayoutMetadata,
        payload: String? = null,
    ): StateFlow<ReuseNodeContent?> { // StateFlow is required here to prevent glitches
        val root = root.first { it != null }!!
        root.addNode(id, metadata, payload)
        val nodeChildren = root.content.widgetsById.first { id in it }[id]!!.content
        return nodeChildren.content
    }

    public fun free(id: String) {
        root.value?.removeNode(id)
    }

    /**
     * If error happened in treehouse/zipline - we must manually reset root.
     */
    public fun reset() {
        root.value = null
    }

    override fun reuseRootInserted(root: FoundationReuseRoot) {
        this.root.value = root
    }
}
