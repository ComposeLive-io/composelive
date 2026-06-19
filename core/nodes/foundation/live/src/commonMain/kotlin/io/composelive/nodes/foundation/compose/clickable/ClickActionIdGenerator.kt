package io.composelive.nodes.foundation.compose.clickable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember

public class ClickActionIdGenerator internal constructor() {
    private var nextId = 0

    @Composable
    internal fun rememberNewId(): Int {
        return remember { nextId.also { nextId++ } }
    }
}

public val LocalClickActionIdGenerator: ProvidableCompositionLocal<ClickActionIdGenerator?> =
    compositionLocalOf { null }

@Composable
public fun rememberClickActionIdGenerator(): ClickActionIdGenerator =
    remember { ClickActionIdGenerator() }

@Composable
internal fun requireClickActionIdGenerator(): ClickActionIdGenerator {
    return requireNotNull(LocalClickActionIdGenerator.current) {
        "ClickActionIdGenerator must be provided to use the clickable modifier"
    }
}
