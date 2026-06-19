package io.clive.test.composeui

import androidx.compose.runtime.Composable
import app.cash.redwood.leaks.RedwoodLeakApi
import app.cash.redwood.protocol.host.HostProtocol
import app.cash.redwood.treehouse.TreehouseApp
import app.cash.redwood.treehouse.TreehouseAppFactory
import app.cash.zipline.loader.ManifestVerifier
import app.cash.zipline.loader.ZiplineHttpClient
import okio.Path.Companion.toPath

@Composable
public actual fun rememberCreateTreehouseAppFactory(
    hostProtocolFactory: HostProtocol.Factory,
): (ZiplineHttpClient) -> TreehouseApp.Factory {
    @OptIn(RedwoodLeakApi::class)
    return { httpClient ->
        TreehouseAppFactory(
            httpClient = httpClient,
            manifestVerifier = ManifestVerifier.NO_SIGNATURE_CHECKS,
            embeddedDir = "/".toPath(),
            hostProtocolFactory = hostProtocolFactory,
        )
    }
}
