package io.clive.treehouse

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.cash.redwood.treehouse.DynamicContentWidgetFactory
import app.cash.redwood.treehouse.TreehouseApp
import app.cash.redwood.treehouse.TreehouseContentSource
import app.cash.redwood.treehouse.TreehouseDispatchers
import app.cash.redwood.treehouse.bindWhenReady
import app.cash.redwood.ui.Cancellable
import app.cash.redwood.ui.OnBackPressedCallback
import app.cash.redwood.ui.OnBackPressedDispatcher
import app.cash.redwood.widget.WidgetSystem
import app.cash.zipline.Zipline
import io.clive.configuration.CliveConfiguration
import io.clive.configuration.CliveUiConfiguration
import io.clive.logger.CliveLogger
import io.clive.manifest.CliveManifest
import io.clive.services.MainAppService
import io.composelive.reuse.treehouse.HostZiplineBridge
import io.composelive.reuse.treehouse.MainAppSpec
import io.composelive.reuse.treehouse.ReuseRootOwner
import io.composelive.reuse.treehouse.ReuseRootView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import okio.Closeable
import kotlin.concurrent.Volatile

public class CliveTreehouseFactory(
    private val uiScope: CoroutineScope,
    private val createTreehouseFactory: (version: Int) -> TreehouseApp.Factory,
    private val widgetSystem: WidgetSystem<@Composable (Modifier) -> Unit>,
    configuration: CliveConfiguration,
    private val uiConfiguration: CliveUiConfiguration,
    hostBridges: HostZiplineBridge,
    logger: CliveLogger,
) {
    private val appSpec = MainAppSpec(
        flow { emit(configuration.getBaseUrl() + "/${CliveManifest.FILE_NAME}") },
        hostBridges
    )

    private val dynamicContentWidgetFactory = CliveDynamicContentWidgetFactory(logger)
    private val contentSource = TreehouseContentSource(MainAppService::launch)

    internal fun create(
        version: Int,
        logger: CliveLogger,
        onError: (String, Exception) -> Unit,
    ): CliveTreehouse {
        val app = createTreehouseFactory(version).create(
            appScope = uiScope,
            spec = appSpec,
            eventListenerFactory = CliveTreehouseListener(logger, onError),
        )
        return CliveTreehouse(
            app,
            uiConfiguration,
            dynamicContentWidgetFactory,
            widgetSystem,
            contentSource,
            logger,
        )
    }
}

internal class CliveTreehouse(
    private val app: TreehouseApp<MainAppService>,
    private val cliveUiConfiguration: CliveUiConfiguration,
    private val dynamicContentWidgetFactory: DynamicContentWidgetFactory<@Composable (Modifier) -> Unit>,
    private val widgetSystem: WidgetSystem<@Composable (Modifier) -> Unit>,
    private val contentSource: TreehouseContentSource<MainAppService>,
    private val logger: CliveLogger,
) : AutoCloseable {

    val dispatchers: TreehouseDispatchers get() = app.dispatchers

    val zipline: Flow<Zipline> = app.zipline.filterNotNull()

    @Volatile
    private var boundCloseable: Closeable? = null

    fun bind(reuseRootOwner: ReuseRootOwner) {
        val reuseRootView = ReuseRootView(
            reuseRootOwner = reuseRootOwner,
            widgetSystem = widgetSystem,
            dynamicContentWidgetFactory = dynamicContentWidgetFactory,
            onBackPressedDispatcher = NoOpOnBackPressedDispatcher,
            uiConfiguration = cliveUiConfiguration.flow,
        )
        boundCloseable = contentSource.bindWhenReady(reuseRootView, app)
    }

    suspend fun stop() {
        boundCloseable?.close()
        app.stop()

        logger.i("stop - wait zipline thread tasks finished")
        withContext(dispatchers.zipline) {
            // wait till currently posted tasks are finished.
        }
        logger.i("stop - wait old zipline is closed")
        app.zipline.first { it == null }
    }

    override fun close() {
        boundCloseable?.close()
        app.close()
    }
}

private object NoOpOnBackPressedDispatcher : OnBackPressedDispatcher {
    override fun addCallback(
        onBackPressedCallback: OnBackPressedCallback,
    ): Cancellable = object : Cancellable {
        override fun cancel() {}
    }
}
