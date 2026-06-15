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
@file:JvmName("LazyList") // Conflicts with generated LazyList compose widget

package io.composelive.nodes.foundation.compose.lazygrid

import androidx.collection.mutableIntObjectMapOf
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import app.cash.redwood.Modifier
import app.cash.redwood.RedwoodCodegenApi
import io.composelive.nodes.foundation.common.Arrangement
import io.composelive.nodes.foundation.common.MotionProgress
import io.composelive.nodes.foundation.compose.LazyGrid
import io.composelive.nodes.foundation.compose.LazyGridScope
import io.composelive.nodes.foundation.compose.LazyItems
import kotlin.jvm.JvmName

@OptIn(RedwoodCodegenApi::class)
@Composable
internal fun LazyGrid(
    isVertical: Boolean,
    state: LazyGridState,
    chunks: Int = 1,
    horizontalArrangement: Arrangement? = null,
    verticalArrangement: Arrangement? = null,
    boundMotionProgress: MotionProgress? = null,
    modifier: Modifier = Modifier,
    content: LazyGridScope.() -> Unit,
) {
    val itemProvider = rememberLazyListItemProvider(content)
    val intervals = itemProvider.listContent.intervals
    val intervalsCount = intervals.intervalsCount
    val loadRangeFirsts = remember(intervalsCount) { IntArray(intervalsCount) }
    val loadRangeLasts = remember(intervalsCount) { IntArray(intervalsCount) }
    state.loadRanges(
        intervals,
        writeFirstsTo = loadRangeFirsts,
        writeLastsTo = loadRangeLasts,
    )
    var lastReceivedViewportChangedId by remember { mutableIntStateOf(-1) }
    LazyGrid(
        isVertical = isVertical,
        onViewportChanged = { localFirstVisibleItemIndex, localLastVisibleItemIndex, viewportChangeId ->
            state.onUserScroll(localFirstVisibleItemIndex, localLastVisibleItemIndex)
            lastReceivedViewportChangedId = viewportChangeId
        },
        lastReceivedViewportChangedId = lastReceivedViewportChangedId,
        chunks = chunks,
        horizontalArrangement = horizontalArrangement,
        verticalArrangement = verticalArrangement,
        boundMotionProgress = boundMotionProgress,
        modifier = modifier,
        scrollItemIndex = state.programmaticScrollIndex,
        items = {
            for (intervalIndex in 0 until intervalsCount) {
                val interval = intervals.getInterval(intervalIndex)
                val loadRangeFirst = loadRangeFirsts[intervalIndex]
                val loadRangeLast = loadRangeLasts[intervalIndex]
                val loadRangeSize = (loadRangeLast - loadRangeFirst) + 1
                val keyRecycler = remember(intervalIndex) { KeyRecycler() }
                key(intervalIndex) {
                    LazyItems(
                        itemsBefore = loadRangeFirst,
                        itemsAfter = (interval.size - loadRangeSize - loadRangeFirst)
                            .coerceIn(0, interval.size),
                        span = interval.value.span,
                        modifier = Modifier,
                        placeholder = interval.value.placeholder,
                        items = {
                            keyRecycler.loadRangeUpdated(loadRangeFirst, loadRangeLast)
                            for (virtualIndex in loadRangeFirst..loadRangeLast) {
                                val key = keyRecycler.keyFor(virtualIndex)
                                key(key) {
                                    interval.value.item(virtualIndex)
                                }
                            }
                        },
                    )
                }
            }
        },
    )
}

@Stable
private class KeyRecycler {
    private var counter = 0
    private val currentKeys = mutableIntObjectMapOf<Int>()
    private val keysRecyclePool = mutableListOf<Int>()

    fun loadRangeUpdated(first: Int, last: Int) {
        currentKeys.removeIf { index, key ->
            val needRecycle = index !in first..last
            if (needRecycle) {
                keysRecyclePool.add(key)
            }
            needRecycle
        }
    }

    private fun generate(): Int = counter.also {
        counter++
    }

    fun keyFor(index: Int): Int = when {
        index in currentKeys -> {
            val key = currentKeys[index]!!
            key
        }

        keysRecyclePool.isNotEmpty() -> {
            val key = keysRecyclePool.removeFirst()
            currentKeys[index] = key
            key
        }

        else -> {
            val key = generate()
            currentKeys[index] = key
            key
        }
    }
}
