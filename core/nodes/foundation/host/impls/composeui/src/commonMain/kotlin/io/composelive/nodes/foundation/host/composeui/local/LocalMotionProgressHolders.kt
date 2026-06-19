package io.composelive.nodes.foundation.host.composeui.local

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import app.cash.redwood.widget.compose.ComposeWidgetChildren
import io.composelive.nodes.foundation.host.composeui.MotionProgressState
import io.composelive.nodes.foundation.host.composeui.FoundationMotionProgressHolder

@PublishedApi
internal val LocalMotionProgressHolders:
        ProvidableCompositionLocal<List<FoundationMotionProgressHolder>> =
    compositionLocalOf { emptyList() }

@Composable
@PublishedApi
internal inline fun WithLocalMotionProgressHolders(
    children: ComposeWidgetChildren,
    crossinline content: @Composable () -> Unit,
) {
    val holders by remember {
        derivedStateOf {
            children.widgets.filterIsInstance<FoundationMotionProgressHolder>()
        }
    }
    holders.forEach { holder ->
        holder.value(Modifier)
    }

    if (holders.isNotEmpty()) {
        CompositionLocalProvider(
            LocalMotionProgressHolders provides
                    LocalMotionProgressHolders.current + holders
        ) {
            content()
        }
    } else {
        content()
    }
}

@Composable
public fun rememberScrollProgressState(id: Long): MotionProgressState? {
    val holders = LocalMotionProgressHolders.current
    return remember(id) {
        holders.find { it.progress?.id == id }?.state
    }
}
