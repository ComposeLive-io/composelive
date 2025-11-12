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
    val url: String,
    val protocol: URLProtocol,
) {
    MockApi(url = "688d0588cd9d22dda5cf3ab8.mockapi.io/bdui", protocol = URLProtocol.HTTPS),
    IosEmulatorHost(url = "localhost:8080", protocol = URLProtocol.HTTP),
    AndroidEmulatorHost(url = "10.0.2.2:8080", protocol = URLProtocol.HTTP),
}

fun URLBuilder.setBaseUrl(baseUrl: BaseUrl) {
    protocol = baseUrl.protocol
    host = baseUrl.url
}
