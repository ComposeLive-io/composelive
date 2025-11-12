package io.composelive.shared

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.bodyAsText
import io.composelive.presenter.network.HttpClient as RedwoodHttpClient

class RedwoodHttpClientImpl(private val client: HttpClient) : RedwoodHttpClient {

    override suspend fun call(
        url: String,
        headers: Map<String, String>
    ): String {
        val response = client.get(url) {
            headers.forEach { (key, value) ->
                header(key, value)
            }
        }
        return response.bodyAsText()
    }
}
