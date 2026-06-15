package io.composelive.nodes.foundation.common

import androidx.compose.runtime.Stable
import app.cash.redwood.ui.Dp
import app.cash.redwood.ui.Margin
import app.cash.redwood.ui.dp

public typealias PaddingValues = Margin

@Stable
public fun PaddingValues(
    horizontal: Dp = 0.dp,
    vertical: Dp = 0.dp,
): PaddingValues = PaddingValues(horizontal, horizontal, vertical, vertical)

@Stable
public fun PaddingValues(
    all: Dp = 0.dp,
): PaddingValues = PaddingValues(all, all, all, all)
