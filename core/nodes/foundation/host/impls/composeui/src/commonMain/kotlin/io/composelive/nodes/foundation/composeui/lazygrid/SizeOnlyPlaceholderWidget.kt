package io.composelive.nodes.foundation.composeui.lazygrid

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import app.cash.redwood.widget.Widget
import io.composelive.nodes.foundation.widget.Box
import app.cash.redwood.Modifier as RedwoodModifier

internal class SizeOnlyPlaceholderWidget(
    private val size: DpSize,
) : Box<@Composable (Modifier) -> Unit> {

    override val content: Widget.Children<@Composable ((Modifier) -> Unit)>
        get() = object : Widget.Children<@Composable ((Modifier) -> Unit)> {
            override val widgets: List<Widget<@Composable ((Modifier) -> Unit)>>
                get() = emptyList()

            override fun insert(
                index: Int,
                widget: Widget<@Composable ((Modifier) -> Unit)>
            ) {
            }

            override fun move(fromIndex: Int, toIndex: Int, count: Int) {
            }

            override fun remove(index: Int, count: Int) {
            }

            override fun onModifierUpdated(
                index: Int,
                widget: Widget<@Composable ((Modifier) -> Unit)>
            ) {
            }

            override fun detach() {
            }
        }

    override fun onClick(onClick: (() -> Unit)?) {}

    override val value: @Composable ((Modifier) -> Unit) = { modifier ->
        Box(Modifier.size(size))
    }

    override var modifier: RedwoodModifier = RedwoodModifier
}
