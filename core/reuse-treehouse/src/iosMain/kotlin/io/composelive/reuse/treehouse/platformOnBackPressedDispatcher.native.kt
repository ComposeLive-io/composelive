package io.composelive.reuse.treehouse

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import app.cash.redwood.ui.Cancellable
import app.cash.redwood.ui.OnBackPressedCallback
import app.cash.redwood.ui.OnBackPressedDispatcher

@Composable
internal actual fun platformOnBackPressedDispatcher(): OnBackPressedDispatcher {
    return remember {
        object : OnBackPressedDispatcher {
            override fun addCallback(onBackPressedCallback: OnBackPressedCallback): Cancellable =
                object : Cancellable {
                    override fun cancel() = Unit
                }
        }
    }
}
