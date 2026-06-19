package io.composelive.nodes.foundation.host.composeui.images

import coil3.Uri
import coil3.fetch.Fetcher
import coil3.network.ktor3.KtorNetworkFetcherFactory

internal actual fun imageFetcherFactory(): Fetcher.Factory<Uri> =
    KtorNetworkFetcherFactory()
