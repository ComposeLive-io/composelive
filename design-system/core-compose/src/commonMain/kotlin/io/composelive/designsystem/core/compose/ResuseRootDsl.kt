package io.composelive.designsystem.core.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import kotlinx.serialization.json.JsonElement

@Composable
public fun ReuseRoot(content: @Composable (type: String, payload: JsonElement?) -> Unit) {
    val nodes = remember { mutableStateMapOf<String, Node>() }
    ReuseRoot(
        addNode = { reuseId, type, payload ->
            nodes[reuseId] = Node(type, payload)
        },
        removeNode = { reuseId ->
            nodes.remove(reuseId)
        }
    ) {
        nodes.forEach { (reuseId, node) ->
            ReuseNode(reuseId) {
                content(node.type, node.payload)
            }
        }
    }
}

private data class Node(
    val type: String,
    val payload: JsonElement?,
)
