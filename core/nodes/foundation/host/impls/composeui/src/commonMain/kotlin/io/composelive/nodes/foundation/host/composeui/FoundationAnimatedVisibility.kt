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
package io.composelive.nodes.foundation.host.composeui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.composelive.nodes.foundation.common.animation.EnterTransition
import io.composelive.nodes.foundation.common.animation.ExitTransition

@Composable
public fun FoundationAnimatedVisibility(
    visible: Boolean,
    enter: EnterTransition,
    exit: ExitTransition,
    modifier: Modifier,
    content: @Composable AnimatedVisibilityScope.() -> Unit,
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = visible,
        enter = remember(enter) { enter.toEnterAnimations() },
        exit = remember(exit) { exit.toExitTransitions() },
        content = content,
    )
}

private fun EnterTransition.toEnterAnimations() = listOfNotNull(
    if (EnterTransition.fadeIn in this) fadeIn() else null,
    if (EnterTransition.scaleIn in this) scaleIn() else null,
    if (EnterTransition.expandIn in this) expandIn(expandFrom = Alignment.Center) else null,
    if (EnterTransition.slideInHorizontally in this) slideInHorizontally() else null,
    if (EnterTransition.slideInVertically in this) slideInVertically() else null,
).reduce { a1, a2 -> a1 + a2 }

private fun ExitTransition.toExitTransitions() = listOfNotNull(
    if (ExitTransition.fadeOut in this) fadeOut() else null,
    if (ExitTransition.scaleOut in this) scaleOut() else null,
    if (ExitTransition.shrinkOut in this) shrinkOut(shrinkTowards = Alignment.Center) else null,
    if (ExitTransition.slideOutHorizontally in this) slideOutHorizontally() else null,
    if (ExitTransition.slideOutVertically in this) slideOutVertically() else null,
).reduce { a1, a2 -> a1 + a2 }
