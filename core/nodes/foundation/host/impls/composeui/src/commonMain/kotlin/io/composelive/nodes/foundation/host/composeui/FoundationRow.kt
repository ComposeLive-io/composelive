package io.composelive.nodes.foundation.host.composeui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.composelive.nodes.foundation.common.Alignment
import io.composelive.nodes.foundation.common.Arrangement

@Composable
public fun FoundationRow(
    modifier: Modifier,
    horizontalArrangement: Arrangement.Horizontal,
    verticalAlignment: Alignment.Vertical,
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement.toHorizontalArrangement(),
        verticalAlignment = verticalAlignment.toAlignment(),
        content = content,
    )
}
