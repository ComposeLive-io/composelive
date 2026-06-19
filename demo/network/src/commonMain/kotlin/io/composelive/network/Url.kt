package io.composelive.network

public data class Url(
    internal val host: String,
    internal val isHttps: Boolean = false,
    internal val port: Int? = null,
    internal val path: String? = null,
) {
    public fun baseUrl(): String = copy(path = null).toString()

    override fun toString(): String = buildString {
        append(if (isHttps) "https" else "http")
        append("://")
        append(host)
        if (port != null) {
            append(':')
            append(port)
        }
        if (path != null) {
            append('/')
            append(path)
        }
    }
}
