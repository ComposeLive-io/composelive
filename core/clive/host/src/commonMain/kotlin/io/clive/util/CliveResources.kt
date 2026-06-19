package io.clive.util

import okio.ByteString

public expect class CliveResources {
    internal fun readByteString(path: String): ByteString?
}
