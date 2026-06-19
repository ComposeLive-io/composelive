package io.composelive.nodes.foundation.host.composeui.lazylayout

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import app.cash.redwood.widget.ChangeListener
import app.cash.redwood.widget.Widget
import app.cash.redwood.widget.compose.ComposeWidgetChildren

public interface BaseLazyItems : ChangeListener {
    public val placeholder: ComposeWidgetChildren
    public val items: LazyChildren
    public fun itemsBefore(itemsBefore: Int)
    public fun itemsAfter(itemsAfter: Int)
}

public abstract class BaseLazyItemsImpl : BaseLazyItems {
    override val placeholder: ComposeWidgetChildren = ComposeWidgetChildren()
    abstract override val items: LazyChildren

    protected fun placeholder(): Widget<@Composable (Modifier) -> Unit> {
        return placeholder.widgets.getOrElse(0) {
            SizeOnlyPlaceholderWidget(DpSize(0.dp, 0.dp))
        }
    }

    override fun itemsBefore(itemsBefore: Int) {
        items.itemsBefore = itemsBefore
    }

    override fun itemsAfter(itemsAfter: Int) {
        items.itemsAfter = itemsAfter
    }

    override fun onEndChanges() {
        items.onEndChanges()
    }
}
