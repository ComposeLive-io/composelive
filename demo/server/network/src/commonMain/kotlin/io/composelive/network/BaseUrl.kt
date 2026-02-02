package io.composelive.network

public enum class BaseUrl(
    internal val url: Url,
) {
    MockApi(Url(host = "688d0588cd9d22dda5cf3ab8.mockapi.io/bdui", isHttps = true)),
}
