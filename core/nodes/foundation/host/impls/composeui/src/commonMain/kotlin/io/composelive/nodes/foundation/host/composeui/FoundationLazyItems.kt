package io.composelive.nodes.foundation.host.composeui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import app.cash.redwood.widget.ChangeListener
import io.composelive.nodes.foundation.host.composeui.children.Children
import io.composelive.nodes.foundation.host.composeui.lazygrid.LazyChildren
import io.composelive.nodes.foundation.host.composeui.lazygrid.SizeOnlyPlaceholderWidget
import io.composelive.nodes.foundation.widget.LazyItems
import app.cash.redwood.Modifier as RedwoodModifier
import io.composelive.nodes.foundation.common.lazygrid.GridItemSpan as RedwoodGridItemSpan

internal class FoundationLazyItems : LazyItems<@Composable (Modifier) -> Unit>, ChangeListener {
    internal var span: RedwoodGridItemSpan? by mutableStateOf(null)

    override var modifier: RedwoodModifier = RedwoodModifier

    override val placeholder = Children()
    override val items = LazyChildren(
        placeholder = {
            placeholder.widgets.getOrElse(0) { SizeOnlyPlaceholderWidget(DpSize(0.dp, 0.dp)) }
        }
    )

    override fun itemsBefore(itemsBefore: Int) {
        items.itemsBefore = itemsBefore
    }

    override fun itemsAfter(itemsAfter: Int) {
        items.itemsAfter = itemsAfter
    }

    override fun span(span: RedwoodGridItemSpan?) {
        this.span = span
    }

    override val value: @Composable ((Modifier) -> Unit) = { modifier ->

    }

    override fun onEndChanges() {
        items.onEndChanges()
    }
}
