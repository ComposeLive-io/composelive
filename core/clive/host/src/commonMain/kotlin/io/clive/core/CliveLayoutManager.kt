package io.clive.core

import io.clive.core.Clive.LayoutState
import io.clive.logger.CliveLogger
import io.clive.treehouse.CliveTreehouse
import io.clive.treehouse.CliveZipline
import io.clive.treehouse.CliveZipline.ModuleLoadResult.RecoverableError
import io.clive.treehouse.CliveZipline.ModuleLoadResult.Success
import io.clive.treehouse.CliveZipline.ModuleLoadResult.ZiplineCorruption
import io.composelive.nodes.foundation.common.LayoutMetadata
import io.composelive.reuse.treehouse.ReuseRootHolder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow


internal class CliveLayoutManager(
    private val zipline: MutableStateFlow<CliveZipline?>,
    private val error: MutableStateFlow<Exception?>,
    private val layoutRetriesFlow: MutableSharedFlow<Unit>,
    private val logger: CliveLogger
) {
    private val reuseRoot: ReuseRootHolder = ReuseRootHolder()

    fun bindTo(treehouse: CliveTreehouse) {
        treehouse.bind(reuseRoot)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun layoutFor(
        id: String,
        metadata: LayoutMetadata,
        payload: String?,
    ): Flow<LayoutState> = channelFlow {
        send(LayoutState.Loading)

        combine(zipline, error, ::Pair)
            .collectLatest { (zipline, error) ->
                if (error != null) {
                    send(LayoutState.Error(error))
                } else if (zipline == null) {
                    send(LayoutState.Loading)
                } else {
                    try {
                        zipline.loadModuleWithRetries(metadata.moduleId)
                            .collectLatest(::send)

                        val layoutContent = reuseRoot.load(id, metadata, payload)
                        layoutContent.collectLatest { content ->
                            send(
                                if (content != null) {
                                    LayoutState.Success(content)
                                } else {
                                    LayoutState.Error(NoCliveLayoutData(metadata))
                                }
                            )
                        }
                    } catch (e: Exception) {
                        send(LayoutState.Error(e))
                    }
                }
            }
    }

    private fun CliveZipline.loadModuleWithRetries(moduleId: String) = flow {
        while (true) {
            when (val result = loadModule(moduleId)) {
                is Success -> return@flow

                is ZiplineCorruption -> throw result.error

                is RecoverableError -> {
                    emit(LayoutState.Error(result.error))
                    layoutRetriesFlow.first()
                    logger.i("will retry to load module [$moduleId]")
                    emit(LayoutState.Loading)
                }
            }
        }
    }

    fun reset() {
        reuseRoot.reset()
    }

    fun free(id: String) {
        reuseRoot.free(id)
    }

    private class NoCliveLayoutData(metadata: LayoutMetadata) :
        IllegalStateException("No CliveLayoutData for layout [$metadata]")
}
