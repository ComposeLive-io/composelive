package io.composelive.nodes.foundation.host.composeui.service

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import app.cash.redwood.widget.Widget

/**
 * A widget that has no UI when composed.
 * Ignored in the single-widget-only check of [io.composelive.nodes.foundation.widget.ReuseNode].
 */
public abstract class EffectWidget : Widget<@Composable (Modifier) -> Unit> {

    final override val value: @Composable ((Modifier) -> Unit) = {
        DisposableEffect(Unit) {
            composed()
            onDispose {
                disposed()
            }
        }
    }

    public open fun composed() {}
    public open fun disposed() {}
}
