package io.composelive.nodes.foundation.host.composeui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
public fun FoundationColumn(
    modifier: Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(modifier = modifier, content = content)
}
