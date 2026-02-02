package io.composelive.shared

import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol

fun HttpClientConfig<*>.configure(baseUrl: BaseUrl) {
    defaultRequest {
        url {
            setBaseUrl(baseUrl)
        }
    }
}

enum class BaseUrl(
    val host: String,
    val isHttps: Boolean = false,
    val port: Int? = null,
    val path: String? = null,
) {
    MockApi(host = "688d0588cd9d22dda5cf3ab8.mockapi.io/bdui", isHttps = true),
    IosSimulatorHost(host = "localhost", port = 8080),
    AndroidEmulatorHost(host = "10.0.2.2", port = 8080),

    ;

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

fun URLBuilder.setBaseUrl(baseUrl: BaseUrl) {
    protocol = if (baseUrl.isHttps) URLProtocol.HTTPS else URLProtocol.HTTP
    host = baseUrl.host
    if (baseUrl.port != null) {
        port = baseUrl.port
    }
    if (baseUrl.path != null) {
        pathSegments = listOf(baseUrl.path)
    }
}
