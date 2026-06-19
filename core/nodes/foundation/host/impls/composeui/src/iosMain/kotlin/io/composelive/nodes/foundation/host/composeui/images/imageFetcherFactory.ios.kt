package io.composelive.nodes.foundation.host.composeui.images

import coil3.Uri
import coil3.annotation.ExperimentalCoilApi
import coil3.fetch.Fetcher
import coil3.network.NetworkClient
import coil3.network.NetworkFetcher
import coil3.network.NetworkHeaders
import coil3.network.NetworkRequest
import coil3.network.NetworkRequestBody
import coil3.network.NetworkResponse
import coil3.network.NetworkResponseBody
import okio.Buffer
import platform.Foundation.NSData
import platform.Foundation.NSMutableURLRequest
import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.Foundation.NSURLSession
import platform.Foundation.addValue
import platform.Foundation.setHTTPBody
import platform.Foundation.setHTTPMethod

@OptIn(ExperimentalCoilApi::class)
internal actual fun imageFetcherFactory(): Fetcher.Factory<Uri> = NetworkFetcher.Factory(
    networkClient = { URLSessionNetworkClient(NSURLSession.sharedSession) },
)

private class URLSessionNetworkClient(
    private val session: NSURLSession
) : NetworkClient {

    override suspend fun <T> executeRequest(
        request: NetworkRequest,
        block: suspend (response: NetworkResponse) -> T
    ): T {
        val response = session.execute(request.toNSURLRequest())
        return block(response.toNetworkResponse())
    }
}

private suspend fun NetworkRequest.toNSURLRequest(): NSURLRequest {
    return NSMutableURLRequest(NSURL.URLWithString(url)!!).apply {
        setHTTPMethod(method)
        headers.asMap().forEach { (name, values) ->
            values.forEach { addValue(it, name) }
        }
        body?.let {
            setHTTPBody(it.toNSData())
        }
    }
}

private fun URLSessionResponse.toNetworkResponse(): NetworkResponse {
    val headers = NetworkHeaders.Builder()
    response.allHeaderFields.forEach { (name, value) ->
        headers[name.toString()] = value.toString()
    }
    return NetworkResponse(
        code = response.statusCode.toInt(),
        headers = headers.build(),
        body = data?.toNetworkResponseBody()
    )
}

private fun NSData.toNetworkResponseBody() =
    NetworkResponseBody(toBuffer())

private suspend fun NetworkRequestBody.toNSData(): NSData {
    val buffer = Buffer()
    writeTo(buffer)
    return buffer.toNSData()
}
