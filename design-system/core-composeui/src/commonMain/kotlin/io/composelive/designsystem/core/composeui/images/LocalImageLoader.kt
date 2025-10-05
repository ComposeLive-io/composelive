package io.composelive.designsystem.core.composeui.images

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import coil3.ImageLoader

public val LocalImageLoader: ProvidableCompositionLocal<ImageLoader?> =
    compositionLocalOf { null }
