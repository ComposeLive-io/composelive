package io.composelive.network

// Switch to https://github.com/cashapp/zipline/issues/490 once available.
@Suppress("FUN_INTERFACE_WITH_SUSPEND_FUNCTION") // https://youtrack.jetbrains.com/issue/KTIJ-7642
fun interface HttpClient {
    suspend fun call(url: String, headers: Map<String, String>): String
}
