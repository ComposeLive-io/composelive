package io.composelive.nodes.foundation.compose

import androidx.compose.runtime.Stable
import app.cash.redwood.Modifier
import io.composelive.nodes.foundation.common.Brush
import io.composelive.nodes.foundation.common.RectangleShape
import io.composelive.nodes.foundation.common.Shape

@Stable
public fun Modifier.background(
    brush: Brush,
    shape: Shape = RectangleShape,
): Modifier = brushBackground(brush, shape)
