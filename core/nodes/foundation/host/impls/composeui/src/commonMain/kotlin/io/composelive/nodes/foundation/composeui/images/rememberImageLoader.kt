package io.composelive.nodes.foundation.composeui.images

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.network.ktor3.KtorNetworkFetcherFactory
import coil3.serviceLoaderEnabled

@Composable
public fun rememberImageLoader(createContext: () -> PlatformContext): ImageLoader = remember {
    ImageLoader.Builder(createContext())
        .serviceLoaderEnabled(false)
        .components {
            add(KtorNetworkFetcherFactory())
        }
        .build()
}
