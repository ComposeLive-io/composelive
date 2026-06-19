package io.clive.util

import android.content.Context
import okio.ByteString
import okio.ByteString.Companion.toByteString

public actual class CliveResources(
    context: Context,
) {
    private val assets = context.assets

    internal actual fun readByteString(path: String): ByteString? {
        return try {
            assets.open("clive/$path").use {
                it.readBytes().toByteString()
            }
        } catch (_: Exception) {
            return null
        }
    }
}
