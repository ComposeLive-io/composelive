package io.composelive.app.treehouse

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import app.cash.redwood.leaks.RedwoodLeakApi
import app.cash.redwood.treehouse.EventListener
import app.cash.redwood.treehouse.TreehouseApp
import app.cash.redwood.treehouse.TreehouseContentSource
import app.cash.redwood.treehouse.composeui.TreehouseContent
import app.cash.zipline.loader.ZiplineHttpClient
import app.cash.zipline.loader.withDevelopmentServerPush
import coil3.ImageLoader
import io.composelive.app.Container
import io.composelive.network.ManifestHostUrl
import io.composelive.nodes.standard.host.composeui.ComposeUiStandardWidgetSystem
import io.composelive.treehouse.MainHostApiService
import io.composelive.treehouse.MainPresenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.flowOf

@Composable
fun MainTreehouseApp(
    scope: CoroutineScope,
    appFactory: TreehouseApp.Factory,
    httpClient: ZiplineHttpClient,
    hostApi: MainHostApiService,
    imageLoader: ImageLoader,
    hostUrl: ManifestHostUrl,
) {
    val treehouseContentSource = remember { TreehouseContentSource(MainPresenter::launch) }
    val eventListener = remember { MainEventListener() }
    val treehouseApp = remember(
        // TODO: Hot reloading is broken for some reason.
        //       This needs to be removed when fixed.
        eventListener.contentReloadedCounter,
    ) {
        createTreehouseApp(
            scope = scope,
            factory = appFactory,
            httpClient = httpClient,
            eventListener = eventListener,
            hostApi = hostApi,
            treehouseHost = hostUrl.url.toString(),
        )
    }
    Container { contentPadding ->
        TreehouseContent(
            treehouseApp = treehouseApp,
            widgetSystem = ComposeUiStandardWidgetSystem(imageLoader),
            contentSource = treehouseContentSource,
            modifier = Modifier.padding(contentPadding),
            dynamicContentWidgetFactory = MainDynamicContentWidgetFactory(),
        )
        if (eventListener.showError) {
            TreehouseConnectionFailure(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
            )
        }
    }
}

@OptIn(RedwoodLeakApi::class)
private fun createTreehouseApp(
    scope: CoroutineScope,
    factory: TreehouseApp.Factory,
    httpClient: ZiplineHttpClient,
    eventListener: EventListener,
    hostApi: MainHostApiService,
    treehouseHost: String,
): TreehouseApp<MainPresenter> {

    val manifestUrl = "$treehouseHost/manifest.zipline.json"
    val manifestUrlFlow = flowOf(manifestUrl)
        .withDevelopmentServerPush(httpClient)

    val treehouseApp = factory.create(
        appScope = scope,
        spec = MainAppSpec(
            manifestUrl = manifestUrlFlow,
            hostApi = hostApi,
        ),
        eventListenerFactory = object : EventListener.Factory {
            override fun create(app: TreehouseApp<*>, manifestUrl: String?) = eventListener
            override fun close() {}
        },
    )

    treehouseApp.start()

    return treehouseApp
}
