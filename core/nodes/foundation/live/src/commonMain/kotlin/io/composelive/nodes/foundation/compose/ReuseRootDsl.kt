package io.composelive.nodes.foundation.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import app.cash.redwood.compose.LocalUiConfiguration
import app.cash.redwood.ui.Size
import io.composelive.nodes.foundation.common.LayoutMetadata

@Composable
public fun ReuseRoot(
    content: @Composable (
        metadata: LayoutMetadata,
        payload: String?,
    ) -> Unit,
) {
    val uiConfiguration = LocalUiConfiguration.current
    val nodes = remember { mutableStateMapOf<String, Pair<LayoutMetadata, String?>>() }
    ReuseRoot(
        addNode = { id, metadata, payload ->
            nodes[id] = metadata to payload
        },
        removeNode = { id ->
            nodes.remove(id)
        }
    ) {
        nodes.forEach { node ->
            val id = node.key
            val (metadata, payload) = node.value
            key(id) {
                var size by remember { mutableStateOf<Size?>(null) }
                ReuseNode(
                    instanceId = id,
                    viewSizeChanged = { size = it },
                ) {
                    CompositionLocalProvider(
                        LocalUiConfiguration provides remember(uiConfiguration, size) {
                            if (size != null)
                                uiConfiguration.copy(viewportSize = size)
                            else
                                uiConfiguration
                        }
                    ) {
                        content(metadata, payload)
                    }
                }
            }
        }
    }
}
