package io.composelive.designsystem.core.compose.modifiers

import app.cash.redwood.Modifier
import app.cash.redwood.ui.Dp
import app.cash.redwood.ui.dp
import io.composelive.designsystem.core.api.PaddingValues
import io.composelive.designsystem.core.compose.padding

public fun Modifier.padding(
    start: Dp = 0.dp,
    end: Dp = 0.dp,
    top: Dp = 0.dp,
    bottom: Dp = 0.dp,
): Modifier =
    padding(
        PaddingValues(
            start = start,
            end = end,
            top = top,
            bottom = bottom,
        )
    )

public fun Modifier.padding(
    horizontal: Dp = 0.dp,
    vertical: Dp = 0.dp,
): Modifier =
    padding(
        PaddingValues(
            horizontal = horizontal,
            vertical = vertical,
        )
    )

public fun Modifier.padding(all: Dp = 0.dp): Modifier =
    padding(PaddingValues(all = all))
