package io.composelive.shared

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import app.cash.redwood.treehouse.TreehouseApp
import app.cash.redwood.treehouse.TreehouseContentSource
import app.cash.redwood.treehouse.composeui.TreehouseContent
import coil3.ImageLoader
import io.composelive.designsystem.motion.composeui.ComposeUiDefaultWidgetSystem
import io.composelive.treehouse.MainPresenter

@Composable
fun MainTreehouseApp(
    treehouseApp: TreehouseApp<MainPresenter>,
    snackbarHostState: SnackbarHostState,
    imageLoader: ImageLoader,
) {
    val treehouseContentSource = remember {
        TreehouseContentSource(MainPresenter::launch)
    }

    Container(snackbarHostState) { contentPadding ->
        TreehouseContent(
            treehouseApp = treehouseApp,
            widgetSystem = ComposeUiDefaultWidgetSystem(imageLoader),
            contentSource = treehouseContentSource,
            modifier = Modifier.padding(contentPadding),
            dynamicContentWidgetFactory = MainDynamicContentWidgetFactory(),
        )
    }
}
