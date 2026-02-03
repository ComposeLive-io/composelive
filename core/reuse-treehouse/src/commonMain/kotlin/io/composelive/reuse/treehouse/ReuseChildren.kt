package io.composelive.reuse.treehouse

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.cash.redwood.widget.Widget
import io.composelive.nodes.foundation.host.composeui.FoundationReuseRoot

public class ReuseChildren(
    private val reuseController: ReuseController
) : Widget.Children<@Composable (Modifier) -> Unit> {
    override val widgets: List<Widget<@Composable (Modifier) -> Unit>> = emptyList()

    override fun insert(index: Int, widget: Widget<@Composable (Modifier) -> Unit>) {
        val root = widget as? FoundationReuseRoot
        if (root != null) {
            reuseController.reuseRootInserted(root)
        }
    }

    override fun move(fromIndex: Int, toIndex: Int, count: Int) {}
    override fun remove(index: Int, count: Int) {}
    override fun onModifierUpdated(
        index: Int,
        widget: Widget<@Composable (Modifier) -> Unit>
    ) {
    }

    override fun detach() {}
}
