package io.composelive.nodes.foundation.compose

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import io.composelive.nodes.foundation.common.DefaultIndication
import io.composelive.nodes.foundation.common.Indication

// Make NoIndication to disable indication
public val LocalIndication: ProvidableCompositionLocal<Indication> =
    compositionLocalOf { DefaultIndication }
