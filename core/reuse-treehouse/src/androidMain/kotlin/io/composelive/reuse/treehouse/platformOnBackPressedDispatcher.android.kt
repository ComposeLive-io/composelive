package io.composelive.reuse.treehouse

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import app.cash.redwood.ui.Cancellable
import androidx.activity.OnBackPressedCallback as AndroidOnBackPressedCallback
import app.cash.redwood.ui.OnBackPressedCallback as RedwoodOnBackPressedCallback
import app.cash.redwood.ui.OnBackPressedDispatcher as RedwoodOnBackPressedDispatcher

@Composable
internal actual fun platformOnBackPressedDispatcher(): RedwoodOnBackPressedDispatcher {
    val delegate = LocalOnBackPressedDispatcherOwner.current!!.onBackPressedDispatcher
    return remember(delegate) {
        object : RedwoodOnBackPressedDispatcher {
            override fun addCallback(onBackPressedCallback: RedwoodOnBackPressedCallback): Cancellable {
                val androidOnBackPressedCallback = onBackPressedCallback.toAndroid()
                onBackPressedCallback.enabledChangedCallback = {
                    androidOnBackPressedCallback.isEnabled = onBackPressedCallback.isEnabled
                }
                delegate.addCallback(androidOnBackPressedCallback)
                return object : Cancellable {
                    override fun cancel() {
                        onBackPressedCallback.enabledChangedCallback = null
                        androidOnBackPressedCallback.remove()
                    }
                }
            }
        }
    }
}

private fun RedwoodOnBackPressedCallback.toAndroid(): AndroidOnBackPressedCallback =
    object : AndroidOnBackPressedCallback(this@toAndroid.isEnabled) {
        override fun handleOnBackPressed() {
            this@toAndroid.handleOnBackPressed()
        }
    }
