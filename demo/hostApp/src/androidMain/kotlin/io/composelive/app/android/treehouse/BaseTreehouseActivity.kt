package io.composelive.app.android.treehouse

import app.cash.redwood.leaks.RedwoodLeakApi
import app.cash.redwood.treehouse.TreehouseApp
import app.cash.redwood.treehouse.TreehouseAppFactory
import app.cash.zipline.loader.ManifestVerifier
import app.cash.zipline.loader.ZiplineHttpClient
import io.composelive.app.android.BaseActivity
import io.composelive.app.treehouse.leakDetector
import io.composelive.nodes.standard.protocol.host.StandardHostProtocol
import okio.Path.Companion.toPath

abstract class BaseTreehouseActivity : BaseActivity() {

    @OptIn(RedwoodLeakApi::class)
    protected fun createTreehouseAppFactory(httpClient: ZiplineHttpClient): TreehouseApp.Factory {
        return TreehouseAppFactory(
            context = applicationContext,
            httpClient = httpClient,
            manifestVerifier = ManifestVerifier.NO_SIGNATURE_CHECKS,
            embeddedDir = "/".toPath(),
            leakDetector = leakDetector(scope),
            hostProtocolFactory = StandardHostProtocol,
        )
    }
}
