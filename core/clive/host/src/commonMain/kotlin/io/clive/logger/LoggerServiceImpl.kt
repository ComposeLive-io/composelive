@file:OptIn(ExperimentalObjCRefinement::class)

package io.clive.logger

import io.clive.services.Logger
import kotlin.experimental.ExperimentalObjCRefinement
import kotlin.native.HiddenFromObjC

@HiddenFromObjC
public class LoggerServiceImpl(private val logger: CliveLogger) : Logger {

    override fun i(message: String) {
        logger.i(message)
    }

    override fun w(message: String) {
        logger.w(message)
    }

    override fun e(
        message: String,
        exceptionMessage: String?,
        stackTrace: String?
    ) {
        val e = CliveJsException(exceptionMessage, stackTrace)
        logger.e(message, e)
    }
}
