package io.composelive.app.android.standard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.clive.configuration.CliveCondition
import io.clive.core.Clive.LayoutState
import io.clive.ui.CliveScreenFactory
import io.clive.ui.data.CliveScreenData
import io.composelive.nodes.foundation.host.composeui.images.LocalCliveImageLoader
import io.composelive.nodes.foundation.host.composeui.images.rememberImageLoader

@Composable
fun DemoStandardApp(screenFactory: CliveScreenFactory) {
    var screenData by remember { mutableIntStateOf(50) }
    var cliveVersion by remember { mutableIntStateOf(0) }

    val screen = remember {
        screenFactory.create(name = "demo").also {
            it.bind(
                CliveCondition.forVersion(cliveVersion),
                CliveScreenData(screenData.toString()),
            )
        }
    }

    fun refresh() {
        screen.bind(
            CliveCondition.forVersion(cliveVersion),
            CliveScreenData(screenData.toString()),
        )
        screen.refreshTriggered()
    }

    var foobarPayload by remember { mutableIntStateOf(200) }

    val foobarNestedLayout = remember {
        screen.layout(
            moduleId = "./demo-foobar-nested.js",
            name = "nested-foobar",
            payload = "<initial>",
        )
    }

    val layouts = remember {
        listOf(
            foobarNestedLayout,
            screen.layout(
                moduleId = "./demo-foobar-parent.js",
                name = "parent-foobar",
                payload = null,
            )
        )
    }

    val lazyColumnState = rememberLazyListState()

    CompositionLocalProvider(
        LocalCliveImageLoader provides rememberImageLoader(),
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                Button({
                    cliveVersion++
                    refresh()
                }) {
                    Text("Bump CliveV$cliveVersion -> CliveV${cliveVersion + 1}")
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                Button({
                    screenData++
                    refresh()
                }) {
                    Text("Bump screen data")
                }

                Spacer(Modifier.width(10.dp))

                Button({
                    foobarNestedLayout.setData(foobarPayload.toString())
                    foobarPayload++
                }) {
                    Text("Bump foobar payload")
                }
            }

            Spacer(Modifier.height(8.dp))

            val layoutStates = layouts.map { layout ->
                layout.state.collectAsState().value
            }

            LazyColumn(Modifier.fillMaxSize(), state = lazyColumnState) {
                items(layoutStates) { layoutState ->
                    when (layoutState) {
                        is LayoutState.Loading -> {
                            Text("Loading $layoutState...")
                        }

                        is LayoutState.Error -> {
                            Text("Failed to load $layoutState: ${layoutState.e}")
                        }

                        is LayoutState.Success -> {
                            layoutState.content.effectWidgets.forEach { widget ->
                                widget.value(Modifier)
                            }
                            layoutState.content.mainWidget.value(Modifier)
                        }
                    }
                }
            }
        }
    }
}
