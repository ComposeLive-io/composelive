package io.clive.logger

import android.util.Log


public class LogcatCliveLogger : CliveLogger {
    override fun e(message: String, exception: Exception) {
        Log.e("Clive", message, exception)

        if (exception is CliveJsException) {
            Log.e("Clive", exception.jsStackTrace.orEmpty())
        }
    }

    override fun w(message: String) {
        Log.w("Clive", message)
    }

    override fun i(message: String) {
        Log.i("Clive", message)
    }
}
