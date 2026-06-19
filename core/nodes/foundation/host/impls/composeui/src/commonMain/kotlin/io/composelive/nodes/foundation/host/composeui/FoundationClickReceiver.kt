package io.composelive.nodes.foundation.host.composeui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import io.composelive.nodes.foundation.host.composeui.clickable.requireClickActionsHolder

@Composable
public fun FoundationClickReceiver(id: Int, action: () -> Unit) {
    val receiversHolder = requireClickActionsHolder()
    DisposableEffect(id, action) {
        receiversHolder.registerAction(id, action)
        onDispose {
            receiversHolder.unregisterAction(id)
        }
    }
}
