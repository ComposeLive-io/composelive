package io.composelive.reuse.treehouse

import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import kotlin.experimental.ExperimentalObjCName

@OptIn(ExperimentalObjCName::class)
@ObjCName(name = "CliveRectSize", exact = true)
public data class CliveRectSize(val width: Double, val height: Double)

internal fun IntSize.rectSize(density: Density) = CliveRectSize(
    width.toDouble() / density.density,
    height.toDouble() / density.density,
)