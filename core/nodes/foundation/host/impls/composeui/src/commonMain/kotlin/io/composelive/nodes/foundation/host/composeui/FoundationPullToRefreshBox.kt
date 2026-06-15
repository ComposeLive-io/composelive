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

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
public fun FoundationPullToRefreshBox(
    isRefreshing: Boolean, // Manual set
    onRefresh: () -> Unit,
    modifier: Modifier,
    content: @Composable BoxScope.() -> Unit,
) {
    var isRefreshing by remember(isRefreshing) { mutableStateOf(isRefreshing) }
    PullToRefreshBox(
        modifier = modifier,
        isRefreshing = isRefreshing,
        onRefresh = {
            // This looks strange, but the other platforms all assume that `refreshing = true` after
            // onRefresh is called. To maintain consistency we do the same, otherwise the refresh
            // indicator disappears whilst we wait for the presenter to send `refreshing = true`
            isRefreshing = true
            onRefresh()
        },
        content = content,
    )
}
