package io.composelive.nodes.foundation.compose

import androidx.compose.runtime.Stable
import app.cash.redwood.Modifier
import io.composelive.nodes.foundation.common.Alignment

@Stable
context(scope: RowScope)
public fun Modifier.align(alignment: Alignment.Vertical): Modifier =
    with(scope) { alignVertically(alignment) }

@Stable
context(scope: ColumnScope)
public fun Modifier.align(alignment: Alignment.Horizontal): Modifier =
    with(scope) { alignHorizontally(alignment) }
