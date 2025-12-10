package io.composelive.app.treehouse

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
            PullToRefreshBox(
                modifier = modifier.fillMaxSize(),
                onRefresh = {},
                isRefreshing = true,
            ) {
                // Just a loader
            }
        }
    }

    private class RealCrashed : Crashed<@Composable (Modifier) -> Unit> {
        private var uncaughtException by mutableStateOf<Throwable?>(null)

        private var restart by mutableStateOf({})

        override var modifier: app.cash.redwood.Modifier = app.cash.redwood.Modifier.Companion
        override val value: @Composable (Modifier) -> Unit = { modifier ->
            LaunchedEffect(uncaughtException) {
                uncaughtException?.printStackTrace()
            }
            MainCrashed(
                restart = restart,
                modifier = modifier,
            )
        }

        override fun uncaughtException(uncaughtException: Throwable) {
            this.uncaughtException = uncaughtException
        }

        override fun restart(restart: () -> Unit) {
            this.restart = restart
        }
    }
}
