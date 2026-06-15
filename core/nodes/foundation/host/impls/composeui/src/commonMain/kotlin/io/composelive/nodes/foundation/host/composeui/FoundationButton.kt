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

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.composelive.nodes.foundation.common.ButtonColors as RedwoodButtonColors
import io.composelive.nodes.foundation.common.Shape as RedwoodShape

@Composable
public fun FoundationButton(
    enabled: Boolean,
    shape: RedwoodShape?,
    colors: RedwoodButtonColors,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        onClick = onClick ?: {},
        enabled = enabled,
        colors = ButtonColors(
            containerColor = colors.containerColor.toColor(),
            contentColor = colors.contentColor.toColor(),
            disabledContainerColor = colors.disabledContainerColor.toColor(),
            disabledContentColor = colors.disabledContentColor.toColor(),
        ),
        shape = shape?.toShape() ?: ButtonDefaults.shape,
        modifier = modifier,
        content = content,
    )
}
