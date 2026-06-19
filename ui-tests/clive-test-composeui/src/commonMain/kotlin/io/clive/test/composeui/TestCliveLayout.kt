package io.clive.test.composeui

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import app.cash.redwood.protocol.host.HostProtocol
import app.cash.redwood.ui.UiConfiguration
import app.cash.redwood.widget.WidgetSystem
import app.cash.zipline.Zipline
import io.clive.configuration.CliveCondition
import io.clive.configuration.CliveConfiguration
import io.clive.configuration.CliveUiConfiguration
import io.clive.core.Clive
import io.clive.standard.host.composeui.ComposeUiStandardWidgetSystem
import io.clive.ui.CliveScreenFactory
import io.composelive.nodes.foundation.host.composeui.clickable.LocalCliveClickActionsHolder
import io.composelive.nodes.foundation.host.composeui.clickable.rememberCliveClickActionsHolder
import io.composelive.nodes.foundation.host.composeui.images.LocalCliveImageLoader
import io.composelive.nodes.foundation.host.composeui.images.rememberImageLoader
import io.composelive.reuse.treehouse.HostZiplineBridge
import io.composelive.standard.protocol.host.StandardHostProtocol
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

@Composable
public fun TestCliveLayout(
    layoutName: String,
    moduleId: String,
    payload: String = "",
    screenFactory: CliveScreenFactory = rememberTestScreenFactory(),
    modifier: Modifier = Modifier,
) {
    val screen = remember(screenFactory) {
        screenFactory.create("test").also {
            it.bind(CliveCondition.forVersion(0))
        }
    }
    DisposableEffect(screen) {
        onDispose {
            screen.free()
        }
    }

    val layout = remember(moduleId, layoutName, payload) {
        screen.layout(moduleId, layoutName, payload)
    }
    DisposableEffect(layout) {
        onDispose {
            layout.free()
        }
    }

    CompositionLocalProvider(
        LocalCliveImageLoader provides rememberImageLoader(),
        LocalCliveClickActionsHolder provides rememberCliveClickActionsHolder(),
    ) {
        when (val state = layout.state.collectAsState().value) {
            Clive.LayoutState.Loading -> {
                BasicText("Loading...")
            }

            is Clive.LayoutState.Error -> {
                BasicText(state.e.message ?: "error")
                LaunchedEffect(state.e) {
                    state.e.printStackTrace()
                }
            }

            is Clive.LayoutState.Success -> {
                state.content.mainWidget.value.invoke(Modifier)
            }
        }
    }
}

@Composable
public fun rememberTestScreenFactory(
    scope: CoroutineScope = remember { MainScope() },
    hostZiplineBridge: HostZiplineBridge = object : HostZiplineBridge {
        override fun bindTo(zipline: Zipline) {}
    },
    widgetSystem: WidgetSystem<@Composable (Modifier) -> Unit> =
        remember { ComposeUiStandardWidgetSystem() },
    hostProtocolFactory: HostProtocol.Factory = StandardHostProtocol,
): CliveScreenFactory {
    val createTreehouseFactory = rememberCreateTreehouseAppFactory(hostProtocolFactory)
    return remember {
        val uiConfiguration = CliveUiConfiguration(UiConfiguration())
        CliveScreenFactory(
            uiScope = scope,
            configuration = object : CliveConfiguration {

                override suspend fun getBaseUrl(): String =
                    localhostBaseUrl

                override suspend fun getEnvironment(): CliveConfiguration.Environment =
                    CliveConfiguration.Environment.Development

                override val cacheDir: String? get() = null
            },
            uiConfiguration = uiConfiguration,
            httpClient = createZiplineHttpClient(),
            widgetSystem = widgetSystem,
            logger = TestCliveLogger,
            hostZiplineBridge = hostZiplineBridge,
            createTreehouseFactory = createTreehouseFactory,
            resources = null,
        )
    }
}
