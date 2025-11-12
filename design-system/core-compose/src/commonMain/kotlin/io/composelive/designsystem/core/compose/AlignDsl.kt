package io.composelive.designsystem.core.compose

import androidx.compose.runtime.Stable
import app.cash.redwood.Modifier
import io.composelive.designsystem.core.api.Alignment

@Stable
context(scope: RowScope)
public fun Modifier.align(alignment: Alignment.Vertical): Modifier =
    with(scope) { alignVertically(alignment) }

@Stable
context(scope: ColumnScope)
public fun Modifier.align(alignment: Alignment.Horizontal): Modifier =
    with(scope) { alignHorizontally(alignment) }
