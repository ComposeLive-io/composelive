package io.composelive.designsystem.core.compose.lazygrid

import io.composelive.designsystem.core.api.lazygrid.GridItemSpan

public data class LazyGridIntervalProperties(
    val span: ((index: Int) -> GridItemSpan)?,
)
