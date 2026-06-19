package io.composelive.nodes.foundation.common

import app.cash.redwood.ui.Size
import app.cash.redwood.ui.dp

public data object ButtonDefaults {
    public val defaultButtonContentPadding: PaddingValues = PaddingValues(
        horizontal = 24.dp,
        vertical = 8.dp,
    )

    public val buttonWithIconContentPadding: PaddingValues = PaddingValues(
        start = 16.dp,
        top = 8.dp,
        end = 24.dp,
        bottom = 8.dp,
    )

    public val defaultButtonShape: Shape = CircleShape

    public val defaultMinButtonSize: Size = Size(24.dp, 24.dp)
}
