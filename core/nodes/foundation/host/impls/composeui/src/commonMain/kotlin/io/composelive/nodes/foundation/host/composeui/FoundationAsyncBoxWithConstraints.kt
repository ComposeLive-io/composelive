package io.composelive.nodes.foundation.host.composeui

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.composelive.nodes.foundation.common.Alignment
import io.composelive.nodes.foundation.common.Constraints

@Composable
public fun FoundationAsyncBoxWithConstraints(
    modifier: Modifier,
    contentAlignment: Alignment,
    propagateMinConstraints: Boolean,
    constraintsChanged: (Constraints) -> Unit,
    content: @Composable BoxWithConstraintsScope.() -> Unit,
) {
    BoxWithConstraints(
        contentAlignment = contentAlignment.toAlignment(),
        modifier = modifier,
        propagateMinConstraints = propagateMinConstraints,
        content = content,
    )
}
