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
package io.composelive.designsystem.core.composeui

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import io.composelive.designsystem.core.api.Color as RedwoodColor
import io.composelive.designsystem.core.api.Shape as RedwoodShape

@Composable
public fun CoreFloatingActionButton(
    onClick: (() -> Unit)?,
    shape: RedwoodShape?,
    containerColor: RedwoodColor?,
    contentColor: RedwoodColor?,
    modifier: Modifier,
    content: @Composable () -> Unit,
) {
    val containerColor = containerColor?.toColor() ?: FloatingActionButtonDefaults.containerColor
    FloatingActionButton(
        onClick = onClick ?: {},
        shape = remember(shape) { shape?.toShape() } ?: FloatingActionButtonDefaults.shape,
        containerColor = containerColor,
        contentColor = contentColor?.toColor() ?: contentColorFor(containerColor),
        modifier = modifier,
        content = content,
    )
}
