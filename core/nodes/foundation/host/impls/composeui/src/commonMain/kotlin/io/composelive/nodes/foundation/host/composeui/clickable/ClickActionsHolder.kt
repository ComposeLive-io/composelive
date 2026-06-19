package io.composelive.nodes.foundation.host.composeui.clickable

import androidx.collection.mutableIntObjectMapOf
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf

public class ClickActionsHolder internal constructor() {
    private val actions = mutableIntObjectMapOf<() -> Unit>()

    @PublishedApi
    internal fun registerAction(id: Int, action: () -> Unit) {
        actions[id] = action
    }

    @PublishedApi
    internal fun unregisterAction(id: Int) {
        actions.remove(id)
    }

    internal fun clicked(id: Int) {
        actions[id]?.invoke()
    }
}

public val LocalCliveClickActionsHolder: ProvidableCompositionLocal<ClickActionsHolder?> =
    staticCompositionLocalOf { ClickActionsHolder() }

@Composable
public fun rememberCliveClickActionsHolder(): ClickActionsHolder =
    remember { ClickActionsHolder() }

@Composable
@PublishedApi
internal fun requireClickActionsHolder(): ClickActionsHolder {
    return requireNotNull(LocalCliveClickActionsHolder.current) {
        "ClickActionsHolder must be provided to use the clickable modifier"
    }
}
