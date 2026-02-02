package io.composelive.nodes.foundation.composeui.images

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import coil3.ImageLoader

public val LocalImageLoader: ProvidableCompositionLocal<ImageLoader?> =
    compositionLocalOf { null }
