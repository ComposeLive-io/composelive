package io.composelive.nodes.foundation.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import app.cash.redwood.Modifier
import io.composelive.nodes.foundation.common.Indication
import io.composelive.nodes.foundation.compose.clickable.requireClickActionIdGenerator

@Composable
public fun Modifier.clickable(
    enabled: Boolean = true,
    indication: Indication = LocalIndication.current,
    onClick: () -> Unit,
): Modifier {
    val actionsHolder = requireClickActionIdGenerator()
    val id = actionsHolder.rememberNewId()
    ClickReceiver(id, onClick)
    return remember(enabled, indication) {
        clickable(
            enabled = enabled,
            indication = indication,
            actionId = id,
        )
    }
}
