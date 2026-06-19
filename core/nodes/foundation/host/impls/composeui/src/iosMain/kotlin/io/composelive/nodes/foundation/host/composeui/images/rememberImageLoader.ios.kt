package io.composelive.nodes.foundation.host.composeui.images

import androidx.compose.runtime.Composable
import coil3.PlatformContext

@Composable
internal actual fun platformContext(): PlatformContext =
    PlatformContext.INSTANCE
