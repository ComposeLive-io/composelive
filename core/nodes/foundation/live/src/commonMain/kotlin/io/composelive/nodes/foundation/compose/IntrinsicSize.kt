package io.composelive.nodes.foundation.compose

import app.cash.redwood.Modifier
import io.composelive.nodes.foundation.common.IntrinsicSize

public fun Modifier.width(size: IntrinsicSize): Modifier =
    intrinsicWidth(size)

public fun Modifier.height(size: IntrinsicSize): Modifier =
    intrinsicHeight(size)

public fun Modifier.size(width: IntrinsicSize, height: IntrinsicSize): Modifier =
    width(width).height(height)

public fun Modifier.size(size: IntrinsicSize): Modifier =
    width(size).height(size)
