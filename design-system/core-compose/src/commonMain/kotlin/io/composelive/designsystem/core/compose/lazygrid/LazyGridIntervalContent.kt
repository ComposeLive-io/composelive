/*
 * Copyright 2021 The Android Open Source Project
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
package io.composelive.designsystem.core.compose.lazygrid

import androidx.compose.runtime.Composable
import io.composelive.designsystem.core.api.lazygrid.GridItemSpan
import io.composelive.designsystem.core.compose.lazygrid.layout.LazyLayoutIntervalContent
import io.composelive.designsystem.core.compose.lazygrid.layout.MutableIntervalList

// Copied from https://github.com/androidx/androidx/blob/5c0f6611fe87e4ed29b1e5881e084581283169c1/compose/foundation/foundation/src/commonMain/kotlin/androidx/compose/foundation/lazy/LazyListIntervalContent.kt
// Removed support for keys, content types, and sticky headers.

internal class LazyGridIntervalContent(
    content: LazyGridScope.() -> Unit,
) : LazyLayoutIntervalContent<LazyGridInterval>(), LazyGridScope {
    override val intervals: MutableIntervalList<LazyGridInterval> = MutableIntervalList()

    init {
        apply(content)
    }

    override fun items(
        count: Int,
        span: ((Int) -> GridItemSpan)?,
        itemContent: @Composable (index: Int) -> Unit,
    ) {
        intervals.addInterval(
            count,
            index = intervals.size,
            LazyGridInterval(
                item = itemContent,
            ),
            span = span,
        )
    }

    override fun item(span: ((Int) -> GridItemSpan)?, content: @Composable () -> Unit) {
        intervals.addInterval(
            1,
            index = intervals.size,
            LazyGridInterval(
                item = { _ -> content() },
            ),
            span = span,
        )
    }
}

internal class LazyGridInterval(
    val item: @Composable (index: Int) -> Unit,
) : LazyLayoutIntervalContent.Interval
