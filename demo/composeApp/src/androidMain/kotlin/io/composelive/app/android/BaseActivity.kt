package io.composelive.app.android

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NoLiveLiterals
import androidx.compose.runtime.remember
import androidx.core.net.toUri
import app.cash.redwood.leaks.RedwoodLeakApi
import app.cash.redwood.treehouse.TreehouseApp
import app.cash.redwood.treehouse.TreehouseAppFactory
import app.cash.zipline.loader.ManifestVerifier
import app.cash.zipline.loader.ZiplineHttpClient
import app.cash.zipline.loader.asZiplineHttpClient
import io.composelive.app.treehouse.MainTreehouseApp
import io.composelive.app.treehouse.RealMainHostApi
import io.composelive.app.treehouse.leakDetector
import io.composelive.nodes.motion.protocol.host.MotionHostProtocol
import io.composelive.shared.BaseUrl
import io.composelive.shared.configure
import io.composelive.shared.rememberImageLoader
import io.composelive.treehouse.MainHostApiService
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import okhttp3.OkHttpClient
import okio.Path.Companion.toPath
import okio.assetfilesystem.asFileSystem

@NoLiveLiterals
abstract class BaseActivity : ComponentActivity() {
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main)

    @Composable
    protected fun Treehouse() {
        val okHttpClient = remember { OkHttpClient() }
        val httpClient = remember { okHttpClient.asZiplineHttpClient() }
        MainTreehouseApp(
            scope = scope,
            appFactory = remember { createTreehouseAppFactory(httpClient) },
            httpClient = httpClient,
            hostApi = remember { createHostApi(okHttpClient) },
            imageLoader = rememberImageLoader { this },
            baseUrl = BaseUrl.AndroidEmulatorHost,
        )
    }

    @OptIn(RedwoodLeakApi::class)
    private fun createTreehouseAppFactory(httpClient: ZiplineHttpClient): TreehouseApp.Factory {
        return TreehouseAppFactory(
            context = applicationContext,
            httpClient = httpClient,
            manifestVerifier = ManifestVerifier.NO_SIGNATURE_CHECKS,
            embeddedFileSystem = applicationContext.assets.asFileSystem(),
            embeddedDir = "/".toPath(),
            leakDetector = leakDetector(scope),
            hostProtocolFactory = MotionHostProtocol,
        )
    }

    private fun createHostApi(httpClient: OkHttpClient): MainHostApiService {
        return RealMainHostApi(
            client = HttpClient(OkHttp) {
                engine {
                    preconfigured = httpClient
                }
                configure(BaseUrl.MockApi)
            },
            openUrl = { url ->
                openUrl(url)
            },
        )
    }

    protected fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = url.toUri()
        startActivity(intent)
    }

    override fun onDestroy() {
        scope.cancel()
        super.onDestroy()
    }
}
