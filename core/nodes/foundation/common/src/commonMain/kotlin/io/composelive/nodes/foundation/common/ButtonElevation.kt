package io.composelive.nodes.foundation.common

import androidx.compose.runtime.Immutable
import app.cash.redwood.ui.Dp
import app.cash.redwood.ui.dp
import kotlinx.serialization.Serializable

@[Immutable Serializable]
public data class ButtonElevation(
    val defaultElevation: Dp,
    val pressedElevation: Dp,
    val focusedElevation: Dp,
    val hoveredElevation: Dp,
    val disabledElevation: Dp,
) {
    public companion object {
        public val buttonElevation: ButtonElevation = ButtonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            focusedElevation = 0.dp,
            hoveredElevation = 1.dp,
            disabledElevation = 0.dp,
        )

        public val elevatedButtonElevation: ButtonElevation = ButtonElevation(
            defaultElevation = 1.dp,
            pressedElevation = 1.dp,
            focusedElevation = 1.dp,
            hoveredElevation = 2.dp,
            disabledElevation = 0.dp,
        )
    }
}
