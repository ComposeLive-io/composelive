package io.composelive.nodes.foundation.host.composeui.local

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import io.composelive.nodes.foundation.common.Color

public val LocalContentColor: ProvidableCompositionLocal<Color> =
    compositionLocalOf { Color.Unspecified }
