package io.composelive.reuse.treehouse

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.cash.redwood.treehouse.DynamicContentWidgetFactory
import app.cash.redwood.treehouse.StateSnapshot
import app.cash.redwood.treehouse.TreehouseView
import app.cash.redwood.treehouse.TreehouseView.ReadyForContentChangeListener
import app.cash.redwood.ui.OnBackPressedDispatcher
import app.cash.redwood.ui.UiConfiguration
import app.cash.redwood.widget.SavedStateRegistry
import app.cash.redwood.widget.WidgetSystem
import kotlinx.coroutines.flow.MutableStateFlow

internal class ReuseTreehouseView(
    reuseController: ReuseController,
    override val widgetSystem: WidgetSystem<@Composable (Modifier) -> Unit>,
    override val dynamicContentWidgetFactory: DynamicContentWidgetFactory<@Composable (Modifier) -> Unit>,
    override val onBackPressedDispatcher: OnBackPressedDispatcher,
    override val uiConfiguration: MutableStateFlow<UiConfiguration>,
) : TreehouseView<@Composable (Modifier) -> Unit> {

    override val children = ReuseChildren(reuseController)

    override val value: @Composable (Modifier) -> Unit = {}

    // TODO TreehouseView is a weird type and shouldn't extend from RedwoodView. The concept
    //  of this registry shouldn't exist for Treehouse / should be auto-wired via RedwoodContent.
    override val savedStateRegistry: SavedStateRegistry? get() = null
    override val readyForContent = true
    override var readyForContentChangeListener:
            ReadyForContentChangeListener<@Composable (Modifier) -> Unit>? =
        null
    override var saveCallback: TreehouseView.SaveCallback? = null
    override val stateSnapshotId = StateSnapshot.Id(null)
}
