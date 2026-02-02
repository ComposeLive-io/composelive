package io.composelive.app.ios

import androidx.compose.ui.window.ComposeUIViewController
import app.cash.zipline.loader.asZiplineHttpClient
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.network.ktor3.KtorNetworkFetcherFactory
import coil3.serviceLoaderEnabled
import io.composelive.app.treehouse.MainTreehouseApp
import io.composelive.network.ManifestHostUrl
import io.composelive.treehouse.MainHostApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import platform.Foundation.NSURLSession
import platform.UIKit.UIApplication
import platform.UIKit.UIViewController

@Suppress("unused") // Used from Swift
fun mainViewController(application: UIApplication, hostApi: MainHostApiService): UIViewController {
    val scope: CoroutineScope = MainScope()

    val httpClient = NSURLSession.sharedSession.asZiplineHttpClient()
    val appFactory = createTreehouseAppFactory(httpClient, scope)

    val imageLoader = ImageLoader.Builder(context = PlatformContext.INSTANCE)
        .serviceLoaderEnabled(false)
        .components {
            add(KtorNetworkFetcherFactory())
        }
        .build()

    return ComposeUIViewController {
        MainTreehouseApp(
            scope = scope,
            appFactory = appFactory,
            httpClient = httpClient,
            hostApi = hostApi,
            imageLoader = imageLoader,
            hostUrl = ManifestHostUrl.IosSimulatorHost,
        )
    }
}
