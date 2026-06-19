package io.composelive.nodes.foundation.host.composeui.images

import coil3.Uri
import coil3.fetch.Fetcher

internal expect fun imageFetcherFactory(): Fetcher.Factory<Uri>
