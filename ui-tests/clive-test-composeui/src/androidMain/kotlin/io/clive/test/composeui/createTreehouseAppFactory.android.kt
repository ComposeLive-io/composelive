package io.clive.test.composeui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import app.cash.redwood.leaks.RedwoodLeakApi
import app.cash.redwood.protocol.host.HostProtocol
import app.cash.redwood.treehouse.TreehouseApp
import app.cash.redwood.treehouse.TreehouseAppFactory
import app.cash.zipline.loader.ManifestVerifier
import app.cash.zipline.loader.ZiplineHttpClient
import okio.Path.Companion.toPath
import okio.assetfilesystem.asFileSystem

@Composable
public actual fun rememberCreateTreehouseAppFactory(
    hostProtocolFactory: HostProtocol.Factory,
): (ZiplineHttpClient) -> TreehouseApp.Factory {
    val context = LocalContext.current
    return { httpClient ->
        @OptIn(RedwoodLeakApi::class)
        TreehouseAppFactory(
            context = context,
            httpClient = httpClient,
            manifestVerifier = ManifestVerifier.NO_SIGNATURE_CHECKS,
            embeddedFileSystem = context.assets.asFileSystem(),
            embeddedDir = "/".toPath(),
            hostProtocolFactory = hostProtocolFactory,
        )
    }
}
