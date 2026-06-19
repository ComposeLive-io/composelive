package io.composelive.nodes.foundation.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import io.composelive.nodes.foundation.common.ScrollProgress

@Composable
public fun rememberMotionProgress(divideScrollBy: Double = 100.0): ScrollProgress {
    val progress = remember { ScrollProgress() }
    MotionProgressHolder(
        progress = progress,
        divideScrollBy = divideScrollBy,
    )
    return progress
}
