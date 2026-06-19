package io.composelive.nodes.foundation.host.composeui.images

import kotlinx.coroutines.suspendCancellableCoroutine
import okio.IOException
import platform.Foundation.NSData
import platform.Foundation.NSHTTPURLResponse
import platform.Foundation.NSURLRequest
import platform.Foundation.NSURLSession
import platform.Foundation.dataTaskWithRequest
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

internal class URLSessionResponse(val response: NSHTTPURLResponse, val data: NSData?)

internal suspend fun NSURLSession.execute(
    request: NSURLRequest
): URLSessionResponse = suspendCancellableCoroutine { continuation ->
    val task = dataTaskWithRequest(request) { data, response, error ->
        when {
            error != null -> {
                continuation.resumeWithException(
                    IOException(error.description() ?: "Unknown error")
                )
            }

            response is NSHTTPURLResponse -> {
                continuation.resume(URLSessionResponse(response, data))
            }

            else -> {
                continuation.resumeWithException(
                    IOException("Invalid $response for $request")
                )
            }
        }
    }
    continuation.invokeOnCancellation {
        task.cancel()
    }
    task.resume()
}
