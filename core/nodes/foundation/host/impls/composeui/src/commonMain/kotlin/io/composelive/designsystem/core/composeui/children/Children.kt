package io.composelive.designsystem.core.composeui.children

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import app.cash.redwood.widget.Widget
import io.composelive.compose.extensions.Tick
import io.composelive.compose.extensions.move
import io.composelive.compose.extensions.remove

public class Children : Widget.Children<@Composable (Modifier) -> Unit> {
    public var modifierTick: Tick = Tick()

    private val _widgets = mutableStateListOf<Widget<@Composable (Modifier) -> Unit>>()
    override val widgets: List<Widget<@Composable (Modifier) -> Unit>> get() = _widgets

    override fun insert(index: Int, widget: Widget<@Composable (Modifier) -> Unit>) {
        _widgets.add(index, widget)
    }

    override fun move(fromIndex: Int, toIndex: Int, count: Int) {
        _widgets.move(fromIndex, toIndex, count)
    }

    override fun remove(index: Int, count: Int) {
        _widgets.remove(index, count)
    }

    override fun onModifierUpdated(index: Int, widget: Widget<@Composable (Modifier) -> Unit>) {
        modifierTick.trigger()
    }

    override fun detach() {
    }
}
