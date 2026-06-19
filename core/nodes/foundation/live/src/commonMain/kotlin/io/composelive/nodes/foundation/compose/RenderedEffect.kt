package io.composelive.nodes.foundation.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope

/**
 * Use to launch a block of code when the host renders the live composition.
 *
 * You can use it instead of [LaunchedEffect] to track appearance
 * of the layout in the host composition.
 *
 * If you just use [LaunchedEffect] directly the result could be unexpected.
 * Because of bridge delays Clive performs live composition of lazy container children
 * much earlier than the host does render it. It's done to prevent the user
 * to see placeholders instead of items in a lazy container.
 */
@Composable
public fun RenderedEffect(
    key1: Any?,
    rendered: suspend CoroutineScope.() -> Unit,
) {
    RenderedEffectLauncher {
        LaunchedEffect(key1, rendered)
    }
}

@Composable
public fun RenderedEffect(
    key1: Any?,
    key2: Any?,
    rendered: suspend CoroutineScope.() -> Unit,
) {
    RenderedEffectLauncher {
        LaunchedEffect(key1, key2, rendered)
    }
}

@Composable
public fun RenderedEffect(
    key1: Any?,
    key2: Any?,
    key3: Any?,
    rendered: suspend CoroutineScope.() -> Unit,
) {
    RenderedEffectLauncher {
        LaunchedEffect(key1, key2, key3, rendered)
    }
}

@Composable
public fun RenderedEffect(
    vararg keys: Any?,
    rendered: suspend CoroutineScope.() -> Unit,
) {
    RenderedEffectLauncher {
        LaunchedEffect(keys = keys, rendered)
    }
}

// This deprecated-error function shadows the varargs overload so that the varargs version
// is not used without key parameters.
@Deprecated(RenderedEffectNoParamError, level = DeprecationLevel.ERROR)
@Suppress("DeprecatedCallableAddReplaceWith", "UNUSED_PARAMETER")
@Composable
public fun RenderedEffect(rendered: suspend CoroutineScope.() -> Unit): Unit =
    error(RenderedEffectNoParamError)

@Composable
private fun RenderedEffectLauncher(
    rendered: @Composable () -> Unit,
) {
    var isRendered by remember { mutableStateOf(false) }
    if (isRendered) {
        rendered()
    }
    RenderedEffectLauncher(
        renderedChanged = { rendered ->
            isRendered = rendered
        },
    )
}

private const val RenderedEffectNoParamError =
    "RenderedEffect must provide one or more 'key' parameters that define the identity of " +
            "the RenderedEffect and determine when its previous effect coroutine should be cancelled " +
            "and a new effect launched for the new key."
