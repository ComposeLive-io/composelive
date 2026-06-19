package io.composelive.nodes.foundation.common

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@[Immutable Serializable]
public data class TextIndent(
    val firstLine: TextUnit = 0.sp,
    val restLine: TextUnit = 0.sp,
)
