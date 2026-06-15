package io.composelive.nodes.foundation.host.composeui.images

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.network.ktor3.KtorNetworkFetcherFactory
import coil3.serviceLoaderEnabled

@Composable
public fun rememberImageLoader(createContext: () -> PlatformContext): ImageLoader =
    remember {
        createImageLoader(createContext())
    }

public fun createImageLoader(context: PlatformContext): ImageLoader = ImageLoader.Builder(context)
    .serviceLoaderEnabled(false)
    .components {
        add(KtorNetworkFetcherFactory())
    }
    .build()
