package io.clive.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.cash.redwood.treehouse.TreehouseApp
import app.cash.redwood.widget.WidgetSystem
import app.cash.zipline.loader.ZiplineHttpClient
import io.clive.configuration.CliveConfiguration
import io.clive.configuration.CliveConfigurationInternal
import io.clive.configuration.CliveUiConfiguration
import io.clive.core.CliveFactory
import io.clive.core.CliveRepository
import io.clive.logger.CliveLogger
import io.clive.manifest.CliveManifestRepository
import io.clive.module.CliveModuleRepository
import io.clive.treehouse.CliveTreehouseFactory
import io.clive.treehouse.delegatesTo
import io.clive.util.CliveResources
import io.composelive.reuse.treehouse.HostZiplineBridge
import kotlinx.coroutines.CoroutineScope

public class CliveScreenFactory(
    private val uiScope: CoroutineScope,
    configuration: CliveConfiguration,
    uiConfiguration: CliveUiConfiguration,
    httpClient: ZiplineHttpClient,
    widgetSystem: WidgetSystem<@Composable (Modifier) -> Unit>,
    private val logger: CliveLogger,
    hostZiplineBridge: HostZiplineBridge,
    createTreehouseFactory: (ZiplineHttpClient) -> TreehouseApp.Factory,
    resources: CliveResources?,
) {
    private val configurationInternal = CliveConfigurationInternal(configuration)
    private val manifestRepository = CliveManifestRepository(uiScope, configurationInternal, httpClient, logger, resources)
    private val moduleRepository = CliveModuleRepository(uiScope, configurationInternal, httpClient, logger, resources)

    private val cliveTreehouseFactory = CliveTreehouseFactory(
        uiScope,
        createTreehouseFactory = { version ->
            createTreehouseFactory(
                httpClient.delegatesTo(version, manifestRepository, moduleRepository)
            )
        },
        widgetSystem,
        configurationInternal,
        uiConfiguration,
        hostZiplineBridge,
        logger,
    )
    private val cliveFactory = CliveFactory(
        uiScope = uiScope,
        manifestRepository = manifestRepository,
        moduleRepository = moduleRepository,
        treehouseFactory = cliveTreehouseFactory,
        logger = logger,
    )
    private val cliveRepository = CliveRepository(cliveFactory, logger)

    public fun create(name: String): CliveScreen = CliveScreen(
        name = name,
        uiScope = uiScope,
        repository = cliveRepository,
    )
}
