package io.composelive.designsystem.core.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import io.composelive.designsystem.core.api.MotionProgress

@Composable
public fun rememberMotionProgress(divideScrollBy: Double = 100.0): MotionProgress {
    val progress = remember { MotionProgress() }
    MotionProgressHolder(
        progress = progress,
        divideScrollBy = divideScrollBy,
    )
    return progress
}
