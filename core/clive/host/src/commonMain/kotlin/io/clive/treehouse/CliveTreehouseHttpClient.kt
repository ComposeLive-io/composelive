package io.clive.treehouse

import app.cash.zipline.loader.ZiplineHttpClient
import io.clive.manifest.CliveManifest
import io.clive.manifest.CliveManifestRepository
import io.clive.module.CliveModuleRepository
import okio.ByteString

/**
 * Delegates downloads of zipline manifest/modules to corresponding repositories to implicitly
 * provide caching for ZiplineLoader.
 */
private class CliveTreehouseHttpClient(
    private val version: Int,
    private val httpClient: ZiplineHttpClient,
    private val manifestRepository: CliveManifestRepository,
    private val moduleRepository: CliveModuleRepository,
) : ZiplineHttpClient() {
    private lateinit var manifest: CliveManifest

    override suspend fun download(
        url: String,
        requestHeaders: List<Pair<String, String>>
    ): ByteString {
        return when {
            url.endsWith(CliveManifest.FILE_NAME) -> loadManifest()
            url.endsWith(".zipline") -> loadModule(url)
            else -> httpClient.download(url, requestHeaders)
        }
    }

    private suspend fun loadManifest(): ByteString {
        manifest = manifestRepository.get(version)
        return manifest.coreByteString
    }

    private suspend fun loadModule(url: String): ByteString {
        val moduleName = url
            .substringAfterLast("/")
            .removeSuffix(".zipline")

        val module = manifest["./$moduleName.js"]

        return moduleRepository.getRaw(manifest.version, module)
    }
}

/**
 * @See CliveTreehouseHttpClient
 */
internal fun ZiplineHttpClient.delegatesTo(
    version: Int,
    manifestRepository: CliveManifestRepository,
    moduleRepository: CliveModuleRepository,
): ZiplineHttpClient =
    CliveTreehouseHttpClient(
        version,
        this,
        manifestRepository,
        moduleRepository
    )
