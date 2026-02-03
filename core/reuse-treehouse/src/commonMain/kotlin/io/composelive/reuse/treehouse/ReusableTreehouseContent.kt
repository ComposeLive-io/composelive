package io.composelive.reuse.treehouse

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import app.cash.redwood.composeui.safeAreaInsets
import app.cash.redwood.treehouse.AppService
import app.cash.redwood.treehouse.DynamicContentWidgetFactory
import app.cash.redwood.treehouse.TreehouseApp
import app.cash.redwood.treehouse.TreehouseContentSource
import app.cash.redwood.treehouse.bindWhenReady
import app.cash.redwood.widget.WidgetSystem
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
public fun <A : AppService> rememberSharedTreehouse(
    treehouseApp: TreehouseApp<A>,
    widgetSystem: WidgetSystem<@Composable (Modifier) -> Unit>,
    contentSource: TreehouseContentSource<A>,
    dynamicContentWidgetFactory: DynamicContentWidgetFactory<@Composable (Modifier) -> Unit>,
): ReuseController {
    val reuseController = remember { ReuseController() }
    val onBackPressedDispatcher = platformOnBackPressedDispatcher()

    val density = LocalDensity.current
    val safeAreaInsets = safeAreaInsets()
    val darkMode = isSystemInDarkTheme()
    val uiConfiguration by remember {
        derivedStateOf {
            createUiConfiguration(
                safeAreaInsets = safeAreaInsets,
                density = density.density.toDouble(),
                darkMode = darkMode,
            )
        }
    }
    val uiConfigurationFlow = remember { MutableStateFlow(uiConfiguration) }
    LaunchedEffect(uiConfigurationFlow, uiConfiguration) {
        uiConfigurationFlow.value = uiConfiguration
    }
    val treehouseView = remember(widgetSystem) {
        ReuseTreehouseView(
            reuseController = reuseController,
            widgetSystem = widgetSystem,
            dynamicContentWidgetFactory = dynamicContentWidgetFactory,
            onBackPressedDispatcher = onBackPressedDispatcher,
            uiConfiguration = uiConfigurationFlow,
        )
    }
    DisposableEffect(treehouseView, contentSource) {
        val closeable = contentSource.bindWhenReady(treehouseView, treehouseApp)
        onDispose {
            closeable.close()
        }
    }
    return reuseController
}
