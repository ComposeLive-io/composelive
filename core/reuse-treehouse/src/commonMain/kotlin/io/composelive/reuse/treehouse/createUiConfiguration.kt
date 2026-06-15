package io.composelive.reuse.treehouse

import app.cash.redwood.ui.Margin
import app.cash.redwood.ui.UiConfiguration
import app.cash.redwood.ui.LayoutDirection as RedwoodLayoutDirection

public fun createUiConfiguration(
    safeAreaInsets: Margin,
    density: Double,
    darkMode: Boolean,
): UiConfiguration =
    UiConfiguration(
        darkMode = darkMode,
        safeAreaInsets = safeAreaInsets,
        viewInsets = Margin.Zero,
        viewportSize = null,
        density = density,
        layoutDirection = RedwoodLayoutDirection.Ltr,
    )
