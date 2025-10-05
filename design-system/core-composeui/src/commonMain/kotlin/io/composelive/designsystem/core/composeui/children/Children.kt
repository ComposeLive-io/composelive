package io.composelive.designsystem.core.composeui.children

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import app.cash.redwood.widget.Widget
import app.cash.redwood.widget.compose.ComposeWidgetChildren

public class Children private constructor(
    private val children: ComposeWidgetChildren
) : Widget.Children<@Composable (Modifier) -> Unit> {

    public constructor() : this(children = ComposeWidgetChildren())

    public var modifierTick: Int by mutableIntStateOf(0)

    override val widgets: List<Widget<@Composable ((Modifier) -> Unit)>>
        get() = children.widgets

    override fun insert(
        index: Int,
        widget: Widget<@Composable ((Modifier) -> Unit)>
    ) {
        children.insert(index, widget)
    }

    override fun move(fromIndex: Int, toIndex: Int, count: Int) {
        children.move(fromIndex, toIndex, count)
    }

    override fun remove(index: Int, count: Int) {
        children.remove(index, count)
    }

    override fun onModifierUpdated(
        index: Int,
        widget: Widget<@Composable ((Modifier) -> Unit)>
    ) {
        modifierTick++
    }

    override fun detach() {
        children.detach()
    }
}
