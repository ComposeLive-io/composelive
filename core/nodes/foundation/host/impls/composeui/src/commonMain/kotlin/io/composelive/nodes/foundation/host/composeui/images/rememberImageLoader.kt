package io.composelive.nodes.foundation.host.composeui.images

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.serviceLoaderEnabled

@Composable
public fun rememberImageLoader(): ImageLoader {
    val context = platformContext()
    val loader = rememberImageLoader { context }
    DisposableEffect(loader) {
        onDispose {
            loader.shutdown()
        }
    }
    return loader
}

@Composable
public fun rememberImageLoader(getContext: () -> PlatformContext): ImageLoader =
    remember { createImageLoader(getContext()) }

public fun createImageLoader(context: PlatformContext): ImageLoader = ImageLoader.Builder(context)
    .serviceLoaderEnabled(false)
    .components {
        add(imageFetcherFactory())
    }
    .build()

@Composable
internal expect fun platformContext(): PlatformContext
