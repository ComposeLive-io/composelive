package io.clive.test.composeui

import app.cash.zipline.loader.ZiplineHttpClient
import app.cash.zipline.loader.asZiplineHttpClient
import platform.Foundation.NSURLSession

internal actual fun createZiplineHttpClient(): ZiplineHttpClient =
    NSURLSession.sharedSession.asZiplineHttpClient()
