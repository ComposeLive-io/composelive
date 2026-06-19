package io.clive.standard.host.composeui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.composelive.nodes.foundation.host.composeui.ComposeUiFoundationWidgetFactory
import io.composelive.standard.widget.StandardWidgetSystem

@Suppress("FunctionName") // Acting like a type.
public fun ComposeUiStandardWidgetSystem(): StandardWidgetSystem<@Composable ((Modifier) -> Unit)> {
    return StandardWidgetSystem(
        Foundation = ComposeUiFoundationWidgetFactory(),
        Standard = ComposeUiStandardWidgetFactory(),
    )
}
