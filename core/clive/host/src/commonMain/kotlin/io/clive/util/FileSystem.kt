package io.clive.util

import okio.ByteString
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath
import okio.SYSTEM
import okio.buffer
import okio.use
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

internal object FileSystem {
    private val fileSystem = FileSystem.SYSTEM

    fun readByteString(path: String): ByteString? {
        val filepath = path.toPath()
        if (!fileSystem.exists(filepath)) return null
        return fileSystem.read(filepath) {
            readByteString()
        }
    }

    fun writeByteString(path: String, byteString: ByteString) {
        val filepath = path.toPath()
        filepath.parent?.let { fileSystem.createDirectories(it) }
        fileSystem.sink(path.toPath()).buffer().use { sink ->
            sink.write(byteString)
        }
    }

    fun delete(path: String) {
        fileSystem.delete(path.toPath())
    }

    @OptIn(ExperimentalTime::class)
    fun prune(path: String, cutoff: Instant) {
        val filepath = path.toPath()
        if (!fileSystem.exists(filepath)) return
        prune(filepath, cutoff.toEpochMilliseconds())
    }

    private fun prune(dir: Path, cutoff: Long) {
        fileSystem.listOrNull(dir)?.forEach { path ->
            val metadata = fileSystem.metadataOrNull(path) ?: return@forEach
            when {
                metadata.isRegularFile && (metadata.lastAccessedAtMillis ?: 0) < cutoff -> {
                    fileSystem.delete(path)
                }
                metadata.isDirectory -> {
                    prune(path, cutoff)
                }
            }
        }
        if (fileSystem.listOrNull(dir)?.isEmpty() == true) {
            fileSystem.delete(dir)
        }
    }
}