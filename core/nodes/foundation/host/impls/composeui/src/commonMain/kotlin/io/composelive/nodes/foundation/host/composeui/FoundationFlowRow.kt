package io.composelive.nodes.foundation.host.composeui

import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.composelive.nodes.foundation.common.Alignment as RedwoodAlignment
import io.composelive.nodes.foundation.common.Arrangement as RedwoodArrangement

@Composable
public fun FoundationFlowRow(
    modifier: Modifier,
    horizontalArrangement: RedwoodArrangement.Horizontal,
    verticalArrangement: RedwoodArrangement.Vertical,
    itemVerticalAlignment: RedwoodAlignment.Vertical,
    maxItemsInEachRow: Int,
    maxLines: Int,
    content: @Composable FlowRowScope.() -> Unit,
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement.toHorizontalArrangement(),
        verticalArrangement = verticalArrangement.toVerticalArrangement(),
        maxItemsInEachRow = maxItemsInEachRow,
        maxLines = maxLines,
        itemVerticalAlignment = itemVerticalAlignment.toAlignment(),
        content = content,
    )
}

