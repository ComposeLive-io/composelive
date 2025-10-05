package io.composelive.designsystem.core.api

import androidx.compose.runtime.Immutable
import app.cash.redwood.ui.Dp
import app.cash.redwood.ui.dp
import kotlinx.serialization.Serializable

@[Immutable Serializable]
public sealed class Shape {

    @[Immutable Serializable]
    public data class RoundedCorner(
        public val topStart: Dp = 0.dp,
        public val topEnd: Dp = 0.dp,
        public val bottomEnd: Dp = 0.dp,
        public val bottomStart: Dp = 0.dp
    ) : Shape() {
        public constructor(size: Dp) : this(
            topStart = size,
            topEnd = size,
            bottomEnd = size,
            bottomStart = size,
        )
    }

    @[Immutable Serializable]
    public data object Rectangle : Shape()

    @[Immutable Serializable]
    public data object Circle : Shape()
}

public typealias RoundedCornerShape = Shape.RoundedCorner

public typealias RectangleShape = Shape.Rectangle

public typealias CircleShape = Shape.Circle
