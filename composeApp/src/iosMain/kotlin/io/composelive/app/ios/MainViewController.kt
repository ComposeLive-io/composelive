package io.composelive.app.ios

import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.window.ComposeUIViewController
import app.cash.redwood.leaks.LeakDetector
import app.cash.redwood.leaks.RedwoodLeakApi
import app.cash.redwood.treehouse.EventListener
import app.cash.redwood.treehouse.TreehouseApp
import app.cash.redwood.treehouse.TreehouseAppFactory
import app.cash.zipline.Zipline
import app.cash.zipline.ZiplineManifest
import app.cash.zipline.loader.ManifestVerifier
import app.cash.zipline.loader.asZiplineHttpClient
import app.cash.zipline.loader.withDevelopmentServerPush
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.network.ktor3.KtorNetworkFetcherFactory
import coil3.serviceLoaderEnabled
import io.composelive.shared.MainTreehouseApp
import io.composelive.designsystem.motion.protocol.host.MotionHostProtocol
import io.composelive.launcher.MainAppSpec
import io.composelive.treehouse.HostApi
import io.composelive.treehouse.MainPresenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.flowOf
import platform.Foundation.NSLog
import platform.Foundation.NSOperationQueue
import platform.Foundation.NSURLSession
import platform.UIKit.UIViewController
import kotlin.time.Duration.Companion.seconds
import kotlin.time.TimeSource

@Suppress("FunctionName") // Used from Swift
fun MainViewController(
    nsurlSession: NSURLSession,
    hostApi: HostApi,
    listener: MainEventListener,
): UIViewController {
    val app = createTreehouseApp(nsurlSession, hostApi, listener)

    val imageLoader = ImageLoader.Builder(context = PlatformContext.INSTANCE)
        .serviceLoaderEnabled(false)
        .components {
            add(KtorNetworkFetcherFactory())
        }
        .build()

    return ComposeUIViewController {
        MainTreehouseApp(
            treehouseApp = app,
            snackbarHostState = SnackbarHostState(),
            imageLoader = imageLoader,
        )
    }
}

@OptIn(RedwoodLeakApi::class)
@Suppress("unused") // Invoked in Swift.
private fun createTreehouseApp(
    nsurlSession: NSURLSession,
    hostApi: HostApi,
    listener: MainEventListener,
): TreehouseApp<MainPresenter> {
    val coroutineScope: CoroutineScope = MainScope()
    val ziplineHttpClient = nsurlSession.asZiplineHttpClient()

    val eventListener = object : EventListener() {
        override fun codeLoadFailed(exception: Exception, startValue: Any?) {
            NSLog("Treehouse: codeLoadFailed: $exception")
            NSOperationQueue.mainQueue.addOperationWithBlock {
                listener.codeLoadFailed()
            }
        }

        override fun codeLoadSuccess(
            manifest: ZiplineManifest,
            zipline: Zipline,
            startValue: Any?
        ) {
            NSLog("Treehouse: codeLoadSuccess")
            NSOperationQueue.mainQueue.addOperationWithBlock {
                listener.codeLoadSuccess()
            }
        }
    }

    val treehouseAppFactory = TreehouseAppFactory(
        httpClient = ziplineHttpClient,
        manifestVerifier = ManifestVerifier.Companion.NO_SIGNATURE_CHECKS,
        leakDetector = LeakDetector.timeBasedIn(
            scope = coroutineScope,
            timeSource = TimeSource.Monotonic,
            leakThreshold = 10.seconds,
            callback = { reference, note ->
                NSLog("Leak detected! $reference $note")
            },
        ),
        hostProtocolFactory = MotionHostProtocol,
    )

    val manifestUrl = "http://localhost:8080/manifest.zipline.json"
    val manifestUrlFlow = flowOf(manifestUrl)
        .withDevelopmentServerPush(ziplineHttpClient)

    val treehouseApp = treehouseAppFactory.create(
        appScope = coroutineScope,
        spec = MainAppSpec(
            manifestUrl = manifestUrlFlow,
            hostApi = hostApi,
        ),
        eventListenerFactory = object : EventListener.Factory {
            override fun create(app: TreehouseApp<*>, manifestUrl: String?) = eventListener
            override fun close() {
            }
        },
    )

    treehouseApp.start()

    return treehouseApp
}

interface MainEventListener {
    fun codeLoadFailed()
    fun codeLoadSuccess()
}
