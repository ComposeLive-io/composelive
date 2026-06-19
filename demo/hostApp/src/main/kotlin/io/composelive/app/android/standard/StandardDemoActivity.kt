package io.composelive.app.android.standard

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NoLiveLiterals
import androidx.compose.runtime.remember
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import app.cash.zipline.Zipline
import io.clive.configuration.CliveConfiguration
import io.clive.configuration.CliveUiConfiguration
import io.clive.logger.LogcatCliveLogger
import io.clive.logger.LoggerServiceImpl
import io.clive.services.Logger
import io.clive.standard.host.composeui.createStandardCliveScreenFactory
import io.clive.ui.CliveScreenFactory
import io.composelive.network.ManifestUrl
import io.composelive.reuse.treehouse.HostZiplineBridge
import okhttp3.OkHttpClient

@NoLiveLiterals
class StandardDemoActivity : ComponentActivity() {
    private val uiConfiguration by lazy { CliveUiConfiguration(context = this) }
    private val logger = LogcatCliveLogger()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val screenFactory = rememberCliveScreenFactory()
            DemoStandardApp(screenFactory)
        }
    }

    @Composable
    private fun rememberCliveScreenFactory(): CliveScreenFactory {
        return remember {
            val configuration = object : CliveConfiguration {
                override suspend fun getBaseUrl(): String {
                    return ManifestUrl.AndroidEmulatorHost.baseUrl()
                }

                override suspend fun getEnvironment(): CliveConfiguration.Environment {
                    return CliveConfiguration.Environment.Development
                }

                override val cacheDir: String
                    get() = this@StandardDemoActivity.cacheDir.absolutePath
            }

            createStandardCliveScreenFactory(
                context = this,
                uiScope = lifecycleScope,
                configuration,
                uiConfiguration,
                httpClient = OkHttpClient(),
                logger = logger,
                hostZiplineBridge = object : HostZiplineBridge {
                    override fun bindTo(zipline: Zipline) {
                        zipline.bind<Logger>(Logger.NAME, LoggerServiceImpl(logger))
                    }
                },
            )
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        uiConfiguration.update(context = this)
    }
}
