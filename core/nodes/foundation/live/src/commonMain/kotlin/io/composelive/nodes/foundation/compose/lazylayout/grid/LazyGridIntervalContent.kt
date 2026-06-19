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
package io.composelive.nodes.foundation.compose.lazylayout.grid

import androidx.compose.runtime.Composable
import io.composelive.nodes.foundation.common.lazylayout.grid.GridItemSpan
import io.composelive.nodes.foundation.compose.LazyGridScope
import io.composelive.nodes.foundation.compose.lazylayout.LazyLayoutIntervalContent
import io.composelive.nodes.foundation.compose.lazylayout.MutableIntervalList

// Copied from https://github.com/androidx/androidx/blob/5c0f6611fe87e4ed29b1e5881e084581283169c1/compose/foundation/foundation/src/commonMain/kotlin/androidx/compose/foundation/lazy/LazyListIntervalContent.kt
// Removed support for keys, content types, and sticky headers.

internal class LazyGridIntervalContent(
    content: LazyGridScope.() -> Unit,
) : LazyLayoutIntervalContent<LazyGridInterval>(), LazyGridScope {
    override val intervals: MutableIntervalList<LazyGridInterval> = MutableIntervalList()

    init {
        content()
    }

    override fun items(
        count: Int,
        placeholder: @Composable (() -> Unit),
        itemContent: @Composable ((Int) -> Unit)
    ) {
        intervals.addInterval(
            count,
            LazyGridInterval(
                placeholder = placeholder,
                item = itemContent,
            ),
        )
    }

    override fun item(span: () -> GridItemSpan?, content: @Composable (() -> Unit)) {
        intervals.addInterval(
            1,
            LazyGridInterval(
                span = span(),
                placeholder = {},
                item = {
                    content()
                },
            ),
        )
    }
}

public class LazyGridInterval(
    public val span: GridItemSpan? = null,
    public override val placeholder: @Composable () -> Unit,
    public override val item: @Composable (index: Int) -> Unit,
) : LazyLayoutIntervalContent.Interval
