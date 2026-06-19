package io.clive.manifest

import app.cash.zipline.ZiplineManifest
import io.clive.manifest.util.reduceFor
import okio.ByteString
import okio.ByteString.Companion.encodeUtf8

public data class CliveManifest(
    public val version: Int,
    private val originalManifest: ZiplineManifest,
) {
    public val coreManifest: ZiplineManifest by lazy {
        originalManifest.reduceFor(
            moduleId = originalManifest.mainModuleId,
            mainFunction = originalManifest.mainFunction,
        )
    }

    public val coreByteString: ByteString by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        coreManifest.encodeJson().encodeUtf8()
    }

    public fun dependenciesFor(moduleId: String): Map<String, ZiplineManifest.Module> {
        this[moduleId] // Check that requested module exists in the manifest
        return originalManifest.reduceFor(moduleId).modules
    }

    public operator fun contains(moduleId: String): Boolean =
        moduleId in originalManifest.modules

    public operator fun get(moduleId: String): ZiplineManifest.Module =
        originalManifest.modules[moduleId]
            ?: throw NoModuleInManifest("Module [$moduleId] doesn't exist in manifest ver. $version")

    public companion object {
        private class NoModuleInManifest(message: String) : IllegalStateException(message)

        public const val FILE_NAME: String = "manifest.zipline.json"
    }
}
