package io.clive

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import io.composelive.nodes.foundation.common.LayoutMetadata

val LocalLayoutMetadata: ProvidableCompositionLocal<LayoutMetadata> =
    staticCompositionLocalOf { error("metadata wasn't set") }
