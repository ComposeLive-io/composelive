package io.composelive.nodes.foundation.host.composeui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import io.composelive.nodes.foundation.host.composeui.service.EffectWidget
import io.composelive.nodes.foundation.widget.RenderedEffectLauncher
import app.cash.redwood.Modifier as RedwoodModifier

public class FoundationRenderedEffectLauncher :
    RenderedEffectLauncher<@Composable (Modifier) -> Unit>, EffectWidget() {

    override var modifier: RedwoodModifier = RedwoodModifier

    private var renderedChanged by mutableStateOf<((Boolean) -> Unit)?>(null)

    override fun renderedChanged(renderedChanged: (Boolean) -> Unit) {
        this.renderedChanged = renderedChanged
    }

    override fun composed() {
        renderedChanged?.let { it(true) }
    }

    override fun disposed() {
        renderedChanged?.let { it(false) }
    }
}
