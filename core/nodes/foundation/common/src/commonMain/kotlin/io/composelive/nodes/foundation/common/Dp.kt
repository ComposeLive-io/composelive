package io.composelive.nodes.foundation.common

import app.cash.redwood.ui.Dp
import app.cash.redwood.ui.dp

public val Dp.Companion.Unspecified: Dp get() = DP_UNSPECIFIED.dp

public const val DP_UNSPECIFIED: Int = Int.MAX_VALUE