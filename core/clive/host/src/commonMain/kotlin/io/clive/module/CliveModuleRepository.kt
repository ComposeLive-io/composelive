package io.clive.module

import app.cash.zipline.ZiplineManifest
import app.cash.zipline.loader.ZiplineFile.Companion.toZiplineFile
import app.cash.zipline.loader.ZiplineHttpClient
import io.clive.configuration.CliveConfiguration
import io.clive.configuration.CliveConfiguration.Environment.Development
import io.clive.configuration.CliveConfiguration.Environment.Production
import io.clive.configuration.url
import io.clive.logger.CliveLogger
import io.clive.util.CliveResources
import io.clive.util.DOWNLOAD_HEADERS
import io.clive.util.FileSystem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okio.ByteString
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.minutes
import kotlin.time.ExperimentalTime

internal class CliveModuleRepository(
    private val uiScope: CoroutineScope,
    private val configuration: CliveConfiguration,
    private val httpClient: ZiplineHttpClient,
    private val logger: CliveLogger,
    private val resources: CliveResources?,
) {
    private val cache: Cache? by lazy { configuration.cacheDir?.let { Cache(it) } }

    suspend fun get(
        manifestVersion: Int,
        module: ZiplineManifest.Module,
    ): ByteArray = withContext(Dispatchers.IO) {
        val rawData = getRaw(manifestVersion, module)
        val bytecode = rawData.toZiplineFile().quickjsBytecode.toByteArray()
        return@withContext bytecode
    }

    suspend fun getRaw(
        manifestVersion: Int,
        module: ZiplineManifest.Module,
    ): ByteString = withContext(Dispatchers.IO) {
        val url = "${configuration.url(manifestVersion)}/${module.url}"

        val byteString = when (configuration.getEnvironment()) {
            Production -> {
                cache?.get(module)
                    ?: (readResource(module)
                        ?: httpClient.download(url, DOWNLOAD_HEADERS)).also {
                        cache?.put(module, it)
                    }
            }

            Development -> {
                // Development and local modules are unstable, cache is disabled for those
                httpClient.download(url, DOWNLOAD_HEADERS)
            }
        }

        return@withContext byteString
    }

    private fun readResource(module: ZiplineManifest.Module) =
        resources?.readByteString("${module.url}-${module.sha256.hex()}")

    private inner class Cache(cliveCacheDir: String) {
        private val fs = FileSystem
        private val modulesDir = "$cliveCacheDir/modules"

        private fun modulePath(module: ZiplineManifest.Module): String =
            "$modulesDir/${module.url.removeSuffix(".zipline")}/${module.sha256.hex()}.zipline"

        init {
            uiScope.launch(Dispatchers.IO) {
                delay(1.minutes)
                try {
                    @OptIn(ExperimentalTime::class)
                    fs.prune(modulesDir, Clock.System.now() - 7.days)
                } catch (e: Exception) {
                    logger.e("prune modules", e)
                }
            }
        }

        fun get(module: ZiplineManifest.Module): ByteString? {
            val path = modulePath(module)
            try {
                return fs.readByteString(path)
            } catch (e: Exception) {
                logger.e("read module from $path", e)
                return null
            }
        }

        fun put(module: ZiplineManifest.Module, byteString: ByteString) {
            uiScope.launch(Dispatchers.IO) {
                val path = modulePath(module)
                try {
                    fs.writeByteString(path, byteString)
                } catch (e: Exception) {
                    logger.e("write module to $path", e)
                }
            }
        }
    }
}
