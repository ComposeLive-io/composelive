package io.clive.manifest

import app.cash.zipline.ZiplineManifest
import app.cash.zipline.loader.ZiplineHttpClient
import io.clive.configuration.CliveConfiguration
import io.clive.configuration.CliveConfiguration.Environment.Development
import io.clive.configuration.CliveConfiguration.Environment.Production
import io.clive.configuration.url
import io.clive.logger.CliveLogger
import io.clive.manifest.CliveManifest.Companion.FILE_NAME
import io.clive.util.CliveResources
import io.clive.util.DOWNLOAD_HEADERS
import io.clive.util.FileSystem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okio.ByteString.Companion.encodeUtf8
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.minutes
import kotlin.time.ExperimentalTime

internal class CliveManifestRepository(
    private val uiScope: CoroutineScope,
    private val configuration: CliveConfiguration,
    private val httpClient: ZiplineHttpClient,
    private val logger: CliveLogger,
    private val resources: CliveResources?,
) {
    private val cache: Cache? by lazy { configuration.cacheDir?.let { Cache(it) } }

    suspend fun get(version: Int): CliveManifest = withContext(Dispatchers.IO) {
        val url = "${configuration.url(version)}/$FILE_NAME"

        val originalManifest = when (configuration.getEnvironment()) {
            Production -> {
                cache?.get(version) ?: (readResource(version)
                    ?: httpClient.loadManifest(url)).also {
                    cache?.put(version, it)
                }
            }

            Development -> {
                // Development and local manifests are unstable, cache is disabled for those
                httpClient.loadManifest(url)
            }
        }

        CliveManifest(version, originalManifest)
    }

    private fun readResource(version: Int): ZiplineManifest? {
        return resources?.readByteString("$FILE_NAME-$version")?.utf8()?.let {
            ZiplineManifest.decodeJson(it)
        }
    }

    private suspend fun ZiplineHttpClient.loadManifest(
        url: String,
    ): ZiplineManifest = withContext(Dispatchers.IO) {
        val data = download(url, DOWNLOAD_HEADERS)
        val manifestUtf8 = data.utf8()

        ZiplineManifest
            .decodeJson(manifestUtf8)
            .copy(baseUrl = url)
    }

    private inner class Cache(cliveCacheDir: String) {
        private val fs = FileSystem
        private val manifestDir = "$cliveCacheDir/manifests"
        private fun manifestPath(version: Int): String = "$manifestDir/$version.json"

        init {
            uiScope.launch(Dispatchers.IO) {
                delay(1.minutes)
                try {
                    @OptIn(ExperimentalTime::class)
                    fs.prune(manifestDir, Clock.System.now() - 7.days)
                } catch (e: Exception) {
                    logger.e("prune manifests", e)
                }
            }
        }

        suspend fun get(version: Int): ZiplineManifest? = withContext(Dispatchers.IO) {
            try {
                val path = manifestPath(version)
                fs.readByteString(path)?.utf8()?.let {
                    ZiplineManifest.decodeJson(it)
                }
            } catch (e: Exception) {
                logger.e("read manifest with version: $version", e)
                return@withContext null
            }
        }

        fun put(version: Int, manifest: ZiplineManifest) = uiScope.launch(Dispatchers.IO) {
            try {
                val path = manifestPath(version)
                fs.writeByteString(path, manifest.encodeJson().encodeUtf8())
            } catch (e: Exception) {
                logger.e("write manifest with version: $version", e)
            }
        }
    }
}
