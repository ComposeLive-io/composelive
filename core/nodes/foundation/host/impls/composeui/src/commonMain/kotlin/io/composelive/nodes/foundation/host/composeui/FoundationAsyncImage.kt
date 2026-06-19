package io.composelive.nodes.foundation.host.composeui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import io.composelive.nodes.foundation.common.Alignment
import io.composelive.nodes.foundation.common.AsyncImageState
import io.composelive.nodes.foundation.common.Color
import io.composelive.nodes.foundation.common.ContentScale
import io.composelive.nodes.foundation.common.FilterQuality
import io.composelive.nodes.foundation.host.composeui.images.LocalCliveImageLoader
import io.composelive.nodes.foundation.host.composeui.local.LocalContentColor
import kotlin.Float

@Composable
public fun FoundationAsyncImage(
    model: String,
    modifier: Modifier,
    contentDescription: String?,
    onState: ((AsyncImageState) -> Unit)?,
    alignment: Alignment,
    contentScale: ContentScale,
    alpha: Float,
    filterQuality: FilterQuality,
    clipToBounds: Boolean,
    tintColor: Color,
) {
    val resolvedTintColor = when {
        tintColor != Color.Unspecified -> tintColor
        LocalContentColor.current != Color.Unspecified -> LocalContentColor.current
        else -> null
    }
    val resolvedColorFilter = resolvedTintColor?.let { ColorFilter.tint(it.toColor()) }
    AsyncImage(
        modifier = modifier,
        model = if (model.isNotEmpty()) {
            ImageRequest.Builder(LocalPlatformContext.current)
                .data(model)
                .crossfade(true)
                .build()
        } else {
            null
        },
        imageLoader = requireNotNull(LocalCliveImageLoader.current) {
            "LocalImageLoader must be set"
        },
        contentDescription = contentDescription,
        onState = { state ->
            onState?.invoke(
                when (state) {
                    is AsyncImagePainter.State.Empty -> AsyncImageState.Empty
                    is AsyncImagePainter.State.Loading -> AsyncImageState.Loading
                    is AsyncImagePainter.State.Success -> AsyncImageState.Success
                    is AsyncImagePainter.State.Error -> AsyncImageState.Error(state.result.throwable.message)
                }
            )
        },
        alignment = alignment.toAlignment(),
        contentScale = contentScale.toContentScale(),
        alpha = alpha,
        filterQuality = filterQuality.toFilterQuality(),
        clipToBounds = clipToBounds,
        colorFilter = resolvedColorFilter,
    )
}
