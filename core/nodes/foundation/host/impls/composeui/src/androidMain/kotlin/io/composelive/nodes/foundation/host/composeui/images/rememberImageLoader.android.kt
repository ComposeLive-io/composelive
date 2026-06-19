package io.composelive.nodes.foundation.host.composeui.images

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import coil3.PlatformContext

@Composable
internal actual fun platformContext(): PlatformContext =
    LocalContext.current