package io.clive.test.composeui

import kotlin.time.Clock
import kotlin.time.ExperimentalTime

public class TimeMeasurer {
    private val logger = Logger(tag = "TimeMeasurer")
    private var startTime: Long = 0

    private var measuredCount = 0
    private var measuredTimeSum = 0L

    public fun start() {
        startTime = now()
    }

    public fun tick(message: String) {
        val time = now() - startTime
        logger.info("$message, time=$time")
        measuredCount++
        measuredTimeSum += time
    }

    public fun printAverage() {
        val averageTime = measuredTimeSum / measuredCount
        logger.info("average, time=$averageTime")
    }

    @OptIn(ExperimentalTime::class)
    private fun now(): Long = Clock.System.now().toEpochMilliseconds()
}
