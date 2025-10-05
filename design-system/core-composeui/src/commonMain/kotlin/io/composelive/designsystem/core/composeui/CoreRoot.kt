/*
 * Copyright (C) 2024 Square, Inc.
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
package io.composelive.designsystem.core.composeui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import coil3.ImageLoader
import io.composelive.designsystem.core.composeui.images.LocalImageLoader

@Composable
public fun CoreRoot(
    imageLoader: ImageLoader? = null,
    modifier: Modifier,
    content: @Composable () -> Unit,
) {
    if (imageLoader != null) {
        CompositionLocalProvider(
            LocalImageLoader provides imageLoader
        ) {
            content()
        }
    } else {
        content()
    }
}
