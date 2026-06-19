package io.composelive.nodes.foundation.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import io.composelive.nodes.foundation.compose.lazylayout.ProgrammaticScrollableLazyLayoutState
import io.composelive.nodes.foundation.compose.lazylayout.strategy.LoadingStrategy
import io.composelive.nodes.foundation.compose.lazylayout.strategy.StaticSizeLoadingStrategy

/**
 * Creates a [LazyGridState] that is remembered across compositions.
 */
@Composable
public fun rememberLazyGridState(
    strategy: LoadingStrategy = StaticSizeLoadingStrategy(),
): LazyGridState {
    return remember { LazyGridState(strategy) }
}

/**
 * A state object that can be hoisted to control and observe scrolling.
 *
 * In most cases, this will be created via [rememberLazyGridState].
 */
public open class LazyGridState(
    strategy: LoadingStrategy = StaticSizeLoadingStrategy(),
) : ProgrammaticScrollableLazyLayoutState(strategy)
