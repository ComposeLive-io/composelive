package io.composelive.app.ios

import app.cash.redwood.leaks.RedwoodLeakApi
import app.cash.redwood.treehouse.TreehouseApp
import app.cash.redwood.treehouse.TreehouseAppFactory
import app.cash.zipline.loader.ManifestVerifier
import app.cash.zipline.loader.ZiplineHttpClient
import io.composelive.app.treehouse.leakDetector
import io.composelive.nodes.standard.protocol.host.StandardHostProtocol
import kotlinx.coroutines.CoroutineScope

@OptIn(RedwoodLeakApi::class)
internal fun createTreehouseAppFactory(
    httpClient: ZiplineHttpClient,
    scope: CoroutineScope,
): TreehouseApp.Factory {
    return TreehouseAppFactory(
        httpClient = httpClient,
        manifestVerifier = ManifestVerifier.NO_SIGNATURE_CHECKS,
        leakDetector = leakDetector(scope),
        hostProtocolFactory = StandardHostProtocol,
    )
}
