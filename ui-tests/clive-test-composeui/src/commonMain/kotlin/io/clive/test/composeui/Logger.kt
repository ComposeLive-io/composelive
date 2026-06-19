package io.clive.test.composeui

public class Logger(private val tag: String) {

    public fun info(message: String) {
        println("$tag: $message")
    }
}
