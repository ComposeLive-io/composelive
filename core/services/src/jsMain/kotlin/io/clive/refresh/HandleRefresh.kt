package io.clive.refresh

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import io.clive.LocalLayoutMetadata
import io.clive.services.RefreshServiceImpl

@Composable
fun HandleRefresh(refreshed: () -> Unit) {
    val layoutMetadata = LocalLayoutMetadata.current
    DisposableEffect(Unit) {
        RefreshServiceImpl.register(layoutMetadata.screenKey, refreshed)
        onDispose {
            RefreshServiceImpl.unregister(layoutMetadata.screenKey, refreshed)
        }
    }
}
