package io.clive.standard.host.composeui

import android.content.Context
import app.cash.redwood.leaks.RedwoodLeakApi
import app.cash.redwood.treehouse.TreehouseAppFactory
import app.cash.zipline.loader.ManifestVerifier
import app.cash.zipline.loader.asZiplineHttpClient
import io.clive.configuration.CliveConfiguration
import io.clive.configuration.CliveUiConfiguration
import io.clive.logger.CliveLogger
import io.clive.ui.CliveScreenFactory
import io.clive.util.CliveResources
import io.composelive.reuse.treehouse.HostZiplineBridge
import io.composelive.standard.protocol.host.StandardHostProtocol
import kotlinx.coroutines.CoroutineScope
import okhttp3.OkHttpClient

public fun createStandardCliveScreenFactory(
    context: Context,
    uiScope: CoroutineScope,
    configuration: CliveConfiguration,
    uiConfiguration: CliveUiConfiguration,
    httpClient: OkHttpClient,
    logger: CliveLogger,
    hostZiplineBridge: HostZiplineBridge,
): CliveScreenFactory = CliveScreenFactory(
    uiScope = uiScope,
    configuration = configuration,
    uiConfiguration = uiConfiguration,
    httpClient = httpClient.asZiplineHttpClient(),
    logger = logger,
    hostZiplineBridge = hostZiplineBridge,
    widgetSystem = ComposeUiStandardWidgetSystem(),
    createTreehouseFactory = { factoryHttpClient ->
        @OptIn(RedwoodLeakApi::class)
        TreehouseAppFactory(
            context = context,
            httpClient = factoryHttpClient,
            manifestVerifier = ManifestVerifier.NO_SIGNATURE_CHECKS,
            embeddedFileSystem = null,
            embeddedDir = null,
            hostProtocolFactory = StandardHostProtocol,
        )
    },
    resources = CliveResources(context),
)
