package io.clive.core

import io.clive.logger.CliveLogger
import io.clive.logger.ScopedCliveLogger
import io.clive.manifest.CliveManifestRepository
import io.clive.module.CliveModuleRepository
import io.clive.treehouse.CliveTreehouseFactory
import kotlinx.coroutines.CoroutineScope

internal class CliveFactory(
    private val uiScope: CoroutineScope,
    private val manifestRepository: CliveManifestRepository,
    private val moduleRepository: CliveModuleRepository,
    private val treehouseFactory: CliveTreehouseFactory,
    private val logger: CliveLogger,
) {
    fun create(version: Int): Clive = Clive(
        version = version,
        uiScope = uiScope,
        manifestRepository = manifestRepository,
        moduleRepository = moduleRepository,
        treehouseFactory = treehouseFactory,
        logger = ScopedCliveLogger("Clive(v${version})", logger)
    )
}
