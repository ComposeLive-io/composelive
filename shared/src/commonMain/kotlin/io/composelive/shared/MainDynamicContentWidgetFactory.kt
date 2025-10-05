package io.composelive.shared

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import app.cash.redwood.treehouse.Crashed
import app.cash.redwood.treehouse.DynamicContentWidgetFactory
import app.cash.redwood.treehouse.Loading

class MainDynamicContentWidgetFactory :
    DynamicContentWidgetFactory<@Composable (Modifier) -> Unit> {

    override fun Loading(): Loading<@Composable (Modifier) -> Unit> = RealLoading()

    override fun Crashed(): Crashed<@Composable (Modifier) -> Unit> = RealCrashed()

    private class RealLoading : Loading<@Composable (Modifier) -> Unit> {
        override var modifier: app.cash.redwood.Modifier = app.cash.redwood.Modifier.Companion
        override val value: @Composable (Modifier) -> Unit = { modifier ->
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Companion.Center,
            ) {
                BasicText("loading...")
            }
        }
    }

    private class RealCrashed : Crashed<@Composable (Modifier) -> Unit> {
        private var uncaughtException by mutableStateOf<Throwable?>(null)

        override var modifier: app.cash.redwood.Modifier = app.cash.redwood.Modifier.Companion
        override val value: @Composable (Modifier) -> Unit = { modifier ->
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Companion.Center,
            ) {
                BasicText(uncaughtException?.stackTraceToString() ?: "")
            }
        }

        override fun uncaughtException(uncaughtException: Throwable) {
            this.uncaughtException = uncaughtException
        }

        override fun restart(restart: () -> Unit) {
        }
    }
}
