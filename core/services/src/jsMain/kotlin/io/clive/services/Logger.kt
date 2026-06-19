package io.clive.services

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import io.clive.zipline

@Composable
fun rememberLogger(): Logger = remember {
    zipline.take(name = Logger.NAME)
}

fun Logger.e(message: String, throwable: Throwable? = null) {
    e(message, throwable?.message, throwable?.stackTraceToString())
}
