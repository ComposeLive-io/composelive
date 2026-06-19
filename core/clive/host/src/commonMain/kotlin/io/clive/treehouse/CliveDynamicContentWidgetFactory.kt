package io.clive.treehouse

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.cash.redwood.treehouse.Crashed
import app.cash.redwood.treehouse.DynamicContentWidgetFactory
import app.cash.redwood.treehouse.Loading
import io.clive.logger.CliveLogger
import io.clive.logger.e
import app.cash.redwood.Modifier as RedwoodModifier

public class CliveDynamicContentWidgetFactory(
    private val logger: CliveLogger,
) : DynamicContentWidgetFactory<@Composable (Modifier) -> Unit> {

    override fun Loading(): Loading<@Composable (Modifier) -> Unit> = RealLoading()

    override fun Crashed(): Crashed<@Composable (Modifier) -> Unit> = RealCrashed(logger)

    private class RealLoading : Loading<@Composable (Modifier) -> Unit> {
        override var modifier: RedwoodModifier = RedwoodModifier
        override val value: @Composable (Modifier) -> Unit = {}
    }

    private class RealCrashed(
        private val logger: CliveLogger
    ) : Crashed<@Composable (Modifier) -> Unit> {
        override var modifier: RedwoodModifier = RedwoodModifier
        override val value: @Composable (Modifier) -> Unit = {}

        override fun uncaughtException(uncaughtException: Throwable) {
            if (uncaughtException is Exception) {
                logger.e(uncaughtException)
            } else {
                throw uncaughtException
            }
        }

        override fun restart(restart: () -> Unit) {}
    }
}
