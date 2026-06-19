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
import app.cash.redwood.widget.Widget
import app.cash.redwood.widget.WidgetSystem
import kotlinx.coroutines.flow.StateFlow

public class ReuseRootView(
    reuseRootOwner: ReuseRootOwner,
    override val widgetSystem: WidgetSystem<@Composable (Modifier) -> Unit>,
    override val dynamicContentWidgetFactory: DynamicContentWidgetFactory<@Composable (Modifier) -> Unit>,
    override val onBackPressedDispatcher: OnBackPressedDispatcher,
    override val uiConfiguration: StateFlow<UiConfiguration>,
) : TreehouseView<@Composable (Modifier) -> Unit> {

    override val children: Widget.Children<@Composable ((Modifier) -> Unit)> =
        ReuseChildren(reuseRootOwner)

    override val value: @Composable (Modifier) -> Unit = {}

    // TODO TreehouseView is a weird type and shouldn't extend from RedwoodView. The concept
    //  of this registry shouldn't exist for Treehouse / should be auto-wired via RedwoodContent.
    override val savedStateRegistry: SavedStateRegistry? get() = null
    override val readyForContent: Boolean = true
    override var readyForContentChangeListener:
            ReadyForContentChangeListener<@Composable (Modifier) -> Unit>? =
        null
    override var saveCallback: TreehouseView.SaveCallback? = null
    override val stateSnapshotId: StateSnapshot.Id = StateSnapshot.Id(null)

    override fun requestFocus(widget: Widget<@Composable ((Modifier) -> Unit)>) {
        // TODO: complete this.
    }
}
