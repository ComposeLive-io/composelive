@file:OptIn(ExperimentalObjCName::class, ExperimentalObjCRefinement::class)

package io.clive.logger

import kotlin.experimental.ExperimentalObjCName
import kotlin.experimental.ExperimentalObjCRefinement
import kotlin.native.HiddenFromObjC
import kotlin.native.ObjCName

@ObjCName("CliveLogger", exact = true)
public interface CliveLogger {

    @ObjCName("error")
    public fun e(message: String, exception: Exception)

    @ObjCName("warning")
    public fun w(message: String)

    @ObjCName("info")
    public fun i(message: String)

    @HiddenFromObjC
    public companion object None : CliveLogger {
        override fun e(message: String, exception: Exception) {}
        override fun w(message: String) {}
        override fun i(message: String) {}
    }
}

@HiddenFromObjC
public fun CliveLogger.e(exception: Exception) {
    e(exception.message ?: "Unknown error", exception)
}

@ObjCName("CliveJsException", exact = true)
public class CliveJsException(
    message: String?,
    public val jsStackTrace: String?,
) : Exception(message)
