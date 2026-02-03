package io.composelive

interface MainHostApi {
    /** Decodes the response as a string and returns it. */
    suspend fun httpCall(url: String, headers: Map<String, String>): String

    fun openUrl(url: String)
}
