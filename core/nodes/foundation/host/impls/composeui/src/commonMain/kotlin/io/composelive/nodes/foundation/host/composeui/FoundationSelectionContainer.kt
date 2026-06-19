package io.composelive.nodes.foundation.host.composeui

import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlin.Unit

@Composable
public fun FoundationSelectionContainer(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    SelectionContainer(modifier = modifier, content = content)
}
