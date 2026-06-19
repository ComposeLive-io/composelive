package io.clive.test.composeui

import app.cash.zipline.loader.ZiplineHttpClient
import app.cash.zipline.loader.asZiplineHttpClient
import okhttp3.OkHttpClient

internal actual fun createZiplineHttpClient(): ZiplineHttpClient =
    OkHttpClient().asZiplineHttpClient()
