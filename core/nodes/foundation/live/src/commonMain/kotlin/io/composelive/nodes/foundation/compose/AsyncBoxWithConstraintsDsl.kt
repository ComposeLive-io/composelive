package io.composelive.nodes.foundation.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import app.cash.redwood.Modifier
import io.composelive.nodes.foundation.common.Constraints

/**
 * A [BoxWithConstraints]-style layout that receives constraints asynchronously.
 *
 * Layout and measurement run on the host (Compose UI), while this function runs on the guest.
 * There is no shared composition tree, so the host measures, then sends constraints back via
 * [constraintsChanged]; [content] is composed with the latest constraints when they arrive.
 * No real subcomposition happens—constraints are at least one frame behind the host layout.
 *
 */
@Composable
public fun AsyncBoxWithConstraints(
    propagateMinConstraints: Boolean = false,
    modifier: Modifier = Modifier,
    content: @Composable (Constraints) -> Unit,
) {
    var constraints by remember { mutableStateOf<Constraints?>(null) }
    AsyncBoxWithConstraints(
        propagateMinConstraints = propagateMinConstraints,
        constraintsChanged = { values ->
            constraints = values
        },
        modifier = modifier,
        content = {
            constraints?.let {
                content(it)
            }
        },
    )
}
