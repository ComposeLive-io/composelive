package io.clive.test.composeui

import io.clive.logger.CliveLogger

internal object TestCliveLogger : CliveLogger {

    override fun e(message: String, exception: Exception) {
        println("Clive: $message\n${exception.stackTraceToString()}")
    }

    override fun w(message: String) {
        println("Clive: $message")
    }

    override fun i(message: String) {
        println("Clive: $message")
    }
}
