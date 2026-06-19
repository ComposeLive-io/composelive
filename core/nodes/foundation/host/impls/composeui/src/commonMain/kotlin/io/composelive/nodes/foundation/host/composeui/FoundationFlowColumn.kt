package io.composelive.nodes.foundation.host.composeui

import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.composelive.nodes.foundation.common.Alignment as RedwoodAlignment
import io.composelive.nodes.foundation.common.Arrangement as RedwoodArrangement

@Composable
public fun FoundationFlowColumn(
    modifier: Modifier,
    verticalArrangement: RedwoodArrangement.Vertical,
    horizontalArrangement: RedwoodArrangement.Horizontal,
    itemHorizontalAlignment: RedwoodAlignment.Horizontal,
    maxItemsInEachColumn: Int,
    maxLines: Int,
    content: @Composable FlowColumnScope.() -> Unit,
) {
    FlowColumn(
        modifier = modifier,
        verticalArrangement = verticalArrangement.toVerticalArrangement(),
        horizontalArrangement = horizontalArrangement.toHorizontalArrangement(),
        maxItemsInEachColumn = maxItemsInEachColumn,
        maxLines = maxLines,
        itemHorizontalAlignment = itemHorizontalAlignment.toAlignment(),
        content = content,
    )
}
