package io.composelive.shared

import io.composelive.treehouse.HostApi
import io.ktor.client.HttpClient

class RealHostApi(
    client: HttpClient,
    private val openUrl: (url: String) -> Unit,
) : HostApi {

    private val redwoodClient = RedwoodHttpClientImpl(client)

    override suspend fun httpCall(url: String, headers: Map<String, String>): String {
        return redwoodClient.call(url, headers)
    }

    override fun openUrl(url: String) {
        openUrl.invoke(url)
    }
}
