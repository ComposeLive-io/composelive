package io.composelive.network

public class Url(
    internal val host: String,
    internal val isHttps: Boolean = false,
    internal val port: Int? = null,
    internal val path: String? = null,
) {
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
