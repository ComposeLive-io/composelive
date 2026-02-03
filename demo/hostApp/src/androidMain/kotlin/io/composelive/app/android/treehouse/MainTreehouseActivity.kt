package io.composelive.app.android.treehouse

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NoLiveLiterals
import androidx.compose.runtime.remember
import androidx.core.view.WindowCompat
import app.cash.zipline.loader.asZiplineHttpClient
import io.composelive.app.treehouse.MainTreehouseApp
import io.composelive.app.treehouse.RealMainHostApi
import io.composelive.network.BaseUrl
import io.composelive.network.ManifestHostUrl
import io.composelive.network.configure
import io.composelive.nodes.foundation.host.composeui.images.rememberImageLoader
import io.composelive.treehouse.MainHostApiService
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import okhttp3.OkHttpClient

@NoLiveLiterals
class MainTreehouseActivity : BaseTreehouseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            Treehouse()
        }
    }

    @Composable
    private fun Treehouse() {
        val okHttpClient = remember { OkHttpClient() }
        val httpClient = remember { okHttpClient.asZiplineHttpClient() }
        MainTreehouseApp(
            scope = scope,
            appFactory = remember { createTreehouseAppFactory(httpClient) },
            httpClient = httpClient,
            hostApi = remember { createHostApi(okHttpClient) },
            imageLoader = rememberImageLoader { this },
            hostUrl = ManifestHostUrl.AndroidEmulatorHost,
        )
    }

    private fun createHostApi(
        httpClient: OkHttpClient,
    ): MainHostApiService = RealMainHostApi(
        client = HttpClient(OkHttp) {
            engine {
                preconfigured = httpClient
            }
            configure(BaseUrl.MockApi)
        },
        openUrl = ::openUrl,
    )
}
