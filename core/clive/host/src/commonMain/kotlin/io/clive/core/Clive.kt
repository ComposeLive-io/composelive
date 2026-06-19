package io.clive.core

import io.clive.logger.CliveLogger
import io.clive.logger.e
import io.clive.manifest.CliveManifestRepository
import io.clive.module.CliveModuleRepository
import io.clive.treehouse.CliveTreehouse
import io.clive.treehouse.CliveTreehouseFactory
import io.clive.treehouse.CliveZipline
import io.composelive.nodes.foundation.common.LayoutMetadata
import io.composelive.nodes.foundation.host.composeui.ReuseNodeContent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import kotlin.concurrent.Volatile

public typealias CliveLayoutContent = ReuseNodeContent

public class Clive internal constructor(
    internal val version: Int,
    private val uiScope: CoroutineScope,
    private val manifestRepository: CliveManifestRepository,
    private val moduleRepository: CliveModuleRepository,
    treehouseFactory: CliveTreehouseFactory,
    private val logger: CliveLogger,
) {
    private val zipline: MutableStateFlow<CliveZipline?> = MutableStateFlow(null)
    private val error: MutableStateFlow<Exception?> = MutableStateFlow(null)
    private val layoutRetriesFlow = MutableSharedFlow<Unit>(extraBufferCapacity = 1)

    private val treehouse: CliveTreehouse =
        treehouseFactory.create(version, logger) { message, exception ->
            logger.e(message, exception)
            if (error.value == null) {
                error.value = exception
            }
        }

    private val layoutManager = CliveLayoutManager(zipline, error, layoutRetriesFlow, logger)
    internal val servicesTasks = CliveServiceTaskManager(
        treehouse,
        zipline,
        uiScope,
        logger,
        isCliveFreed = { isFreed },
        onError = { error.value = it },
    )

    @Volatile
    private var isFreed = false

    @Volatile
    private var initialization = initialize()

    init {
        @OptIn(ExperimentalCoroutinesApi::class)
        uiScope.launch {
            val ziplineErrors = zipline.flatMapLatest {
                it?.error ?: emptyFlow()
            }.filterNotNull()

            ziplineErrors.collectLatest {
                logger.e(it)
                if (error.value == null) {
                    error.value = it
                }
                zipline.value = null
                layoutManager.reset()
            }
        }
    }

    private fun initialize() = uiScope.launch {
        logger.i("initialization - started")
        zipline.value = null
        error.value = null

        try {
            logger.i("initialization - stop old treehouse")
            treehouse.stop()

            error.value = null

            logger.i("initialization - load manifest")
            val manifest = manifestRepository.get(version)

            logger.i("initialization - bind new treehouse")
            layoutManager.bindTo(treehouse)

            logger.i("initialization - wait new zipline")
            val rawZipline = treehouse.zipline.first()

            zipline.value = CliveZipline(
                rawZipline,
                manifest,
                treehouse,
                moduleRepository,
                logger,
            )
            logger.i("initialization - finished")
        } catch (e: Exception) {
            logger.e("initialization - failed", e)
            error.value = e
            layoutManager.reset()
        }
    }

    internal fun reloadIfError() {
        if (isFreed) return
        if (!initialization.isCompleted) return

        if (error.value == null) {
            logger.i("full reload skipped, runtime exists; try to reload failed layouts")
            layoutRetriesFlow.tryEmit(Unit)
        } else {
            logger.i("will do full reload due to runtime failure with [${error.value?.message}]")
            initialization.cancel()
            initialization = initialize()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    public fun layoutFor(
        id: String,
        metadata: LayoutMetadata,
        payload: String? = null,
    ): Flow<LayoutState> = layoutManager.layoutFor(id, metadata, payload)

    internal fun freeLayout(id: String) {
        layoutManager.free(id)
    }

    public fun free() {
        isFreed = true
        initialization.cancel()
        uiScope.launch { treehouse.close() }
    }

    override fun toString(): String = "Clive(v$version)"

    public sealed interface LayoutState {
        public data object Loading : LayoutState
        public data class Error(val e: Exception) : LayoutState
        public class Success(public val content: CliveLayoutContent) : LayoutState
    }
}
