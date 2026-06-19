package io.composelive.nodes.foundation.common

import androidx.compose.runtime.Immutable
import app.cash.redwood.ui.Dp
import app.cash.redwood.ui.dp
import kotlinx.serialization.Serializable

@[Immutable Serializable]
public data class Shape(
    val roundedCorner: RoundedCorner? = null,
    val rectangle: Rectangle? = null,
    val circle: Circle? = null,
) {
    @[Immutable Serializable]
    public data class RoundedCorner(
        public val topStart: Dp = 0.dp,
        public val topEnd: Dp = 0.dp,
        public val bottomEnd: Dp = 0.dp,
        public val bottomStart: Dp = 0.dp,
    ) {
        public constructor(size: Dp) : this(
            topStart = size,
            topEnd = size,
            bottomEnd = size,
            bottomStart = size,
        )
    }

    @[Immutable Serializable]
    public data object Rectangle

    @[Immutable Serializable]
    public data object Circle
}

@Suppress("FunctionName")
public fun RoundedCornerShape(
    topStart: Dp = 0.dp,
    topEnd: Dp = 0.dp,
    bottomEnd: Dp = 0.dp,
    bottomStart: Dp = 0.dp,
): Shape = Shape(
    roundedCorner = Shape.RoundedCorner(
        topStart = topStart,
        topEnd = topEnd,
        bottomEnd = bottomEnd,
        bottomStart = bottomStart,
    )
)

@Suppress("FunctionName")
public fun RoundedCornerShape(
    size: Dp,
): Shape = Shape(
    roundedCorner = Shape.RoundedCorner(
        topStart = size,
        topEnd = size,
        bottomEnd = size,
        bottomStart = size,
    )
)

public val RectangleShape: Shape = Shape(rectangle = Shape.Rectangle)

public val CircleShape: Shape = Shape(circle = Shape.Circle)
