package io.composelive.nodes.foundation.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.key

@Composable
public fun StaticBuffer(
    count: () -> Int,
    bufferSize: Int,
    item: @Composable (index: Int, inRange: Boolean) -> Unit,
) {
    for (index in 0 until bufferSize) {
        key(index) {
            item(index, index < count())
        }
    }
}
