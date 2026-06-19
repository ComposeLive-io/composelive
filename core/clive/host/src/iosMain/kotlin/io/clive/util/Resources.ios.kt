@file:OptIn(ExperimentalForeignApi::class)

package io.clive.util

import kotlinx.cinterop.ExperimentalForeignApi
import okio.ByteString
import okio.ByteString.Companion.toByteString
import platform.Foundation.NSBundle
import platform.Foundation.NSData
import platform.Foundation.NSDataCompressionAlgorithmLZMA
import platform.Foundation.dataWithContentsOfFile
import platform.Foundation.decompressedDataUsingAlgorithm

public actual class CliveResources {
    internal actual fun readByteString(path: String): ByteString? {
        val bundlePath = NSBundle.mainBundle.pathForResource(path, "lzma") ?: return null
        return NSData.dataWithContentsOfFile(bundlePath)
            ?.decompressedDataUsingAlgorithm(NSDataCompressionAlgorithmLZMA, null)
            ?.toByteString()
    }
}
