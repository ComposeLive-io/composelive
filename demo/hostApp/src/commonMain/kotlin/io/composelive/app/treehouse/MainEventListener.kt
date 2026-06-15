package io.composelive.app.treehouse

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import app.cash.redwood.treehouse.EventListener
import app.cash.zipline.Zipline
import app.cash.zipline.ZiplineManifest

internal class MainEventListener : EventListener() {
    var showError by mutableStateOf(false)
        private set

    private var contentLoaded = false

    private var previousManifest: ZiplineManifest? = null
    var contentReloadedCounter by mutableIntStateOf(0)

    override fun codeLoadFailed(exception: Exception, startValue: Any?) {
        println("Treehouse: codeLoadFailed, exception=${exception.stackTraceToString()}")
        if (!contentLoaded) {
            showError = true
        }
    }

    override fun codeLoadSuccess(
        manifest: ZiplineManifest,
        zipline: Zipline,
        startValue: Any?
    ) {
        contentLoaded = true
        showError = false
        println("Treehouse: codeLoadSuccess")
        if (manifest != previousManifest) {
            previousManifest = manifest
            contentReloadedCounter++
        }
    }
}
