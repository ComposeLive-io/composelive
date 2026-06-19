package io.composelive.nodes.foundation.host.composeui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.composelive.nodes.foundation.common.Alignment
import io.composelive.nodes.foundation.common.Arrangement

@Composable
public fun FoundationColumn(
    modifier: Modifier,
    verticalArrangement: Arrangement.Vertical,
    horizontalAlignment: Alignment.Horizontal,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = verticalArrangement.toVerticalArrangement(),
        horizontalAlignment = horizontalAlignment.toAlignment(),
        content = content,
    )
}
