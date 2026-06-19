package io.clive.test.composeui

public class MemoryMeasurer {
    private val logger = Logger("MemoryMeasurer")

    private var startUsage: Long = 0

    public fun start() {
        startUsage = memoryUsageInBytes()
    }

    public fun finish(message: String) {
        val usage = memoryUsageInBytes()
        val diff = usage - startUsage
        logger.info("$message, diff=$diff, pretty=${bytesToHumanReadableSize(diff.toDouble())}")
    }

    private fun bytesToHumanReadableSize(bytes: Double) = when {
        bytes >= 1 shl 30 -> "${bytes / (1 shl 30)} GB"
        bytes >= 1 shl 20 -> "${bytes / (1 shl 20)} MB"
        bytes >= 1 shl 10 -> "${bytes / (1 shl 10)} kB"
        else -> "$bytes bytes"
    }
}

internal expect fun memoryUsageInBytes(): Long
