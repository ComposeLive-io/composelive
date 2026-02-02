package io.composelive.app.treehouse

import io.composelive.network.RedwoodHttpClientImpl
import io.composelive.treehouse.MainHostApiService
import io.ktor.client.HttpClient

class RealMainHostApi(
    client: HttpClient,
    private val openUrl: (url: String) -> Unit,
) : MainHostApiService {

    private val redwoodClient = RedwoodHttpClientImpl(client)

    override suspend fun httpCall(url: String, headers: Map<String, String>): String {
        return redwoodClient.call(url, headers)
    }

    override fun openUrl(url: String) {
        openUrl.invoke(url)
    }
}
