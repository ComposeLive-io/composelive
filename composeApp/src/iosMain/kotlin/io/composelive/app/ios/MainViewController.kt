package io.composelive.app.ios

import androidx.compose.ui.window.ComposeUIViewController
import app.cash.redwood.leaks.RedwoodLeakApi
import app.cash.redwood.treehouse.TreehouseApp
import app.cash.redwood.treehouse.TreehouseAppFactory
import app.cash.zipline.loader.ManifestVerifier
import app.cash.zipline.loader.ZiplineHttpClient
import app.cash.zipline.loader.asZiplineHttpClient
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.network.ktor3.KtorNetworkFetcherFactory
import coil3.serviceLoaderEnabled
import io.composelive.designsystem.motion.protocol.host.MotionHostProtocol
import io.composelive.shared.BaseUrl
import io.composelive.shared.MainTreehouseApp
import io.composelive.shared.RealHostApi
import io.composelive.shared.configure
import io.composelive.shared.leakDetector
import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.engine.darwin.KtorNSURLSessionDelegate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import platform.Foundation.NSURL
import platform.Foundation.NSURLSession
import platform.Foundation.NSURLSessionConfiguration
import platform.UIKit.UIApplication
import platform.UIKit.UIViewController

@Suppress("unused") // Used from Swift
fun mainViewController(application: UIApplication): UIViewController {
    val urlSessionDelegate = KtorNSURLSessionDelegate()
    val urlSession = NSURLSession.sessionWithConfiguration(
        NSURLSessionConfiguration.defaultSessionConfiguration(),
        urlSessionDelegate,
        delegateQueue = null
    )

    val scope: CoroutineScope = MainScope()
    val ktorClient = HttpClient(Darwin) {
        engine {
            usePreconfiguredSession(urlSession, urlSessionDelegate)
        }
        configure(BaseUrl.MockApi)
    }

    val httpClient = urlSession.asZiplineHttpClient()
    val appFactory = createTreehouseAppFactory(httpClient, scope)

    val imageLoader = ImageLoader.Builder(context = PlatformContext.INSTANCE)
        .serviceLoaderEnabled(false)
        .components {
            add(KtorNetworkFetcherFactory())
        }
        .build()

    val hostApi = RealHostApi(
        client = ktorClient,
        openUrl = { url ->
            scope.launch {
                val iosUrl = NSURL(string = url)
                application.openURL(iosUrl)
            }
        }
    )

    return ComposeUIViewController {
        MainTreehouseApp(
            scope = scope,
            appFactory = appFactory,
            httpClient = httpClient,
            hostApi = hostApi,
            imageLoader = imageLoader,
            baseUrl = BaseUrl.IosEmulatorHost,
        )
    }
}

@OptIn(RedwoodLeakApi::class)
@Suppress("unused") // Invoked in Swift.
private fun createTreehouseAppFactory(
    httpClient: ZiplineHttpClient,
    scope: CoroutineScope,
): TreehouseApp.Factory {
    return TreehouseAppFactory(
        httpClient = httpClient,
        manifestVerifier = ManifestVerifier.NO_SIGNATURE_CHECKS,
        leakDetector = leakDetector(scope),
        hostProtocolFactory = MotionHostProtocol,
    )
}
