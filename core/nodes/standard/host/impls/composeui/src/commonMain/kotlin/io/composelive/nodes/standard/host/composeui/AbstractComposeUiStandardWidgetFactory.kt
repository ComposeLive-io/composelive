package io.composelive.nodes.standard.host.composeui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.composelive.nodes.standard.widget.StandardWidgetFactory

public abstract class AbstractComposeUiStandardWidgetFactory : StandardWidgetFactory<@Composable (Modifier) -> Unit>