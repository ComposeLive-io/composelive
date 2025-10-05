package io.composelive.shared.android

import android.content.Intent
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NoLiveLiterals
import androidx.compose.runtime.remember
import androidx.core.net.toUri
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
import coil3.network.ktor3.KtorNetworkFetcherFactory
import coil3.serviceLoaderEnabled
import io.composelive.designsystem.motion.protocol.host.MotionHostProtocol
import io.composelive.launcher.MainAppSpec
import io.composelive.shared.MainTreehouseApp
import io.composelive.shared.RealHostApi
import io.composelive.treehouse.HostApi
import io.composelive.treehouse.MainPresenter
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okio.Path.Companion.toPath
import okio.assetfilesystem.asFileSystem
import kotlin.time.Duration.Companion.seconds
import kotlin.time.TimeSource

@NoLiveLiterals
abstract class BaseActivity : ComponentActivity() {
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main)
    protected val snackbarHostState = SnackbarHostState()

    @OptIn(RedwoodLeakApi::class)
    private val leakDetector = LeakDetector.Companion.timeBasedIn(
        scope = scope,
        timeSource = TimeSource.Monotonic,
        leakThreshold = 10.seconds,
        callback = { reference, note ->
            Log.e("LEAK", "Leak detected! $reference $note")
        },
    )

    private val appEventListener: EventListener = object : EventListener() {
        private var success = true
        private var snackbarJob: Job? = null

        override fun codeLoadFailed(exception: Exception, startValue: Any?) {
            Log.w("Treehouse", "codeLoadFailed", exception)
            if (success) {
                // Only show the Snackbar on the first transition from success.
                success = false
                snackbarJob = scope.launch {
                    snackbarHostState.showSnackbar(
                        message = "Unable to load guest code from server",
                        actionLabel = "Dismiss",
                        duration = SnackbarDuration.Indefinite,
                    )
                }
            }
        }

        override fun codeLoadSuccess(
            manifest: ZiplineManifest,
            zipline: Zipline,
            startValue: Any?
        ) {
            Log.i("Treehouse", "codeLoadSuccess")
            success = true
            snackbarJob?.cancel()
        }
    }

    @Composable
    protected fun Treehouse() {
        MainTreehouseApp(
            treehouseApp = remember { createTreehouseApp(httpClient = OkHttpClient()) },
            snackbarHostState = snackbarHostState,
            imageLoader = rememberImageLoader(),
        )
    }

    @Composable
    protected fun rememberImageLoader(): ImageLoader = remember {
        ImageLoader.Builder(context = this)
            .serviceLoaderEnabled(false)
            .components {
                add(KtorNetworkFetcherFactory())
            }
            .build()
    }

    @OptIn(RedwoodLeakApi::class)
    private fun createTreehouseApp(httpClient: OkHttpClient): TreehouseApp<MainPresenter> {
        val treehouseAppFactory = TreehouseAppFactory(
            context = applicationContext,
            httpClient = httpClient,
            manifestVerifier = ManifestVerifier.Companion.NO_SIGNATURE_CHECKS,
            embeddedFileSystem = applicationContext.assets.asFileSystem(),
            embeddedDir = "/".toPath(),
            leakDetector = leakDetector,
            hostProtocolFactory = MotionHostProtocol,
        )

        val manifestUrl = "http://10.0.2.2:8080/manifest.zipline.json"
        val manifestUrlFlow = flowOf(manifestUrl)
            .withDevelopmentServerPush(httpClient.asZiplineHttpClient())

        val treehouseApp = treehouseAppFactory.create(
            appScope = scope,
            spec = MainAppSpec(
                manifestUrl = manifestUrlFlow,
                hostApi = createHostApi(httpClient),
            ),
            eventListenerFactory = object : EventListener.Factory {
                override fun create(app: TreehouseApp<*>, manifestUrl: String?) = appEventListener
                override fun close() {}
            },
        )

        treehouseApp.start()

        return treehouseApp
    }

    private fun createHostApi(httpClient: OkHttpClient): HostApi {
        return RealHostApi(
            client = HttpClient(OkHttp) {
                engine {
                    preconfigured = httpClient
                }
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
