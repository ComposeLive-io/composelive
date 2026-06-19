package ru.wildberries.clive.astro.host

import app.cash.zipline.Zipline
import io.clive.logger.CliveLogger
import io.clive.logger.LoggerServiceImpl
import io.clive.services.Logger
import io.clive.services.logger.bind
import io.composelive.reuse.treehouse.HostZiplineBridge

public class StandardServiceProvider(logger: CliveLogger) : HostZiplineBridge {
    private val liveLogger: Logger = LoggerServiceImpl(logger)

    override fun bindTo(zipline: Zipline) {
        with(zipline) {
            liveLogger.bind()
        }
    }
}
