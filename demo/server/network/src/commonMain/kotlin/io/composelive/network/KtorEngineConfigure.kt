package io.composelive.network

import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol

public fun HttpClientConfig<*>.configure(baseUrl: BaseUrl) {
    defaultRequest {
        url {
            setBaseUrl(baseUrl)
        }
    }
}

public fun URLBuilder.setBaseUrl(baseUrl: BaseUrl) {
    val url = baseUrl.url
    protocol = if (url.isHttps) URLProtocol.HTTPS else URLProtocol.HTTP
    host = url.host
    if (url.port != null) {
        port = url.port
    }
    if (url.path != null) {
        pathSegments = listOf(url.path)
    }
}
