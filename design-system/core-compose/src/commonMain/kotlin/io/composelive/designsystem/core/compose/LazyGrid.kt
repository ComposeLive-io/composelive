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
@file:JvmName("LazyGrid") // Conflicts with generated LazyGrid compose widget

package io.composelive.designsystem.core.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import app.cash.redwood.Modifier
import io.composelive.designsystem.core.api.Arrangement
import io.composelive.designsystem.core.api.MotionProgress
import io.composelive.designsystem.core.compose.lazygrid.LazyGridScope
import io.composelive.designsystem.core.compose.lazygrid.LazyGridState
import io.composelive.designsystem.core.compose.lazygrid.rememberLazyListItemProvider
import kotlin.jvm.JvmName

@Composable
public fun LazyGrid(
    isVertical: Boolean,
    state: LazyGridState,
    chunks: Int,
    horizontalArrangement: Arrangement?,
    verticalArrangement: Arrangement?,
    boundMotionProgress: MotionProgress?,
    modifier: Modifier = Modifier,
    content: LazyGridScope.() -> Unit,
) {
    val itemProvider = rememberLazyListItemProvider(content)
    val spans by derivedStateOf { itemProvider.spans() }
    val itemCount = itemProvider.itemCount
//    val loadRange = state.loadRange(itemCount)
    LazyGrid(
        isVertical = isVertical,
        onViewportChanged = { localFirstVisibleItemIndex, localLastVisibleItemIndex ->
            state.onUserScroll(localFirstVisibleItemIndex, localLastVisibleItemIndex)
        },
        programmaticScrollIndex = state.programmaticScrollIndex,
        chunks = chunks,
        horizontalArrangement = horizontalArrangement,
        verticalArrangement = verticalArrangement,
        boundMotionProgress = boundMotionProgress,
        spans = spans,
        modifier = modifier,
        items = {
            for (index in 0 until itemCount) {
                key(index) {
                    itemProvider.Item(scope = this, index)
                }
            }
        },
    )
}
