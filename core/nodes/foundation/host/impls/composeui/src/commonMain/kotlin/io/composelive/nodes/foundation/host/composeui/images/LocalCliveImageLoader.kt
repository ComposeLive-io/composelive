package io.composelive.nodes.foundation.host.composeui.images

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import coil3.ImageLoader

public val LocalCliveImageLoader: ProvidableCompositionLocal<ImageLoader?> =
    staticCompositionLocalOf { null }
