package io.composelive.shared

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.bodyAsText
import io.composelive.presenter.network.HttpClient as RedwoodHttpClient

fun httpClientImpl(client: HttpClient) = RedwoodHttpClient { url, headers ->
    val response = client.get(url) {
        headers.forEach { (key, value) ->
            header(key, value)
        }
    }
    response.bodyAsText()
}
