package io.clive.core

import app.cash.zipline.ZiplineService
import io.clive.logger.CliveLogger
import io.clive.treehouse.CliveTreehouse
import io.clive.treehouse.CliveZipline
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.cancellation.CancellationException

/**
 * Runs tasks on [CliveService].
 */
internal class CliveServiceTaskManager(
    private val treehouse: CliveTreehouse,
    private val zipline: StateFlow<CliveZipline?>,
    private val uiScope: CoroutineScope,
    private val logger: CliveLogger,
    private val isCliveFreed: () -> Boolean,
    private val onError: (error: Exception) -> Unit,
) {
    /**
     * Repeatedly runs [task] on [service] whenever underlying zipline runtime changes.
     * There is no guaranty [task] will be ever executed.
     *
     * If error occurred during task execution, whole [Clive] runtime will fail.
     */
    internal inline fun <reified TService : ZiplineService> runSticky(
        service: CliveService<TService>,
        crossinline task: TService.() -> Unit,
    ) = uiScope.launch {
        if (isCliveFreed()) return@launch

        zipline
            .filterNotNull()
            .distinctUntilChanged()
            .collectLatest { zipline ->
                if (isCliveFreed()) {
                    cancel()
                }

                try {
                    withContext(treehouse.dispatchers.zipline) {
                        logger.i("will execute sticky task on service [$service]")
                        val service = zipline.service(service)
                        service.task()
                    }
                } catch (e: Exception) {
                    if (!isCliveFreed() && e !is CancellationException) {
                        logger.e("Service [$service] sticky task failed", e)
                        onError(e)
                    }
                }
            }
    }

    /**
     * Tries to run [task] on [service].
     * There is no guaranty [task] will be ever executed.
     */
    internal inline fun <reified TService : ZiplineService> runNow(
        service: CliveService<TService>,
        crossinline task: TService.() -> Unit,
    ) = uiScope.launch {
        if (isCliveFreed()) return@launch

        val zipline = zipline.firstOrNull() ?: return@launch

        try {
            withContext(treehouse.dispatchers.zipline) {
                logger.i("will execute one-time task on service [$service]")
                val service = zipline.service(service)
                service.task()
            }
        } catch (e: Exception) {
            if (!isCliveFreed() && e !is CancellationException) {
                logger.e("Service [$service] one-time task failed", e)
            }
        }
    }
}
