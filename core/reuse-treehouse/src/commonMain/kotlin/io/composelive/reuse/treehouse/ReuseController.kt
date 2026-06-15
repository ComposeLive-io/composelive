package io.composelive.reuse.treehouse

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import io.composelive.nodes.foundation.host.composeui.FoundationReuseRoot
import kotlinx.serialization.json.JsonElement

public class ReuseController {
    private var root: FoundationReuseRoot? by mutableStateOf(null)
    private val nodesToAddAfterInit = mutableListOf<Node>()

    @Composable
    public fun Render(
        reuseId: String,
        type: String,
        payload: JsonElement? = null,
        modifier: Modifier = Modifier,
    ) {
        LaunchedEffect(Unit) {
            val root = root
            if (root != null) {
                root.addNode(reuseId, type, payload)
            } else {
                nodesToAddAfterInit.add(Node(reuseId, type, payload))
            }
        }
        val child = root?.content?.widgetsById[reuseId]
        if (child != null) {
            child.value(modifier)
        }
    }

    internal fun reuseRootInserted(root: FoundationReuseRoot) {
        nodesToAddAfterInit.forEach { node ->
            root.addNode(
                reuseId = node.reuseId,
                type = node.type,
                payload = node.payload,
            )
        }
        this.root = root
    }

    private data class Node(
        val reuseId: String,
        val type: String,
        val payload: JsonElement?,
    )
}
