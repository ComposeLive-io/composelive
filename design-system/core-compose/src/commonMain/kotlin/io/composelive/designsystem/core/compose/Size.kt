package io.composelive.designsystem.core.compose

import app.cash.redwood.Modifier
import app.cash.redwood.ui.Dp

public fun Modifier.size(width: Dp, height: Dp): Modifier =
    width(width).height(height)

public fun Modifier.size(size: Dp): Modifier =
    width(size).height(size)

public fun Modifier.fillMaxSize(): Modifier =
    fillMaxWidth().fillMaxHeight()
