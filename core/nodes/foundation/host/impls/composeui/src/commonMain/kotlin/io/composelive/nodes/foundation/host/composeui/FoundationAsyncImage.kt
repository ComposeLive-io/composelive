/*
 * Copyright (C) 2022 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.composelive.nodes.foundation.host.composeui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import io.composelive.nodes.foundation.host.composeui.images.LocalImageLoader

@Composable
public fun FoundationAsyncImage(
    model: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        modifier = modifier,
        contentScale = ContentScale.Crop,
        model = if (model.isNotEmpty()) {
            ImageRequest.Builder(LocalPlatformContext.current)
                .data(model)
                .crossfade(true)
                .build()
        } else {
            null
        },
        imageLoader = requireNotNull(LocalImageLoader.current) {
            "LocalImageLoader must be set"
        },
        contentDescription = null,
    )
}
