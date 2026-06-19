package io.composelive.nodes.foundation.host.composeui.images

import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import okio.Buffer
import platform.Foundation.NSData
import platform.Foundation.create
import platform.posix.memcpy

@OptIn(ExperimentalForeignApi::class)
internal fun NSData.toBuffer(): Buffer = Buffer().write(toByteArray())

internal fun Buffer.toNSData(): NSData = readByteArray().toNSData()

@OptIn(ExperimentalForeignApi::class)
internal fun NSData.toByteArray() = ByteArray(length.toInt()).apply {
    usePinned { pinned ->
        memcpy(pinned.addressOf(0), bytes, length)
    }
}

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
internal fun ByteArray.toNSData(): NSData = usePinned {
    NSData.create(it.addressOf(0), size.toULong())
}
