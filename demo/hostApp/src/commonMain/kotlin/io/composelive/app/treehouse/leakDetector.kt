package io.composelive.app.treehouse

import app.cash.redwood.leaks.LeakDetector
import app.cash.redwood.leaks.RedwoodLeakApi
import kotlinx.coroutines.CoroutineScope
import kotlin.time.Duration.Companion.seconds
import kotlin.time.TimeSource

@OptIn(RedwoodLeakApi::class)
fun leakDetector(scope: CoroutineScope) = LeakDetector.timeBasedIn(
    scope = scope,
    timeSource = TimeSource.Monotonic,
    leakThreshold = 10.seconds,
    callback = { reference, note ->
        println("LEAK: Leak detected! $reference $note")
    },
)
