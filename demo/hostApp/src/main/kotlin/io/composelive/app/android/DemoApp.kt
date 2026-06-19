package io.composelive.app.android

import android.app.Application
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy


class DemoApp : Application() {
    override fun onCreate() {
        super.onCreate()

        StrictMode.setThreadPolicy(
            ThreadPolicy.Builder()
                .detectAll()
                .penaltyDeath()
                .build()
        )
    }
}
