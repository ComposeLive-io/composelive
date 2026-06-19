package io.clive.logger


internal class ScopedCliveLogger(
    private val scope: String,
    private val logger: CliveLogger,
) : CliveLogger {
    override fun e(message: String, exception: Exception) =
        logger.e("${scope}: $message", exception)

    override fun w(message: String) =
        logger.w("${scope}: $message")

    override fun i(message: String) =
        logger.i("${scope}: $message")
}
